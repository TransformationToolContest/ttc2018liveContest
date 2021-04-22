package de.tudresden.inf.st.ttc18live.translator;

import de.tudresden.inf.st.ttc18live.Utils;
import de.tudresden.inf.st.ttc18live.jastadd.model.*;
import de.tudresden.inf.st.ttc18live.parser.*;
import de.tudresden.inf.st.ttc18live.parser.change.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Translate parsed XML content to <i>JastAdd</i> models.
 *
 * @author rschoene - Initial contribution
 */
public class XmlToJastaddTranslator {

  /** Found forward references when translating change sets (by element) */
  private Map<ParsedModelElement, ModelElement> forwardReferencesElements;
  /** Found forward references when translating change sets (by path) */
  private Map<String, ModelElement> forwardReferencesPaths;
  /** Created objects within changes when translating change sets */
  private Map<Long, ModelElement> createdObjects;

  /** Created users created initially and within changes */
  private Map<Long, User> userMap = new HashMap<>();
  /** Created submissions created initially and within changes */
  private Map<Long, Submission> submissionMap = new HashMap<>();

  private SocialNetwork socialNetwork;

  public XmlToJastaddTranslator() {
    // empty for now
    socialNetwork = null;
  }

  public SocialNetwork getSocialNetwork() {
    return socialNetwork;
  }

  public SocialNetwork translateSocialNetwork(ParsedSocialNetwork parsedSocialNetwork) throws ParseException {
    Map<User, String> userSubmissions = new HashMap<>();
    Map<User, String> userFriends = new HashMap<>();
    Map<User, String> userLikes = new HashMap<>();

    socialNetwork = SocialNetwork.createSocialNetwork();
    for (ParsedUser parsedUser : parsedSocialNetwork.users) {
      User user = new User();
      user.setId(Long.valueOf(parsedUser.id));
      user.setName(parsedUser.name);
      if (parsedUser.submissions != null) {
        userSubmissions.put(user, parsedUser.submissions);
      }
      if (parsedUser.friends != null) {
        userFriends.put(user, parsedUser.friends);
      }
      if (parsedUser.likes != null) {
        userLikes.put(user, parsedUser.likes);
      }
      socialNetwork.addUser(user);

      // FIXME remove manual user lookup
      userMap.put(user.getId(), user);
    }
    for (ParsedPost parsedPost : parsedSocialNetwork.posts) {
      Post post = new Post();
      post.setId(Long.valueOf(parsedPost.id));
      post.setTimestamp(convertTimestamp(parsedPost.timestamp));
      post.setContent(parsedPost.content);
      addComments(parsedPost, post, submissionMap);
      socialNetwork.addPost(post);

      // FIXME remove manual submission lookup
      submissionMap.put(post.getId(), post);
    }

    // resolveModelElement submissions
    for (Map.Entry<User, String> userAndSubmissions : userSubmissions.entrySet()) {
      String[] submissionTokens = userAndSubmissions.getValue().split(" ");
      User user = userAndSubmissions.getKey();
      for (String token : submissionTokens) {
        user.addSubmission(submissionMap.get(Long.valueOf(token)));
      }
    }
    // resolveModelElement friends
    for (Map.Entry<User, String> userAndFriends : userFriends.entrySet()) {
      String[] friendsTokens = userAndFriends.getValue().split(" ");
      User user = userAndFriends.getKey();
      for (String token : friendsTokens) {
        user.addFriend(userMap.get(Long.valueOf(token)));
      }
    }
    // resolveModelElement likes
    for (Map.Entry<User, String> userAndLikes : userLikes.entrySet()) {
      String[] likesTokens = userAndLikes.getValue().split(" ");
      User user = userAndLikes.getKey();
      for (String token : likesTokens) {
        user.addLike(((Comment) submissionMap.get(Long.valueOf(token))));
      }
    }
    return socialNetwork;
  }

  private void addComments(ParsedSubmission parsedSubmission, Submission submission,
                           Map<Long, Submission> submissionMap)
      throws ParseException {
    if (parsedSubmission.comments == null) {
      return;
    }
    for (ParsedComment parsedComment : parsedSubmission.comments) {
      Comment comment = new Comment();
      comment.setId(Long.valueOf(parsedComment.id));
      comment.setTimestamp(convertTimestamp(parsedComment.timestamp));
      comment.setContent(parsedComment.content);
      submission.addComment(comment);
      addComments(parsedComment, comment, submissionMap);

      // FIXME remove manual submission lookup
      submissionMap.put(comment.getId(), comment);
    }
  }

