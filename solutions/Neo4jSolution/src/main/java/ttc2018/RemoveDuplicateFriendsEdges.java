package ttc2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static ttc2018.Solution.SEPARATOR;

public class RemoveDuplicateFriendsEdges {
    public static void main(String[] args) throws IOException {
        String oldFileName = "graph-friends-initial.csv";
        String tempFileName = "temp.csv";

        Files.walk(Paths.get("models"))
                .filter(path -> path.getFileName().toString().equals(oldFileName)).forEach(path -> {
            Path tempPath = path.resolveSibling(tempFileName);

            try {
                Stream<CharSequence> filteredLines = Files.lines(path).filter(line -> {
                    if (line.startsWith(":"))
                        return true;
                    else {
                        String[] fields = line.split(Pattern.quote(SEPARATOR));
                        long[] longFields = Arrays.stream(fields).mapToLong(Long::parseLong).toArray();

                        // compare IDs to filter duplicates
                        return longFields[0] <= longFields[1];
                    }
                }).map(str -> str);

                Files.write(tempPath, filteredLines::iterator);

                Files.move(tempPath, path, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
