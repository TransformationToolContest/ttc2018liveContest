package de.tudresden.inf.st.ttc18live;

import de.tudresden.inf.st.ttc18live.jastadd.model.*;
import de.tudresden.inf.st.ttc18live.parser.ParsedSocialNetwork;
import de.tudresden.inf.st.ttc18live.parser.change.ParsedModelChangeSet;
import de.tudresden.inf.st.ttc18live.translator.XmlToJastaddTranslator;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("FieldCanBeLocal")
public class Main {

  private static final boolean splitApply = true;
  private static final int changeSet = 128;
  private static final int maxChanges = 20;

  private static User mkUser(long id, SocialNetwork sn, Comment... likes) {
    User result = new User();
    result.setId(id);
    sn.addUser(result);
    for (Comment comment : likes) {
      result.addLike(comment);
    }
    return result;
  }

  private static void addFriends(User one, User... others) {
    for (User other : others) {
      one.addFriend(other);
    }
  }

  private static Comment mkComment(long id, Submission parent) {
    Comment comment = new Comment();
    comment.setId(id);
    comment.setTimestamp(id);
    parent.addComment(comment);
    return comment;
  }

  @SuppressWarnings("unused")
  private static void test() {
    SocialNetwork sn = SocialNetwork.createSocialNetwork();

    Post post = new Post();
    post.setId(100L);
    sn.addPost(post);

    Comment comment1 = mkComment(1000, post);
    Comment comment2 = mkComment(1001, comment1);
    Comment comment3 = mkComment(1002, post);
    Comment comment4 = mkComment(1003, post);
    comment4.setTimestamp(9999L); // very new comment

    User u1 = mkUser(1, sn, comment1);
    User u2 = mkUser(2, sn, comment1, comment2);
    User u3 = mkUser(3, sn, comment1, comment2, comment3);
    User u4 = mkUser(4, sn, comment1, comment4);
    addFriends(u1, u2);
    addFriends(u2, u3);
    addFriends(u3, u4);

    report(sn, u1);
  }

  private static void report(SocialNetwork sn, User interestingUser) {
    sn.flushTreeCache();

    for (Comment comment : sn.comments()) {
      System.out.println("u3.getCommentLikerFriends(" + comment + "): " +
          interestingUser.getCommentLikerFriends(comment));
    }
    for (Comment comment : sn.comments()) {
      System.out.println(comment + ".score: " + comment.score());
    }

    System.out.println("sn.query2: " + sn.query(2));

    System.exit(0);
  }

  public static void main(String[] args) throws Exception {
//    test();
    XmlToJastaddTranslator translator = parseSocialNetwork();
    SocialNetwork socialNetwork = translator.getSocialNetwork();
    socialNetwork.enableTracing();
    System.out.println("Validity: " + socialNetwork.isValid());

//    JsonSerializer.write(socialNetwork, "serializedModelOne");
    System.out.println("JastAdd model with " + socialNetwork.getNumUser() +" users and "
        + socialNetwork.getNumPost() + " posts");

    System.out.println("Query1: " + socialNetwork.query(1));
    System.out.println("Query2: " + socialNetwork.query(2));
//    printQ2Scores(socialNetwork);

    for (int index = 1; index <= maxChanges; index++) {
      ModelChangeSet modelChangeSet = parseChanges(translator, index);
      if (modelChangeSet == null) {
        System.out.println("Got null as modelChangeSet, skipping.");
        continue;
      }
      System.out.println("Validity after parsing of " + modelChangeSet.getNumModelChange()
              + " changes: " + socialNetwork.isValid());

      // flush to get ids correct after parsing
      socialNetwork.flushTreeCache();

      if (splitApply) {
        // copy code of modelChangeSet.apply
        for (ModelChange change : modelChangeSet.getModelChangeList()) {
          change.apply();
          // flush to update for changes
          modelChangeSet.flushTreeCache();
          socialNetwork.flushTreeCache();
          // and validate
          socialNetwork.isValid();
        }
      } else {
        modelChangeSet.apply();
        // flush to update for changes
        socialNetwork.flushTreeCache();
      }

      System.out.println("Validity after application of changes: " + socialNetwork.isValid());

      System.out.println("Query1 after change " + index + ": " + socialNetwork.query(1));
      System.out.println("Query2 after change " + index + ": " + socialNetwork.query(2));

//      printQ2Scores(socialNetwork);
    }
    socialNetwork.writeTracingEvents(Paths.get("events.txt"));
  }

  private static void printQ2Scores(SocialNetwork socialNetwork) {
    java.util.List<Comment> scoringComments = socialNetwork.comments().stream()
        .filter(c -> c.score() > 0)
        .collect(Collectors.toList());
    System.out.println("Scores Q2: " +
        scoringComments.stream()
            .map(c -> c.toString() + ":(" + c.score() + "." + c.getTimestamp() + ")")
            .collect(Collectors.joining(", ")));
    for (Comment comment : scoringComments) {
      System.out.println(comment + " likedBy " + comment.getLikedByList());
      for (User user : comment.getLikedByList()) {
        System.out.println(comment + " and " + user + ": " + user.getCommentLikerFriends(comment));
        System.out.println(user + " friends: " + printList(user.getFriends()));
      }
    }
  }

  private static String printList(List<User> userList) {
    StringBuilder sb = new StringBuilder("[");
    boolean first = true;
    for (User user : userList) {
      if (first) {
        first = false;
      } else {
        sb.append(", ");
      }
      sb.append(user);
    }
    return sb.append("]").toString();
  }

  private static XmlToJastaddTranslator parseSocialNetwork() throws JAXBException, ParseException {
    JAXBContext jc = JAXBContext.newInstance(ParsedSocialNetwork.class);
    Unmarshaller unmarshaller = jc.createUnmarshaller();
    Path model1Content = Paths.get("src", "test", "resources", Integer.toString(changeSet), "initial.xmi");
    ParsedSocialNetwork parsedSocialNetwork = (ParsedSocialNetwork) unmarshaller.unmarshal(model1Content.toFile());
    System.out.println("Users: " + parsedSocialNetwork.users.size()
        + ", Posts: " + parsedSocialNetwork.posts.size());
    XmlToJastaddTranslator translator = new XmlToJastaddTranslator();
    translator.translateSocialNetwork(parsedSocialNetwork);
    return translator;
  }

  private static ModelChangeSet parseChanges(XmlToJastaddTranslator translator, int index) throws JAXBException, ParseException {
    String suffix = String.format("change%02d.xmi", index);
    System.out.println("Parsing changes " + suffix);
    JAXBContext jc = JAXBContext.newInstance(ParsedModelChangeSet.class);
    Unmarshaller unmarshaller = jc.createUnmarshaller();
    unmarshaller.setEventHandler(new jakarta.xml.bind.helpers.DefaultValidationEventHandler());
    Path modelContent = Paths.get("src", "test", "resources", Integer.toString(changeSet), suffix);
    if (!modelContent.toFile().exists()) {
      System.err.println("File " + modelContent + " does not exist!");
      return null;
    }
    ParsedModelChangeSet parsedModelChangeSet = (ParsedModelChangeSet) unmarshaller.unmarshal(modelContent.toFile());

    return translator.translateModelChangeSet(parsedModelChangeSet);
  }
}
