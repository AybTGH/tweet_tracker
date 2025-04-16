package com.ayb.tweetingestor.tweet_ingestor.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "tweets")
public class Tweet {

    @Id
    private String id;
    private String content;
    private String username;
    private String createdAt;
    private String sentiment;


    // Default constructor (REQUIRED for Elasticsearch)
    public Tweet() {}

    // All-args constructor
    public Tweet(String id, String content, String username, String createdAt) {
        this.id = id;
        this.content = content;
        this.username = username;
        this.createdAt = createdAt;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    
    public String getSentiment() { return sentiment; }
    public void setSentiment(String sentiment) { this.sentiment = sentiment; }

}
