package com.bala.parser.controller;

import com.bala.parser.service.FormatterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HTMLParserController {

    private final FormatterService formatterService;

    @Autowired
    public HTMLParserController(FormatterService formatterService) {
        this.formatterService = formatterService;
    }

    /**
     * Rest call to initiate a parse for a file in given path.
     *
     * @param filePath path of the file which need be parsed.
     */
    @GetMapping(path = "/api/parse/{filePath}")
    public void parseFile(@PathVariable String filePath) {
        formatterService.formatFile(filePath);
    }

    /**
     * Rest call to initiate a parse for a file in defaultPath.
     *
     * @return List of strings parsed with HTML tags.
     */
    @GetMapping(path = "/api/parse")
    public List<String> parseFileAndGetLines() {
        return formatterService.formatFile(null);
    }
}
