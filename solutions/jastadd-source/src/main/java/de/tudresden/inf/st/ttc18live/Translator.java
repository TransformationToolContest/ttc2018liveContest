package de.tudresden.inf.st.ttc18live;

import SocialNetwork.SocialNetworkRoot;
import de.tudresden.inf.st.ttc18live.jastadd.model.List;
import de.tudresden.inf.st.ttc18live.jastadd.model.Post;
import de.tudresden.inf.st.ttc18live.jastadd.model.User;
import de.tudresden.inf.st.ttc18live.jastadd.model.Comment;
import de.tudresden.inf.st.ttc18live.jastadd.model.Submission;
import de.tudresden.inf.st.ttc18live.jastadd.model.ModelElement;

import de.tudresden.inf.st.ttc18live.jastadd.model.ModelChange;
import de.tudresden.inf.st.ttc18live.jastadd.model.ChangeTransaction;
import de.tudresden.inf.st.ttc18live.jastadd.model.ModelChangeSet;
import de.tudresden.inf.st.ttc18live.jastadd.model.AssociationCollectionInsertion;
import de.tudresden.inf.st.ttc18live.jastadd.model.AssociationPropertyChange;
import de.tudresden.inf.st.ttc18live.jastadd.model.AttributionPropertyChange;
import de.tudresden.inf.st.ttc18live.jastadd.model.CompositionListInsertion;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;


import java.util.HashMap;
import java.util.Map;

public class Translator {

  private de.tudresden.inf.st.ttc18live.jastadd.model.SocialNetwork socialNetwork;

  private Map<EObject, ModelElement> elementMap = new HashMap<>();
  private Map<SocialNetwork.User,User> userMap = new HashMap<>();
  private Map<SocialNetwork.Submission,Submission> submissionMap = new HashMap<>();
  private Map<SocialNetwork.Comment,Comment> commentMap = new HashMap<>();
  private Map<Long, ModelElement> idMap = new HashMap<>();


  public Translator(SocialNetworkRoot emfNetwork) {

    List<User> users = new List<>();
    for (SocialNetwork.User emfUser : emfNetwork.getUsers()) {
      User user = new User();
      user.setId(Long.valueOf(emfUser.getId()));
      user.setName(emfUser.getName());
      userMap.put(emfUser, user);
      elementMap.put(emfUser, user);
      idMap.put(user.getId(), user);
      users.add(user);
    }

    List<Post> posts = new List<>();
    for (SocialNetwork.Post emfPost : emfNetwork.getPosts()) {
      Post post = new Post();
      post.setId(Long.valueOf(emfPost.getId()));
      post.setTimestamp(emfPost.getTimestamp().getTime());
      post.setContent(emfPost.getContent());
      for (SocialNetwork.Comment subComment : emfPost.getComments()) {
        post.addComment(translateComment(subComment));
      }
      submissionMap.put(emfPost, post);
      elementMap.put(emfPost, post);
      idMap.put(post.getId(), post);
      posts.add(post);
    }

    // fix user non-containment refs

    for (SocialNetwork.User emfUser : emfNetwork.getUsers()) {
      // fix friends
      User user = userMap.get(emfUser);
      for (SocialNetwork.User emfFriend : emfUser.getFriends()) {
        User friend = userMap.get(emfFriend);
        user.addFriend(friend);
      }

      // fix submissions
      for (SocialNetwork.Submission emfSubmission : emfUser.getSubmissions()) {
        Submission submission = submissionMap.get(emfSubmission);
        user.addSubmission(submission);
      }

      // fix likes
      for (SocialNetwork.Comment emfComment : emfUser.getLikes()) {
        Comment comment = commentMap.get(emfComment);
        user.addLike(comment);
      }
    }

    de.tudresden.inf.st.ttc18live.jastadd.model.SocialNetwork network;
    this.socialNetwork = new de.tudresden.inf.st.ttc18live.jastadd.model.SocialNetwork(-42L, users, posts);
    elementMap.put(emfNetwork, this.socialNetwork);
  }

