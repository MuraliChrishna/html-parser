package com.bala.parser.formatter;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlainTextParser {

    /**
     * function to check if a given string is unformatted string or not.
     *
     * @param line actual input string.
     * @return True if is Unformatted string else False.
     */
    public boolean isUnformattedText(String line) {
        return !line.isEmpty() && line.charAt(0) != '#';
    }

    /**
     * Given a non-empty string the function will add <p></p> at the beginning and the end.
     *
     * @param lines actual input string which need be modified
     * @return modified strings with param tag.
     */
    public List<String> buildParagraph(List<String> lines) {
        List<String> parsedStrings = new ArrayList<>();
        for (String line : lines) {
            parsedStrings.add(getHyperLinks(line));
        }
        parsedStrings.set(0, "<p>"+parsedStrings.get(0));
        parsedStrings.set(parsedStrings.size()-1, parsedStrings.get(parsedStrings.size()-1)+"</p>");
        return parsedStrings;
    }

    /**
     * Given a non-empty string, this function will add hyperlink tags, where ever possible to the string.
     *
     * @param line non-empty input string.
     * @return string with hyperlinks if possible to add.
     */
    public String getHyperLinks(String line) {
        StringBuilder parsedString = new StringBuilder();
        for (int i = 0; i < line.length(); ++i) {
            if (line.charAt(i) == '[') {
                int[] isEndIndex = getEnd(line, i+1);
                if (isEndIndex[3] == -1 || isEndIndex[3] == line.length()) {
                    return parsedString + line.substring(i);
                } else {
                    parsedString.append(String.format("<a href=\"%s\">%s</a>",
                            line.substring(isEndIndex[2]+1, isEndIndex[3]),
                            line.substring(isEndIndex[0], isEndIndex[1])));
                    i = isEndIndex[3];
                }
            } else {
                parsedString.append(line.charAt(i));
            }
        }
        return parsedString.toString();
    }

    /**
     * Given index of '[', the function will return array of open and close index of [] and ().
     *
     *
     * @param text actual input string.
     * @param index index of '['.
     * @return array of integers representing indexes of [] and ().
     */
    public int[] getEnd(String text, int index) {
        int count = 1;
        int lastOpenIndex = index;
        int[] status = {-1, -1, -1, -1};
        for (int i = index + 1; i < text.length(); i++) {
            if (text.charAt(i) == '[') {
                lastOpenIndex = i;
                count += 1;
            } else if (text.charAt(i) == ']') {
                if (i + 1 < text.length()) {
                    if (text.charAt(i + 1) != '(') {
                        if (count > 1) {
                            count -= 1;
                        } else {
                            break;
                        }
                    } else {
                        if (count == 1) {
                            return new int[]{index, i, i + 1, getHyperLinkEnd(text, i + 1)};
                        } else {
                            return new int[]{lastOpenIndex, i, i + 1, getHyperLinkEnd(text, i + 1)};
                        }
                    }
                } else {
                    break;
                }
            }
        }
        return status;
    }

    /**
     * Given index of '(', function will return the close index.
     *
     * @param text actual input string.
     * @param index start index for the open brace '('.
     * @return index of ')'
     */
    public int getHyperLinkEnd(String text, int index) {
        while (index < text.length() && text.charAt(index) != ')') {
            index++;
        }
        return index;
    }

    /**
     * Given index of '[', the function will return array of open and close index of [] and ().
     * Not in use will remove based on the discussion.
     *
     * @param text actual input string.
     * @param index index of '['.
     * @return array of integers representing indexes of [] and ().
     */
    public int[] getEnd1(String text, int index) {
        int actualStart = index;
        for (int i = index+1; i < text.length(); ++i) {
            if (text.charAt(i) == '[') {
                actualStart = i;
            }
            if (text.charAt(i) == '(' && text.charAt(i-1) == ']') {
                return new int[]{actualStart, i-1, i, getHyperLinkEnd(text, i+1)};
            }
        }
        return new int[]{-1, -1, -1, -1};
    }
}
