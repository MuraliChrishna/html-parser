package com.bala.parser.filereader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class FileReader {

    private static final String DEFAULT_READ_PATH = "/home/balapolisetti/Documents/Projects/parserExamples/input.txt";

    /**
     * function to read file from a given path if provide if not DEFAULT_READ_PATH and parse with html tags.
     *
     * @param receivedPath nullable path for a file to parse with html tags.
     * @return List<String> represents each line in the doc parse to HTML tags.
     */
    public List<String> readFile(@Nullable String receivedPath) {
        Path filePath = receivedPath != null
                ? Paths.get(receivedPath)
                : Paths.get(DEFAULT_READ_PATH);
        List<String> lines = new ArrayList<>();
        try {
             lines = Files.readAllLines(filePath);
            for (String line : lines) {
                System.out.println(line);
            }
            return lines;
        } catch (IOException e) {
            String message = String.format(
                "Error occurred while trying to get file :- %s",
                receivedPath != null ? receivedPath : DEFAULT_READ_PATH);
            log.error(message, e);
            return lines;
        }
    }
}
