package ttc2018;

import Changes.*;
import SocialNetwork.Comment;
import SocialNetwork.Post;
import SocialNetwork.Submission;
import SocialNetwork.User;
import ttc2018.sqlmodel.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.Date;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class ModelChangeProcessor {
    public static void main(String[] args) throws IOException {
        ModelChangeProcessor converter = new ModelChangeProcessor();

        // processing initial.xmi of size 16 takes about 1 minute
        int size = args.length>0?new Integer(args[0]):16;
        int sequences = args.length>1?new Integer(args[1]):2;

        PrintStream ps;
        for (int i=1; i<=sequences; i++) {
            if (SqlCollectionBase.DO_PRINT) {
                ps = new PrintStream(ModelUtils.getChangesetCSVFile(size, i));
                converter.resetCollections(ps);
            }

            converter.load(size, i);

            if (SqlCollectionBase.DO_PRINT) {
                ps.close();
            }
        }
    }

    ModelChangeProcessor() {
        resetCollections();
    }

    Posts posts;
    Comments comments;
    Users users;
    Friends friends;
    Likes likes;

    void resetCollections() {
        posts = new Posts();
        comments = new Comments();
        users = new Users();
        friends = new Friends();
        likes = new Likes();
    }
    void resetCollections(PrintStream ps) {
        resetCollections();
        for(SqlCollectionBase c: getCollections()) {
            c.setOut(ps);
        }
    }
    SqlCollectionBase[] getCollections() {
        return new SqlCollectionBase[] {posts, comments, users, friends, likes};
    }

    private void load(int size, int sequence) throws IOException {
        ModelChangeSet root = ModelUtils.loadChangeSetFile(size, sequence);
        System.out.println("change seq: "+sequence + " " + new Date());
        processChangeSet(root);
        System.out.println(new Date());
    }

    public void processChangeSet(File changeSet) {
        try (Stream<String> stream = Files.lines(changeSet.toPath())) {
            stream.forEachOrdered(s -> {
                String[] line = s.split(Pattern.quote(SqlCollectionBase.SEPARATOR));
                switch (line[0]) {
                    case "Comments":
                        comments.addComment(line[1], line[2], line[3], line[4], line[5], line[6]);
                        break;
                    case "Friends":
                        friends.addFriend(line[1], line[2]);
                        break;
                    case "Likes":
                        likes.addLike(line[1], line[2]);
                        break;
                    case "Posts":
                        posts.addPost(line[1], line[2], line[3], line[4]);
                        break;
                    case "Users":
                        users.addUser(line[1], line[2]);
                        break;
                    default:
                        throw new RuntimeException("Invalid record type received from CSV input: "+line[0]);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void processChangeSet(ModelChangeSet changeSet) {
        for (ModelChange change: changeSet.getChanges()) {
            processChange(change);
            change.apply();
        }
    }

    // the logic here was copied from NaiadSolution by @antaljanosbenjamin
    private void processChange(ModelChange change) {
        if (change instanceof AssociationCollectionInsertion) {
            AssociationCollectionInsertion aci = (AssociationCollectionInsertion) change;
            switch (aci.getFeature().getName()) {
                case "likedBy":
                    Comment c = (Comment) aci.getAffectedElement();
                    User u = (User) aci.getAddedElement();
                    likes.addLike(u.getId(), c.getId());

                    break;

                case "likes":
                    // E.g.:
                    // <changes xsi:type="changes:ChangeTransaction">
                    //   <sourceChange xsi:type="changes:AssociationCollectionInsertion" addedElement="social:Comment initial.xmi#406915" affectedElement="social:User initial.xmi#1259" feature="ecore:EReference https://www.transformation-tool-contest.eu/2018/social_media#//User/likes" />
                    //   <nestedChanges xsi:type="changes:AssociationCollectionInsertion" addedElement="social:User initial.xmi#1259" affectedElement="social:Comment initial.xmi#406915" feature="ecore:EReference https://www.transformation-tool-contest.eu/2018/social_media#//Comment/likedBy" />
                    // </changes>
                    // We handle the opposite direction in above statement
                    break;

                case "submissions":
                    // E.g.:
                    // <changes xsi:type="changes:AssociationCollectionInsertion" addedElement="social:Post #//@changes.3/@addedElement" affectedElement="social:User initial.xmi#1269" feature="ecore:EReference https://www.transformation-tool-contest.eu/2018/social_media#//User/submissions" />
                    // <changes xsi:type="changes:CompositionListInsertion" index="889" affectedElement="social:SocialNetworkRoot initial.xmi#/" feature="ecore:EReference https://www.transformation-tool-contest.eu/2018/social_media#//SocialNetworkRoot/posts">
                    //   <addedElement xmlns:social="https://www.transformation-tool-contest.eu/2018/social_media" xsi:type="social:Post" id="255833" timestamp="2010-03-10T15:43:59" content="photo255833.jpg" submitter="initial.xmi#1269" />
                    // </changes>
                    // We handle the opposite direction in ICompositionListInsertion->posts and comments
                    break;

                case "friends":
                    User user1 = (User) aci.getAffectedElement();
                    User user2 = (User) aci.getAddedElement();
                    friends.addFriend(user1.getId(), user2.getId());
                    break;

                default:
                    throw new RuntimeException("Unexpected feature received: " + aci.getFeature().getName());
            }
        } else if (change instanceof AssociationPropertyChange) {
            AssociationPropertyChange apc = (AssociationPropertyChange) change;
            switch (apc.getFeature().getName()) {
                case "commented":
                    // E.g.:
                    //< changes xsi: type = "changes:ChangeTransaction" >
                    //    < sourceChange xsi: type = "changes:CompositionListInsertion" index = "1" affectedElement = "social:Comment initial.xmi#725040" feature = "ecore:EReference https://www.transformation-tool-contest.eu/2018/social_media#//Submission/comments" >
                    //        < addedElement xmlns: social = "https://www.transformation-tool-contest.eu/2018/social_media" xsi: type = "social:Comment" post = "initial.xmi#723156" id = "725041" timestamp = "2010-03-10T15:31:35" content = "About Joseph Haydn, Michael Haydn, himself . About Frédéric Chopin, are for solo piano,." submitter = "initial.xmi#4281" />
                    //    </ sourceChange >
                    //    < nestedChanges xsi: type = "changes:AssociationPropertyChange" newValue = "social:Comment initial.xmi#725040" affectedElement = "social:Comment #//@changes.1/@sourceChange/@addedElement" feature = "ecore:EReference https://www.transformation-tool-contest.eu/2018/social_media#//Comment/commented" />
                    //</ changes >
                    // We handle the opposite direction in ICompositionListInsertion->comments
                    break;

                default:
                    throw new RuntimeException("Unexpected feature received: " + apc.getFeature().getName());
            }
        } else if (change instanceof ChangeTransaction) {
            ChangeTransaction ct = (ChangeTransaction) change;
            processChange(ct.getSourceChange());

            for (ModelChange nc: ct.getNestedChanges()) {
                processChange(nc);
            }
        } else if (change instanceof CompositionListInsertion) {
            CompositionListInsertion cli = (CompositionListInsertion) change;
            switch (cli.getFeature().getName()) {
                case "posts":
                    Post p = (Post) cli.getAddedElement();
                    posts.addPost(p.getId(), p.getTimestamp(), p.getContent(), p.getSubmitter().getId());
                    // add child comment tree
                    for(Comment child: p.getComments()) {
                        addCommentTree(p, child);
                    }
                    break;

                case "comments":
                    Submission s = (Submission) cli.getAffectedElement();
                    Comment c = (Comment) cli.getAddedElement();
                    addCommentTree(s, c);
                    break;

                case "users":
                    User u = (User) cli.getAddedElement();
                    users.addUser(u.getId(), u.getName());
                    break;

                default:
                    throw new RuntimeException("Unexpected feature received: " + cli.getFeature().getName());
            }
        } else if (change instanceof AttributePropertyChange) {
            AttributePropertyChange attrpc = (AttributePropertyChange) change;
            // TODO check it, this is an update not a new insert....
            switch (attrpc.getFeature().getName()) {
                case "name":
                    break;

                default:
                    throw new RuntimeException("Unexpected feature received: " + attrpc.getFeature().getName());
            }

        } else {
            throw new RuntimeException("Unexpected change type received: " + change.getClass().getName());
        }
    }

    /**
     * Adds a comment along with its recursive child comment tree
     * @param original the Submission the comment refers to
     * @param comment the Comment itself to add
     */
    private void addCommentTree(Submission original, Comment comment) {
        comments.addComment(
                comment.getId(),
                comment.getTimestamp(),
                comment.getContent(),
                comment.getSubmitter().getId(),
                original.getId(),
                comment.getPost().getId());
        // add child comment tree
        for(Comment child: comment.getComments()) {
            addCommentTree(comment, child);
        }
    }
}
