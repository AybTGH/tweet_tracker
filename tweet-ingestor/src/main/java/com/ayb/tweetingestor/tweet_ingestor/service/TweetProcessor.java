package com.ayb.tweetingestor.tweet_ingestor.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TweetProcessor {

    // Clean up the tweet text by removing hashtags, URLs, etc.
    public String cleanText(String text) {
        // Example: Remove hashtags and links
        return text.replaceAll("#\\w+", "").replaceAll("https?://\\S+", "").trim();
    }

    // Optional: Map author ID to username (mock-up for now, replace with real username logic if needed)
    public String mapAuthorIdToUsername(String authorId) {
        // For demo, we will just prepend 'user_' to the authorId. You can map it to real usernames.
        return "user_" + authorId;
    }

    public String analyzeSentiment(String text) {
        String cleaned = cleanText(text).toLowerCase();

        List<String> positiveWords = Arrays.asList("good", "great", "happy", "love", "excellent", "amazing", "nice", "awesome");
        List<String> negativeWords = Arrays.asList("bad", "sad", "hate", "terrible", "awful", "horrible", "worst", "angry");

        int score = 0;

        for (String word : positiveWords) {
            if (cleaned.contains(word)) score++;
        }

        for (String word : negativeWords) {
            if (cleaned.contains(word)) score--;
        }

        if (score > 0) return "positive";
        else if (score < 0) return "negative";
        else return "neutral";
    }
}