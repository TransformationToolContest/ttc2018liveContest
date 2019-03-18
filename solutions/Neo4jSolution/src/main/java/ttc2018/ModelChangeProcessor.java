package ttc2018;

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
}