  // format = 2010-03-03T07:12:10
  private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
  private Long convertTimestamp(String timestamp) throws ParseException {
    return DATE_FORMAT.parse(timestamp).getTime();
  }

  // TODO remove obsolete data structure. references are resolved on the fly.
  class ChangeListToRevise {
    List<AssociationCollectionInsertion> associationCollectionInsertions = new ArrayList<>();
    List<AssociationPropertyChange> associationPropertyChangeList = new ArrayList<>();
    List<AttributionPropertyChange> attributionPropertyChanges = new ArrayList<>();
    List<CompositionListInsertion> compositionListInsertions = new ArrayList<>();
  }

  public ModelChangeSet translateModelChangeSet(ParsedModelChangeSet parsedModelChangeSet) throws ParseException {
    if (socialNetwork == null) {
      System.err.println("No previously parsed SocialNetwork found!");
      return null;
    }

    forwardReferencesElements = new HashMap<>();
    forwardReferencesPaths = new HashMap<>();
    createdObjects = new HashMap<>();

    ModelChangeSet result = new ModelChangeSet();
    result.setSocialNetwork(socialNetwork);

    if (parsedModelChangeSet.changes == null) {
      // return empty change set
      return result;
    }

    ChangeListToRevise changeListToRevise = new ChangeListToRevise();
    List<ParsedModelChange> changes = parsedModelChangeSet.changes;
    for (int i = 0; i < changes.size(); i++) {
      ParsedModelChange parsedModelChange = changes.get(i);
      result.addModelChange(translateModelChange(parsedModelChange, changeListToRevise, result, parsedModelChangeSet));
    }
    return result;
  }

//  private ModelChange translate(ParsedModelChange parsedModelChange,
//                                ChangeListToRevise changeListToRevise,
//                                ModelChangeSet modelChangeSet,
//                                ParsedModelChangeSet parsedModelChangeSet) throws ParseException {
//    return translate(parsedModelChange, changeListToRevise, modelChangeSet, parsedModelChangeSet);
//  }

  private ModelChange translateModelChange(ParsedModelChange parsedModelChange,
                                ChangeListToRevise changeListToRevise,
                                ModelChangeSet modelChangeSet,
                                ParsedModelChangeSet parsedModelChangeSet) throws ParseException {
    if (parsedModelChange instanceof ParsedChangeTransaction) {
      ParsedChangeTransaction pct = (ParsedChangeTransaction) parsedModelChange;
      ChangeTransaction transaction = new ChangeTransaction();
      transaction.setSourceChange(translateModelChange(pct.sourceChange, changeListToRevise, modelChangeSet, parsedModelChangeSet));
      for (ParsedModelChange nestedChange : pct.nestedChanges) {
        transaction.addNestedChange(translateModelChange(nestedChange, changeListToRevise, modelChangeSet, parsedModelChangeSet));
      }
      return transaction;
    } else {
      // elementary change
      ParsedElementaryChange pec = (ParsedElementaryChange) parsedModelChange;
      // parse added element
      ModelElement affectedElement = resolveModelElement(pec.affectedElement, modelChangeSet, parsedModelChangeSet);
      Objects.requireNonNull(affectedElement, "Affected element not null in " + parsedModelChange);
      ElementaryChange elementaryChange;
      String feature = pec.feature.substring(pec.feature.lastIndexOf('/') + 1);
      if (pec instanceof ParsedAssociationCollectionInsertion) {
        // parse added element
        ModelElement addedElement = resolveModelElement(((ParsedAssociationCollectionInsertion) pec).addedElement,
            modelChangeSet, parsedModelChangeSet);
        // create association collection insertion
        elementaryChange = Utils.add(
            new AssociationCollectionInsertion(affectedElement, feature, addedElement),
            changeListToRevise.associationCollectionInsertions);
      } else if (pec instanceof ParsedAssociationPropertyChange) {
        // parse new value
        ModelElement newValue = resolveModelElement(((ParsedAssociationPropertyChange) pec).newValue,
            modelChangeSet, parsedModelChangeSet);
        Objects.requireNonNull(newValue, "New value not null in " + parsedModelChange);
        // create association property change
        elementaryChange = Utils.add(
            new AssociationPropertyChange(affectedElement, feature, newValue),
            changeListToRevise.associationPropertyChangeList);
      } else if (pec instanceof ParsedAttributionPropertyChange) {
        // create attribution property change
        elementaryChange = Utils.add(
            new AttributionPropertyChange(affectedElement, feature, ((ParsedAttributionPropertyChange) pec).newValue),
            changeListToRevise.attributionPropertyChanges);
      } else if (pec instanceof ParsedCompositionListInsertion) {
        // parse added element
        ModelElement addedElement = translateModelElement(((ParsedCompositionListInsertion) pec).addedElement,
            modelChangeSet, parsedModelChangeSet);
        Objects.requireNonNull(addedElement, "Added element not null in " + parsedModelChange);
        if (addedElement.getParent() == null) {
          modelChangeSet.addPendingNewElement(addedElement);
        }
        // create composition list insertion
        elementaryChange = Utils.add(
            new CompositionListInsertion(affectedElement, feature,
                ((ParsedCompositionListInsertion) pec).index, addedElement),
            changeListToRevise.compositionListInsertions);
      } else {
        throw new RuntimeException("Unsupported change type " + pec.getClass().getSimpleName());
      }
      elementaryChange.setFeature(feature);
      elementaryChange.setAffectedElement(resolveModelElement(pec.affectedElement, modelChangeSet, parsedModelChangeSet));
      return elementaryChange;
    }
  }

