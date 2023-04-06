package org.example.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CypherSplitUtil {

    public static List<String> processFile(Path path, Function<String, String[]> func) {

        String content = null;

        try {
            content = Files.readString(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Optional<String> wrappedContent = Optional.of(content);


        var split_content = Stream.of(wrappedContent.get())
                .map(func)
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());

        return split_content;
    }
}