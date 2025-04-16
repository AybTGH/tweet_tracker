package com.ayb.tweetingestor.tweet_ingestor;

import com.ayb.tweetingestor.tweet_ingestor.service.TwitterApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = "com.ayb.tweetingestor.tweet_ingestor.repository")

public class TweetIngestorApplication implements CommandLineRunner {

    @Autowired
    private TwitterApiService twitterApiService;

    public static void main(String[] args) {
        SpringApplication.run(TweetIngestorApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Call the startStream method to start listening for tweets
        twitterApiService.startStream();
    }
}

// mvn spring-boot:run