  private ModelElement translateModelElement(ParsedModelElement parsedModelElement, ModelChangeSet modelChangeSet,
                                             ParsedModelChangeSet parsedModelChangeSet)
      throws ParseException {
    return translateModelElement(parsedModelElement, modelChangeSet, parsedModelChangeSet, true);
  }

  private ModelElement translateModelElement(ParsedModelElement parsedModelElement, ModelChangeSet modelChangeSet,
                                             ParsedModelChangeSet parsedModelChangeSet, boolean translateSubElements)
      throws ParseException {
    if (forwardReferencesElements.containsKey(parsedModelElement)) {
      return forwardReferencesElements.get(parsedModelElement);
    }
    ModelElement me;
    Long id = Long.valueOf(parsedModelElement.id.substring(parsedModelElement.id.lastIndexOf('#') + 1));
    SocialNetwork socialNetwork = modelChangeSet.getSocialNetwork();
    if (parsedModelElement instanceof ParsedUser) {
      ParsedUser parsedUser = (ParsedUser) parsedModelElement;
      User user = new User();
      if (parsedUser.friends != null) {
        String[] tokens = parsedUser.friends.split(" ");
        for (String token : tokens) {
          User friend = resolveUser(token, modelChangeSet);
          user.addFriend(friend);
        }
      }
      // maybe set other fields? (submission is set, but refers to later introduced changes)
      socialNetwork.addUser(user);
      userMap.put(id, user);
      me = user;
    } else if (parsedModelElement instanceof ParsedSubmission) {
      final ParsedSubmission parsedSubmission = (ParsedSubmission) parsedModelElement;
      final Submission submission;
      final boolean submissionIsAPost;
      if (parsedModelElement instanceof ParsedPost) {
        submission = new Post();
        submissionIsAPost = true;
        socialNetwork.addPost((Post) submission);
      } else if (parsedModelElement instanceof ParsedComment) {
        Comment comment = new Comment();
        submissionIsAPost = false;
        submission = comment;
      } else {
        // submission can either be post or comment, nothing else.
        throw new RuntimeException("Should never happen");
      }
      submission.setTimestamp(convertTimestamp(parsedSubmission.timestamp));
      submission.setContent(parsedSubmission.content);
      // add map
      submissionMap.put(id, submission);
      // resolveModelElement user
      User submitter = resolveUser(parsedSubmission.submitter, modelChangeSet);
      submitter.addSubmission(submission);
      // check if there are any comments attached
      if (parsedSubmission.comments != null) {
        for (ParsedComment parsedSubComment : parsedSubmission.comments) {
          // translate to real comment. just resolve element
          Comment subComment = (Comment) translateModelElement(parsedSubComment, modelChangeSet, parsedModelChangeSet, translateSubElements);
          if (submissionIsAPost) {
            // assume subComment.post == parsedSubmission
            submission.addComment(subComment);
          }
          // submitter for subComment is handled in this method already
          submission.addComment(subComment);
        }
      }
      me = submission;
    } else {
      throw new RuntimeException("Unknown ModelElement " + parsedModelElement +
          " with id=" + parsedModelElement.id);
    }
    me.setId(id);
    createdObjects.put(me.getId(), me);
    return me;
  }

