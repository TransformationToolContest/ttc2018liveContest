package ttc2018;

import SocialNetwork.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ModelToGraphConverter {
	public static void main(String[] args) throws IOException {
		ModelToGraphConverter converter = new ModelToGraphConverter();

		int size = args.length>0?new Integer(args[0]):1;

		converter.load(size);
	}

    private void load(int size) throws IOException {
    	SocialNetworkRoot root = ModelUtils.loadSocialNetworkFile(size);
    	
    	List<User> users = new ArrayList<>();
    	List<Post> posts = new ArrayList<>();
    	List<Comment> comments = new ArrayList<>();

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 'Z' should be added (with quotes) is needed
		// input is parsed as local date, we do so with on the output side
		//TimeZone tz = TimeZone.getTimeZone("UTC");
		//df.setTimeZone(tz);
		PrintWriter usersFile = new PrintWriter(ModelUtils.getResourcePath(size, "graph-users-initial", ResourceType.CSV));
		PrintWriter postsFile = new PrintWriter(ModelUtils.getResourcePath(size, "graph-posts-initial", ResourceType.CSV));
		PrintWriter commentsFile = new PrintWriter(ModelUtils.getResourcePath(size, "graph-comments-initial", ResourceType.CSV));

		PrintWriter friendsFile = new PrintWriter(ModelUtils.getResourcePath(size, "graph-friends-initial", ResourceType.CSV));
		PrintWriter likesFile = new PrintWriter(ModelUtils.getResourcePath(size, "graph-likes-initial", ResourceType.CSV));
		PrintWriter commentToFile = new PrintWriter(ModelUtils.getResourcePath(size, "graph-comment-to-initial", ResourceType.CSV));
		PrintWriter rootPostFile = new PrintWriter(ModelUtils.getResourcePath(size, "graph-root-post-initial", ResourceType.CSV));
		PrintWriter submitterFile = new PrintWriter(ModelUtils.getResourcePath(size, "graph-submitter-initial", ResourceType.CSV));

		printCSV(usersFile, "id:ID", "name:STRING");
		printCSV(postsFile, "id:ID", "timestamp:STRING", "content:STRING");
		printCSV(commentsFile, "id:ID", "timestamp:STRING", "content:STRING");

		printCSV(friendsFile,   ":START_ID", ":END_ID");
		printCSV(likesFile,     ":START_ID", ":END_ID");
		printCSV(commentToFile, ":START_ID", ":END_ID");
		printCSV(rootPostFile,  ":START_ID", ":END_ID");
		printCSV(submitterFile, ":START_ID", ":END_ID");

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
				u.getFriends().forEach(f -> {
					String uId = u.getId();
					String fId = f.getId();

					// only in one direction
					if (Long.parseLong(uId) <= Long.parseLong(fId))
						printCSV(friendsFile, uId, fId);
				});
				// likes
				u.getLikes().forEach(l ->
					printCSV(likesFile, u.getId(), l.getId())
				);
			}
		);
		comments.forEach(c -> {
			printCSV(commentsFile,
				c.getId()
			,	df.format(c.getTimestamp())
			,	c.getContent()
			);
			printCSV(submitterFile,
				c.getId()
			,	c.getSubmitter().getId()
			);
			printCSV(commentToFile,
				c.getId()
			,	c.getCommented().getId()
			);
			printCSV(rootPostFile,
				c.getId()
			,	c.getPost().getId()
			);
		});
		posts.forEach(p -> {
			printCSV(postsFile,
				p.getId()
			,	df.format(p.getTimestamp())
			,	p.getContent()
			);
			printCSV(submitterFile,
				p.getId()
			,	p.getSubmitter().getId()
			);
		});

		usersFile.close();
		postsFile.close();
		commentsFile.close();

		friendsFile.close();
		likesFile.close();
		commentToFile.close();
		rootPostFile.close();
		submitterFile.close();
    }

    protected static void printCSV(PrintWriter writer, String... strings) {
		writer.println(String.join("|", strings));
	}

}