  public de.tudresden.inf.st.ttc18live.jastadd.model.SocialNetwork getSocialNetwork() {
    return this.socialNetwork;
  }

  private Comment translateComment(SocialNetwork.Comment emfComment) {
    Comment comment = new Comment();
    comment.setId(Long.valueOf(emfComment.getId()));
    comment.setTimestamp(emfComment.getTimestamp().getTime());
    comment.setContent(emfComment.getContent());
    for (SocialNetwork.Comment subComment : emfComment.getComments()) {
      comment.addComment(translateComment(subComment));
    }

    commentMap.put(emfComment, comment);
    submissionMap.put(emfComment, comment);
    elementMap.put(emfComment, comment);
    idMap.put(comment.getId(), comment);
    return comment;
  }

  public ModelChangeSet translateChangeSet(Changes.ModelChangeSet emfChanges) {

    ModelChangeSet changes = new ModelChangeSet();

    List<ModelElement> pendingNewElements = new List<>();

    // translate model changes
    for (Changes.ModelChange emfChange : emfChanges.getChanges()) {
      changes.addModelChange(translateModelChange(emfChange, pendingNewElements));
    }

    // translate pending new elements
    changes.setPendingNewElementList(pendingNewElements);

    // set social network
    changes.setSocialNetwork(this.socialNetwork);

    return changes;
  }

  private ModelElement addModelElement(EObject addedEmfElement, List<ModelElement> pendingNewElements) {

    // case 1: element is proxy
    if (addedEmfElement.eIsProxy()) {
      URI proxyURI = ((InternalEObject) addedEmfElement).eProxyURI();

      // resolve
      if (proxyURI.toFileString().endsWith("initial.xmi")) {
        return idMap.get(Long.valueOf(proxyURI.fragment()));
      }

      throw new RuntimeException();

    } else {
      ModelElement addedElement = elementMap.get(addedEmfElement);

      if (addedElement != null) {
        return addedElement;
      }

      if (addedEmfElement instanceof SocialNetwork.Post) {
        SocialNetwork.Post emfPost = (SocialNetwork.Post) addedEmfElement;
        return addPost(emfPost, pendingNewElements);
      } else if (addedEmfElement instanceof SocialNetwork.User) {
        SocialNetwork.User emfUser = (SocialNetwork.User) addedEmfElement;
        return addUser(emfUser, pendingNewElements);
      } else if (addedEmfElement instanceof SocialNetwork.Comment) {
        SocialNetwork.Comment emfComment = (SocialNetwork.Comment) addedEmfElement;
        Comment comment = addComment(emfComment, pendingNewElements);
        return comment;
      } else {
        throw new RuntimeException();
      }
    }

  }

  private Comment addComment(SocialNetwork.Comment emfComment, List<ModelElement> pendingNewElements) {
    Comment comment = new Comment();
    comment.setId(Long.valueOf(emfComment.getId()));
    comment.setTimestamp(emfComment.getTimestamp().getTime());
    comment.setContent(emfComment.getContent());
    elementMap.put(emfComment, comment);
    submissionMap.put(emfComment, comment);
    idMap.put(comment.getId(), comment);
    pendingNewElements.add(comment);

    for (SocialNetwork.Comment emfSubComment : emfComment.getComments()) {
      comment.addComment((Comment)addModelElement(emfSubComment, pendingNewElements));
    }
    return comment;
  }

  private User addUser(SocialNetwork.User emfUser, List<ModelElement> pendingNewElements) {
    User user = new User();
    user.setId(Long.valueOf(emfUser.getId()));
    user.setName(emfUser.getName());
    elementMap.put(emfUser, user);
    userMap.put(emfUser, user);
    idMap.put(user.getId(), user);
    pendingNewElements.add(user);
    return user;
  }

