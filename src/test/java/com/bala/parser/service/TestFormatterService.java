package com.bala.parser.service;

import com.bala.parser.filereader.FileReader;
import com.bala.parser.formatter.HeaderParser;
import com.bala.parser.formatter.PlainTextParser;
import com.bala.parser.writer.FileWriter;
import com.google.common.collect.ImmutableList;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

@SpringBootTest
public class TestFormatterService {

    private FormatterService formatterService;

    @DataProvider
    public static Object[][] testFormatFileDataProvider() {
        return new Object[][]{
            {
                "/resources/input1.txt",
                ImmutableList.of()
            },
            {
                "/home/balapolisetti/Documents/Projects/html-parser/target/classes/input.txt",
                ImmutableList.of(
                    "<h1>Sample Document</h1>",
                    "<p>Hello!</p>",
                    "<p>This is sample markdown for the <a href=\"https://www.mailchimp.com\">Mailchimp</a> homework assignment.</p>",
                    "<h1>Header one</h1>",
                    "<p>Hello there</p>",
                    "<p>How are you?",
                    "What's going on?</p>",
                    "<h2>Another Header</h2>",
                    "<p>This is a paragraph <a href=\"http://google.com\">with an inline link</a>. Neat, eh?</p>",
                    "<h2>This is a header <a href=\"http://yahoo.com\">with a link</a></h2>"
                )
            },
            {
                null,
                ImmutableList.of(
                    "<h1>Sample Document</h1>",
                    "<p>Hello!</p>",
                    "<p>This is sample markdown for the <a href=\"https://www.mailchimp.com\">Mailchimp</a> homework assignment.</p>",
                    "<h1>Header one</h1>",
                    "<p>Hello there</p>",
                    "<p>How are you?",
                    "What's going on?</p>",
                    "<h2>Another Header</h2>",
                    "<p>This is a paragraph <a href=\"http://google.com\">with an inline link</a>. Neat, eh?</p>",
                    "<h2>This is a header <a href=\"http://yahoo.com\">with a link</a></h2>",
                   "<h2>header 2</h2>",
                    "<h3>header 3</h3>",
                    "<h4>header 4</h4>",
                    "<h5>header 5</h5>",
                    "<h6>header 6</h6>",
                    "<p>####### should be a paragraph</p>"
                )

            }
        };
    }

    @BeforeMethod
    public void setUp() {
        FileReader fileReader = new FileReader();
        FileWriter fileWriter = new FileWriter();
        PlainTextParser plainTextParser = new PlainTextParser();
        HeaderParser headerParser = new HeaderParser(plainTextParser);
        formatterService = new FormatterService(fileReader, fileWriter, headerParser, plainTextParser);
    }

    @Test(dataProvider = "testFormatFileDataProvider")
    public void testFormatFile(String path, List<String> expected) {
        List<String> actual = formatterService.formatFile(path);
        Assert.assertEquals(actual, expected);
    }
}