  //  private ModelElement resolveModelElement(String elementSpec, ModelChangeSet modelChangeSet, ParsedModelChangeSet parsedModelChangeSet) throws ParseException {
//    return resolveModelElement(elementSpec, modelChangeSet, parsedModelChangeSet);
//  }
  private ModelElement resolveModelElement(String elementSpec, ModelChangeSet modelChangeSet, ParsedModelChangeSet parsedModelChangeSet) throws ParseException {
    return resolveModelElement(elementSpec, modelChangeSet, parsedModelChangeSet, true);
  }

  private ModelElement resolveModelElement(String elementSpec, ModelChangeSet modelChangeSet, ParsedModelChangeSet parsedModelChangeSet, boolean translateSubElements) throws ParseException {
    if (forwardReferencesPaths.containsKey(elementSpec)) {
      return forwardReferencesPaths.get(elementSpec);
    }
    // one example: "social:User initial.xmi#1274"
    // another example: "social:Comment #//@changes.1/@sourceChange/@addedElement"
    // general form: "'social:'<ClassName> ('initial.xmi#'<Id> | '#//@changes.'<sequenceId>'/@'<target> )

    // remove "social:"
    elementSpec = elementSpec.substring(7);
    String[] tokens = elementSpec.split(" ");
    String nonterminalName = tokens[0];
    String path = tokens[1];
    return Objects.requireNonNull(resolveModelElement(path, modelChangeSet, nonterminalName, parsedModelChangeSet, translateSubElements),
        () -> "Resolving object at '" + path + "' failed, " + createdObjects);
  }

  private User resolveUser(String path, ModelChangeSet modelChangeSet) throws ParseException {
    return (User) resolveModelElement(path, modelChangeSet, "User", null, true);
  }

  private ModelElement resolveModelElement(String path, ModelChangeSet modelChangeSet, String nonterminalName, ParsedModelChangeSet parsedModelChangeSet, boolean translateSubElements) throws ParseException {
    if (forwardReferencesPaths.containsKey(path)) {
      return forwardReferencesPaths.get(path);
    }
    if (nonterminalName.equals("SocialNetworkRoot")) {
      return modelChangeSet.getSocialNetwork();
    }
    long id;
    if (path.startsWith("initial")) {
      // refers to initial model
      id = Long.valueOf(path.substring(path.lastIndexOf('#') + 1));
    } else if (!path.contains("#")) {
      // strange notation without a '#'
      id = Long.valueOf(path);
    } else {
      // refers to an element created in a change
      // example: "#//@changes.1/@sourceChange/@addedElement"
      path = path.substring(4);
      if (path.startsWith("changes")) {
        // refers to changeSet
        int slashIndex = path.indexOf('/');
        int changeId = Integer.valueOf(path.substring(8, slashIndex));
        String remainder = path.substring(slashIndex + 1);
        if (modelChangeSet.getNumModelChange() <= changeId) {
          // resolve a forward reference, put the resolve element in the map "forwardReferences", and return here
          ParsedModelChange parsedModelChange = parsedModelChangeSet.changes.get(changeId);
          ModelElement result = resolvePathForward(remainder, parsedModelChange, modelChangeSet, parsedModelChangeSet, translateSubElements);
          forwardReferencesPaths.put(remainder, result);
          return result;
        }
        return resolvePath(remainder, modelChangeSet.getModelChange(changeId));
      } else if (path.startsWith("initial")) {
        // refers to initial - should not happen, right?
        System.err.println("Reference to initial. That was unexpected");
      }
      // giving up
      System.err.println("Can not resolveModelElement " + path);
      id = 0;
    }
    ModelElement result;
    switch (nonterminalName) {
      case "User":
        result = userMap.get(id);
        break;
      case "Comment":
        result = submissionMap.get(id);
        break;
      case "Post":
        result = submissionMap.get(id);
        break;
      default:
        throw new RuntimeException("Can not resolveModelElement a " + nonterminalName + " with " + path);
    }
    if (result == null) {
      // maybe the result is an model element, which is created within the changes
      result = createdObjects.get(id);
    }
    return result;
  }

