package ttc2018;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import Changes.ChangesPackage;
import SocialNetwork.Comment;
import SocialNetwork.Post;
import SocialNetwork.SocialNetworkPackage;
import SocialNetwork.SocialNetworkRoot;
import SocialNetwork.Submission;
import SocialNetwork.User;

public class ModelConverter {
	public static final String modelBasePath = "../../models/";

	public enum ResourceType {
		XMI("xmi"), CSV("csv");

		public final String extension;
		ResourceType(String extension) {
			this.extension = extension;
		}
	}

	public static File getResourcePath(int size, String resourceName, ResourceType resourceType) {
		return new File(modelBasePath + size, resourceName + "." + resourceType.extension);
	}

	public static void main(String[] args) throws IOException {
		ModelConverter converter = new ModelConverter();

		int size = args.length>0?new Integer(args[0]):1;

		converter.load(size);
	}

    private SocialNetworkRoot loadSocialNetworkFile(int size) throws IOException {
    	ResourceSet repository;
    	repository = new ResourceSetImpl();
		repository.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		repository.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
		repository.getPackageRegistry().put(SocialNetworkPackage.eINSTANCE.getNsURI(), SocialNetworkPackage.eINSTANCE);
		repository.getPackageRegistry().put(ChangesPackage.eINSTANCE.getNsURI(), ChangesPackage.eINSTANCE);

    	Resource resource = repository.getResource(URI.createFileURI(getResourcePath(size, "initial", ResourceType.XMI).getCanonicalPath()), true);
    	return (SocialNetworkRoot) resource.getContents().get(0);
    }

    private void load(int size) throws IOException {
    	SocialNetworkRoot root = loadSocialNetworkFile(size);
    	
    	List<User> users = new ArrayList<>();
    	List<Post> posts = new ArrayList<>();
    	List<Comment> comments = new ArrayList<>();

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 'Z' should be added (with quotes) is needed
		// input is parsed as local date, we do so with on the output side
		//TimeZone tz = TimeZone.getTimeZone("UTC");
		//df.setTimeZone(tz);
		PrintWriter usersFile = new PrintWriter(getResourcePath(size, "csv-users-initial", ResourceType.CSV));
		PrintWriter postsFile = new PrintWriter(getResourcePath(size, "csv-posts-initial", ResourceType.CSV));
		PrintWriter commentsFile = new PrintWriter(getResourcePath(size, "csv-comments-initial", ResourceType.CSV));
		PrintWriter friendsFile = new PrintWriter(getResourcePath(size, "csv-friends-initial", ResourceType.CSV));
		PrintWriter likesFile = new PrintWriter(getResourcePath(size, "csv-likes-initial", ResourceType.CSV));

		root.eAllContents().forEachRemaining(x -> {
			if (x instanceof Submission) {
								
			}			
			
			if (x instanceof User) {
				users.add((User) x);
			} else if (x instanceof Post) {
				posts.add((Post) x);
			} else if (x instanceof Comment) {
				comments.add((Comment) x);
			}
    	});
		
		System.out.println(users.size() + " users");
		System.out.println(posts.size() + " posts");
		System.out.println(comments.size() + " comments");
		
		users.forEach(u -> {
				printCSV(usersFile, u.getId(), u.getName());
				// friends
				u.getFriends().forEach(f ->
					printCSV(friendsFile, u.getId(), f.getId())
				);
				// likes
				u.getLikes().forEach(l ->
					printCSV(likesFile, u.getId(), l.getId())
				);
			}
		);
		comments.forEach(c ->
			printCSV(commentsFile,
				c.getId()
			,	df.format(c.getTimestamp())
			,	c.getContent()
			,	c.getSubmitter().getId()
			,	c.getCommented().getId()
			,	c.getPost().getId()
			)
		);
		posts.forEach(p ->
			printCSV(postsFile,
				p.getId()
			,	df.format(p.getTimestamp())
			,	p.getContent()
			,	p.getSubmitter().getId()
			)
		);

		usersFile.close();
		postsFile.close();
		commentsFile.close();
		friendsFile.close();
		likesFile.close();
    }

    protected static void printCSV(PrintWriter writer, String... strings) {
		writer.println(String.join("|", strings));
	}

}
