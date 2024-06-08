package com.bala.parser.formatter;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class TestHeaderParser {

    private HeaderParser headerParser;

    @BeforeMethod
    public void setUp() {
        PlainTextParser plainTextParser = new PlainTextParser();
        headerParser = new HeaderParser(plainTextParser);
    }

    @DataProvider
    public static Object[][] headerFormatterDataProvider() {
        return new Object[][]{
                {"#sometext", 1},
                {"##sometext", 2},
                {"###sometext", 3},
                {"####sometext", 4},
                {"#####sometext", 5},
                {"######sometext", 6},
                {"#######sometext", -1},
                {"##", 2},
                {"#", 1},
                {"###", 3},
                {"", -1}
        };
    }

    @DataProvider
    public static Object[][] isHeaderDataProvider() {
        return new Object[][]{
            {"###someText", true},
            {"someText##", false},
            {"", false},
        };
    }

    @DataProvider
    public Object[][] headerDataProvider() {
        return new Object[][]{
            {"# Should have h1 tag", "<h1>Should have h1 tag</h1>"},
            {"# Should have h1 tag [hello](http://google.com)", "<h1>Should have h1 tag <a href=\"http://google.com\">hello</a></h1>"},
            {"## Should have h2 tag [hello](http://google.com)", "<h2>Should have h2 tag <a href=\"http://google.com\">hello</a></h2>"},
            {"## Should have h2 tag", "<h2>Should have h2 tag</h2>"},
            {"### Should have h3 tag", "<h3>Should have h3 tag</h3>"},
            {"### Should have h3 tag [hello](http://google.com)", "<h3>Should have h3 tag <a href=\"http://google.com\">hello</a></h3>"},
            {"#### Should have h4 tag", "<h4>Should have h4 tag</h4>"},
            {"#### Should have h4 tag [hello](http://google.com)", "<h4>Should have h4 tag <a href=\"http://google.com\">hello</a></h4>"},
            {"##### Should have h5 tag", "<h5>Should have h5 tag</h5>"},
            {"##### Should have h5 tag [hello](http://google.com)", "<h5>Should have h5 tag <a href=\"http://google.com\">hello</a></h5>"},
            {"###### Should have h6 tag", "<h6>Should have h6 tag</h6>"},
            {"###### Should have h6 tag [hello](http://google.com)", "<h6>Should have h6 tag <a href=\"http://google.com\">hello</a></h6>"},
            {"No Tag Here", "No Tag Here"},
            {"#", "<h1></h1>"},
            {"# [hello](http://google.com)", "<h1><a href=\"http://google.com\">hello</a></h1>"},
            {"# [google](https://google.com) [intuit](https://intuit.com)",
            "<h1><a href=\"https://google.com\">google</a><a href=\"https://intuit.com\">intuit</a></h1>"},
            {"##", "<h2></h2>"},
            {"####### out of scope hashes", "####### out of scope hashes"},
            {"# hash at the end #", "<h1>hash at the end #</h1>"}
        };
    }

    @Test(dataProvider = "isHeaderDataProvider")
    public void isHeaderTest(String text, boolean expectedResult) {
        boolean actual = headerParser.isHeader(text);
        Assert.assertEquals(actual, expectedResult);
    }

    @Test(dataProvider = "headerFormatterDataProvider")
    public void testGetTag(String text, int expected) {
        int actual = headerParser.getTag(text);
        Assert.assertEquals(actual, expected);
    }

    @Test(dataProvider = "headerDataProvider")
    public void testParseString(String input, String expected) {
        String actual = headerParser.parseString(input);
        Assert.assertEquals(actual, expected);
    }
}