  private ModelElement resolvePath(String remainder, ModelChange modelChange) {
    if (modelChange instanceof ChangeTransaction) {
      // either sourceChange
      if (remainder.startsWith("@sourceChange")) {
        ModelChange sourceChange = ((ChangeTransaction) modelChange).getSourceChange();
        int slashIndex = remainder.indexOf('/');
        return resolvePath(remainder.substring(slashIndex + 1), sourceChange);
      }
      // or something not supported yet
      System.err.println("Can not resolve ChangeTransaction " + remainder + " at " + modelChange);
      return null;
    } else if (modelChange instanceof AssociationCollectionInsertion) {
      if (remainder.startsWith("@addedElement")) {
        return ((AssociationCollectionInsertion) modelChange).getAddedElement();
      }
      System.err.println("Can not resolve AssociationCollectionInsertion " + remainder + " at " + modelChange);
      return null;
    } else if (modelChange instanceof CompositionListInsertion) {
      if (remainder.startsWith("@addedElement")) {
        return ((CompositionListInsertion) modelChange).getAddedElement();
      }
      System.err.println("Can not resolve CompositionListInsertion " + remainder + " at " + modelChange);
      return null;
    }
    System.err.println("Unknown change type: " + remainder + " at " + modelChange);
    return null;
  }

//  private ModelElement resolvePathForward(String remainder, ParsedModelChange parsedModelChange, ModelChangeSet modelChangeSet, ParsedModelChangeSet parsedModelChangeSet) throws ParseException {
//    return resolvePathForward(remainder, parsedModelChange, modelChangeSet, parsedModelChangeSet, true);
//  }

  private ModelElement resolvePathForward(String remainder, ParsedModelChange parsedModelChange, ModelChangeSet modelChangeSet, ParsedModelChangeSet parsedModelChangeSet, boolean translateSubElements) throws ParseException {
    if (parsedModelChange instanceof ParsedChangeTransaction) {
      // either sourceChange
      if (remainder.startsWith("@sourceChange")) {
        ParsedModelChange sourceChange = ((ParsedChangeTransaction) parsedModelChange).sourceChange;
        int slashIndex = remainder.indexOf('/');
        return resolvePathForward(remainder.substring(slashIndex + 1), sourceChange, modelChangeSet, parsedModelChangeSet, translateSubElements);
      }
      // or something not supported yet
      System.err.println("Can not forward resolve ChangeTransaction " + remainder + " at " + parsedModelChange);
      return null;
    } else if (parsedModelChange instanceof ParsedAssociationCollectionInsertion) {
      if (remainder.startsWith("@addedElement")) {
        // since addedElement is a String, i.e., a path expression, this has to be resolved
        String path = ((ParsedAssociationCollectionInsertion) parsedModelChange).addedElement;
        ModelElement result = resolveModelElement(path, modelChangeSet, parsedModelChangeSet, translateSubElements);
        forwardReferencesPaths.put(path, result);
        return result;
      }
      System.err.println("Can not forward resolve AssociationCollectionInsertion " + remainder + " at " + parsedModelChange);
      return null;
    } else if (parsedModelChange instanceof ParsedCompositionListInsertion) {
      if (remainder.startsWith("@addedElement")) {
        ParsedModelElement addedElement = ((ParsedCompositionListInsertion) parsedModelChange).addedElement;
        ModelElement result = translateModelElement(addedElement, modelChangeSet, parsedModelChangeSet, translateSubElements);
        forwardReferencesElements.put(addedElement, result);
        return result;
      }
      System.err.println("Can not forward resolve CompositionListInsertion " + remainder + " at " + parsedModelChange);
      return null;
    }
    System.err.println("Unknown forward change type: " + remainder + " at " + parsedModelChange);
    return null;
  }

}