  private Post addPost(SocialNetwork.Post emfPost, List<ModelElement> pendingNewElements) {
    Post post = new Post();
    post.setId(Long.valueOf(emfPost.getId()));
    post.setTimestamp(emfPost.getTimestamp().getTime());
    post.setContent(emfPost.getContent());
    elementMap.put(emfPost, post);
    submissionMap.put(emfPost, post);
    idMap.put(post.getId(), post);
    pendingNewElements.add(post);
    for (SocialNetwork.Comment emfSubComment : emfPost.getComments()) {
      post.addComment((Comment)addModelElement(emfSubComment, pendingNewElements));
    }
    return post;
  }

  private ModelChange translateModelChange(Changes.ModelChange emfChange, List<ModelElement> pendingNewElements) {

    if (emfChange instanceof Changes.ChangeTransaction) {
      Changes.ChangeTransaction emfChangeTransaction = (Changes.ChangeTransaction) emfChange;
      ChangeTransaction change = new ChangeTransaction();
      change.setSourceChange(translateModelChange(emfChangeTransaction.getSourceChange(), pendingNewElements));
      for (Changes.ModelChange emfNestedChange : emfChangeTransaction.getNestedChanges()) {
        change.addNestedChange(translateModelChange(emfNestedChange, pendingNewElements));
      }
      return change;
    } else if (emfChange instanceof Changes.AssociationCollectionInsertion) {
      Changes.AssociationCollectionInsertion emfAssociationCollectionInsertion = (Changes.AssociationCollectionInsertion) emfChange;
      AssociationCollectionInsertion change = new AssociationCollectionInsertion();

      // affected element
      ModelElement affectedElement = addModelElement(emfAssociationCollectionInsertion.getAffectedElement(), pendingNewElements);
      change.setAffectedElement(affectedElement);

      // feature
      change.setFeature(emfAssociationCollectionInsertion.getFeature().getName());

      // added element
      EObject addedEmfElement = emfAssociationCollectionInsertion.getAddedElement();
      change.setAddedElement(addModelElement(addedEmfElement, pendingNewElements));

      return change;
    } else if (emfChange instanceof Changes.AssociationPropertyChange) {
      Changes.AssociationPropertyChange emfAssociationPropertyChange = (Changes.AssociationPropertyChange) emfChange;
      AssociationPropertyChange change = new AssociationPropertyChange();

      // affected element
      ModelElement affectedElement = addModelElement(emfAssociationPropertyChange.getAffectedElement(), pendingNewElements);
      change.setAffectedElement(affectedElement);

      // feature
      change.setFeature(emfAssociationPropertyChange.getFeature().getName());

      // new value
      ModelElement newValue = addModelElement(emfAssociationPropertyChange.getNewValue(), pendingNewElements);
      change.setNewValue(newValue);

      return change;
    } else if (emfChange instanceof Changes.AttributePropertyChange) {
      Changes.AttributePropertyChange emfAttributePropertyChange = (Changes.AttributePropertyChange) emfChange;
      AttributionPropertyChange change = new AttributionPropertyChange();

      // affected element
      ModelElement affectedElement = addModelElement(emfAttributePropertyChange.getAffectedElement(), pendingNewElements);
      change.setAffectedElement(affectedElement);

      // feature
      change.setFeature(emfAttributePropertyChange.getFeature().getName());

      // new value
      change.setNewValue(emfAttributePropertyChange.getNewValue());

      return change;
    } else if (emfChange instanceof Changes.CompositionListInsertion){
      Changes.CompositionListInsertion emfCompositionListInsertion = (Changes.CompositionListInsertion) emfChange;
      CompositionListInsertion change = new CompositionListInsertion();

      // affected element
      ModelElement affectedElement = addModelElement(emfCompositionListInsertion.getAffectedElement(), pendingNewElements);
      change.setAffectedElement(affectedElement);

      // feature
      change.setFeature(emfCompositionListInsertion.getFeature().getName());

      // index
      change.setIndex(emfCompositionListInsertion.getIndex());

      // added element
      EObject addedEmfElement = emfCompositionListInsertion.getAddedElement();
      change.setAddedElement(addModelElement(addedEmfElement, pendingNewElements));

      return change;
    }

    throw new RuntimeException("unsupported change found!");
  }

}
