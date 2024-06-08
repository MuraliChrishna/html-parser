package com.bala.parser.service;

import com.bala.parser.filereader.FileReader;
import com.bala.parser.formatter.PlainTextParser;
import com.bala.parser.formatter.HeaderParser;
import com.bala.parser.writer.FileWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Service
public class FormatterService {

    private final FileReader fileReader;

    private final FileWriter fileWriter;

    private final HeaderParser headerParser;

    private final PlainTextParser plainTextParser;

    @Autowired
    public FormatterService (
        FileReader fileReader,
        FileWriter fileWriter,
        HeaderParser headerParser,
        PlainTextParser plainTextParser) {
        this.fileReader = fileReader;
        this.fileWriter = fileWriter;
        this.headerParser = headerParser;
        this.plainTextParser = plainTextParser;
    }

    /**
     * Function that gets path to the text file to be parsed, if the path is null it looks at the default path for a file to parse.
     *
     * @param filePath Nullable file path for the text doc to parse.
     * @return List<String> parsed to html.
     */
    public List<String> formatFile(@Nullable String filePath) {
        List<String> docLines = fileReader.readFile(filePath);
        List<String> parsedLines = new ArrayList<>();
        for (int i =0; i< docLines.size(); ++i) {
            String docLine = docLines.get(i);
            String trimmedLine = docLine.trim();
            if (trimmedLine.isEmpty()) {
                parsedLines.add(docLine);
                continue;
            }
            if (headerParser.isHeader(trimmedLine)) {
                parsedLines.add(headerParser.parseString(trimmedLine));
            } else {
                List<String> bigParagraph = new ArrayList<>();
                bigParagraph.add(trimmedLine);
                while (i+1 < docLines.size() && plainTextParser.isUnformattedText(docLines.get(i+1))) {
                    i += 1;
                    bigParagraph.add(docLines.get(i).trim());
                }
                parsedLines.addAll(plainTextParser.buildParagraph(bigParagraph));
            }
        }
        fileWriter.writeFile(parsedLines);
        return parsedLines;
    }

}
