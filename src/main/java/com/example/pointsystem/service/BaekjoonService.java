
package com.example.pointsystem.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class BaekjoonService {

    public int checkSolvedProblems(String userId) throws IOException {
        String url = "https://www.acmicpc.net/user/" + userId;
        Document doc = Jsoup.connect(url).get();
        String solved = doc.select(".problem_number").text();
        return solved.split(" ").length;
    }
}
