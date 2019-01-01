package ttc2018;

import Changes.*;
import SocialNetwork.Comment;
import SocialNetwork.Post;
import SocialNetwork.Submission;
import SocialNetwork.User;
import org.eclipse.emf.ecore.EObject;
import ttc2018.sqlmodel.*;

import java.io.IOException;
import java.util.Date;

public class ModelChangeProcessor {
    public static void main(String[] args) throws IOException {
        ModelChangeProcessor converter = new ModelChangeProcessor();

        // processing initial.xmi of size 16 takes about 1 minute
        int size = args.length>0?new Integer(args[0]):16;
        int sequences = args.length>1?new Integer(args[1]):2;

        for (int i=1; i<=sequences; i++) {
            converter.load(size, i);
        }
    }

    Posts posts = new Posts();
    Comments comments = new Comments();
    Users users = new Users();
    Friends friends = new Friends();
    Likes likes = new Likes();

    private void load(int size, int sequence) throws IOException {
        ModelChangeSet root = ModelUtils.loadChangeSetFile(size, sequence);
System.out.println("change seq: "+sequence + " " + new Date());
        for (EObject x: root.getChanges()) {
            if (x instanceof ModelChange) {
                processChange((ModelChange) x);
            } else {
                throw new RuntimeException("Unexpected type received: " + x.eClass().toString());
            }
        };
        System.out.println(new Date());
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
                    break;

                case "comments":
                    Submission s = (Submission) cli.getAffectedElement();
                    Comment c = (Comment) cli.getAddedElement();
                    comments.addComment(
                            c.getId(),
                            c.getTimestamp(),
                            c.getContent(),
                            c.getSubmitter().getId(),
                            s.getId(),
                            c.getPost().getId());
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

    protected static void printCSV(String typeName, String... strings) {
        System.out.print(typeName+": ");
        System.out.println(String.join("|", strings));
    }

}
