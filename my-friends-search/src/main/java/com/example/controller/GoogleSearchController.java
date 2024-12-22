package com.example.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.SearchResult;
import com.example.model.WebPage;
import com.example.service.GoogleQueryService;
import com.example.service.SearchEngine;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // 或指定前端來源，例如 "http://localhost:3000"
public class GoogleSearchController {

    @Autowired
    private GoogleQueryService googleQueryService;

    @Autowired
    private SearchEngine searchEngine;

    @GetMapping("/search")
    public List<SearchResult> search(@RequestParam("q") String query) {
        try {
            // 1. 先用 googleQueryService 抓取(標題, URL)
            Map<String, String> rawResults = googleQueryService.search(query);

            // 2. 建立 WebPage 物件清單
            List<WebPage> pages = new ArrayList<>();
            for (Map.Entry<String, String> entry : rawResults.entrySet()) {
                String title = entry.getKey();
                String url = entry.getValue();
                pages.add(new WebPage(url, title));
            }

            // 3. 用 SearchEngine 執行加權邏輯，並排序
            return searchEngine.searchAndSort(pages, query);

        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}