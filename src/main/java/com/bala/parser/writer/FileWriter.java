package com.bala.parser.writer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Service
public class FileWriter {

    private static final String DEFAULT_WRITE_PATH = "/home/balapolisetti/Documents/Projects/parserExamples/output.html";

    /**
     * Function to write the parsed lines in a given doc path to a new file with HTML tags.
     *
     * @param parsedStrings list of strings that are parsed to HTML that needs to be written to the result doc.
     */
    public void writeFile(List<String> parsedStrings) {
        Path filePath = Paths.get(DEFAULT_WRITE_PATH);
        try {
            Files.deleteIfExists(filePath);
            Files.write(filePath, parsedStrings);
        } catch (IOException e) {
            String message = String.format("Failed to write the output file to the path :- %s", DEFAULT_WRITE_PATH);
            log.error(message, e);
        }
    }
}
