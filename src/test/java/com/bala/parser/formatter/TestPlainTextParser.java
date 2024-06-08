package com.bala.parser.formatter;

import com.google.common.collect.ImmutableList;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public class TestPlainTextParser {

    private PlainTextParser plainTextParser;

    @DataProvider
    public static Object[][] testGetHyperLinkEndDataProvider() {
        return new Object[][]{
            {"someString(someOtherString", 10, 26},
            {"someString(some)OtherString", 10, 15},
            {"someString(someOtherString)", 10, 26}
        };
    }

    @DataProvider
    public static Object[][] testGetEndDataProvider() {
        return new Object[][]{
            {
                "SomeString[someText](hyperlink)someOtherText",
                10,
                new int[]{10, 19, 20, 30}
            },
            {
                "SomeString[some[Text](hyperlink)someOtherText",
                10,
                new int[]{15, 20, 21, 31}
            },
            {
                "SomeString[some[Text](hyperLinkSomeOtherText",
                10,
                new int[]{15, 20, 21, 44}
            },
            {
                "SomeString[some[Text](hyperLinkSomeOtherText)",
                10,
                new int[]{15, 20, 21, 44}
            },
            {
                "SomeString[some[Text]hyperLinkSomeOtherText)",
                10,
                new int[]{-1, -1, -1, -1}
            },
            {
                "SomeString[some[Text]](hyperLinkSomeOtherText)",
                10,
                new int[]{10, 21, 22, 45}
            },
            {
                "SomeString[some[Text]]",
                10,
                new int[]{-1, -1, -1, -1}
            },
            {
                "SomeString[someText",
                10,
                new int[]{-1, -1, -1, -1}
            },
            {
                "SomeString[someText",
                10,
                new int[]{-1, -1, -1, -1}
            }
        };
    }

    @DataProvider
    public static Object[][] testGetEnd1DataProvider() {
        return new Object[][]{
            {
                "SomeString[someText](hyperlink)someOtherText",
                10,
                new int[]{10, 19, 20, 30}
            },
            {
                "SomeString[some[Text](hyperlink)someOtherText",
                10,
                new int[]{15, 20, 21, 31}
            },
            {
                "SomeString[some[Text](hyperLinkSomeOtherText",
                10,
                new int[]{15, 20, 21, 44}
            },
            {
                "SomeString[some[Text](hyperLinkSomeOtherText)",
                10,
                new int[]{15, 20, 21, 44}
            },
            {
                "SomeString[some[Text]hyperLinkSomeOtherText)",
                10,
                new int[]{-1, -1, -1, -1}
            },
            {
                "SomeString[some[Text]](hyperLinkSomeOtherText)",
                10,
                new int[]{15, 21, 22, 45}
            },
            {
                "SomeString[some[Text]]",
                10,
                new int[]{-1, -1, -1, -1}
            },
            {
                "SomeString[someText",
                10,
                new int[]{-1, -1, -1, -1}
            },
            {
                "SomeString[someText",
                10,
                new int[]{-1, -1, -1, -1}
            }
        };
    }

    @DataProvider
    public static Object[][] testBuildParagraphDataProvider() {
        return new Object[][]{
            {
                ImmutableList.of("SomeText"),
                ImmutableList.of("<p>SomeText</p>")
            },
            {
                ImmutableList.of("someText[someWebsite](someUrl)"),
                ImmutableList.of("<p>someText<a href=\"someUrl\">someWebsite</a></p>")
            },
            {
                ImmutableList.of("someText[someWebsite](someUrl)[someWebsite2](someUrl2)"),
                ImmutableList.of("<p>someText<a href=\"someUrl\">someWebsite</a><a href=\"someUrl2\">someWebsite2</a></p>")
            },
            {
                ImmutableList.of("someText[someWebsite](someUrl)someOtherText"),
                ImmutableList.of("<p>someText<a href=\"someUrl\">someWebsite</a>someOtherText</p>")
            },
            {
                ImmutableList.of(
                    "SomeText",
                    "SomeOtherText"),
                ImmutableList.of(
                    "<p>SomeText",
                    "SomeOtherText</p>")
            },
            {
                ImmutableList.of(
                    "someText[someWebsite](someUrl)",
                    "SomeOtherText"),
                ImmutableList.of(
                    "<p>someText<a href=\"someUrl\">someWebsite</a>",
                    "SomeOtherText</p>")
            },
            {
                ImmutableList.of(
                    "someText[someWebsite](someUrl)[someWebsite2](someUrl2)someOtherText",
                    "someText[someWebsite](someUrl)"),
                ImmutableList.of(
                    "<p>someText<a href=\"someUrl\">someWebsite</a><a href=\"someUrl2\">someWebsite2</a>someOtherText",
                    "someText<a href=\"someUrl\">someWebsite</a></p>")
            },
            {
                ImmutableList.of(
                    "someText[someWebsite](someUrl)[someWebsite2](someUrl2)someOtherText",
                    "someText[someWebsite](someUrl)",
                    "someText"),
                ImmutableList.of(
                    "<p>someText<a href=\"someUrl\">someWebsite</a><a href=\"someUrl2\">someWebsite2</a>someOtherText",
                    "someText<a href=\"someUrl\">someWebsite</a>",
                    "someText</p>")
            }
        };
    }

    @BeforeMethod
    public void setUp() {
        plainTextParser = new PlainTextParser();
    }

    @Test(dataProvider = "testGetHyperLinkEndDataProvider")
    public void testGetHyperLinkEnd(String input, int index, int expected) {
        int actual = plainTextParser.getHyperLinkEnd(input, index);
        Assert.assertEquals(actual, expected);
    }

    @Test(dataProvider = "testGetEndDataProvider")
    public void testGetEnd(String input, int index, int[] expected) {
        int[] actual = plainTextParser.getEnd(input, index);
        Assert.assertEquals(actual, expected);
    }

    @Test(dataProvider = "testGetEnd1DataProvider")
    public void testGetEnd1(String input, int index, int[] expected) {
        int[] actual = plainTextParser.getEnd1(input, index);
        Assert.assertEquals(actual, expected);
    }

    @Test(dataProvider = "testBuildParagraphDataProvider")
    public void testBuildParagraph(List<String> param, List<String> expected) {
        List<String> actual = plainTextParser.buildParagraph(param);
        Assert.assertEquals(actual, expected);
    }
}
