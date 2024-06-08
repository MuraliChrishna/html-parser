package com.bala.parser.formatter;

import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class HeaderParser {

    private final PlainTextParser plainTextParser;

    @Autowired
    public HeaderParser(PlainTextParser plainTextParser) {
        this.plainTextParser = plainTextParser;
    }

    private static final Map<Integer, String[]> HEADER_TAG_MAP = ImmutableMap.of(
        1, new String[]{"<h1>", "</h1>"},
        2, new String[]{"<h2>", "</h2>"},
        3, new String[]{"<h3>", "</h3>"},
        4, new String[]{"<h4>", "</h4>"},
        5, new String[]{"<h5>", "</h5>"},
        6, new String[]{"<h6>", "</h6>"},
        -1, new String[]{"", ""}
    );

    /**
     * Given a non-empty string that starts with # at the beginning of the string, the function will add respective tags to the string.
     *
     * @param line non-empty string which needs to be parsed.
     * @return parsed string with necessary tags.
     */
    public String parseString(String line) {
        if (!isHeader(line)) {
            return line;
        }
        int count = getTag(line);
        String[] headerTag = HEADER_TAG_MAP.getOrDefault(count, new String[]{"", ""});
        if (count >= line.length()) {
            return headerTag[0] + headerTag[1];
        }
        return headerTag[0] + plainTextParser.getHyperLinks(line.substring(count+1)) + headerTag[1];
    }

    /**
     * Given a non-empty string that starts with #,
     * the function will return the index of last consecutive # at the beginning of the string.
     *
     * @param line non-empty input string that starts with #.
     * @return index of the last consecutive # at the beginning of the given string.
     */
    public int getTag(String line) {
        if (line.isEmpty()) {
            return -1;
        }
        int index = 0;
        while(index < line.length() && line.charAt(index) == '#') {
            if (index > 5) {
                return -1;
            }
            index += 1;
        }
        return index;
    }

    /**
     * Given a non-empty string, check if the string starts with #.
     *
     * @param line non-empty input string.
     * @return True if the string starts with # if not False.
     */
    public boolean isHeader(String line) {
        return !line.isEmpty() && line.charAt(0) == '#';
    }
}
