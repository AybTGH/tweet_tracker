package com.ayb.tweetingestor.tweet_ingestor.service;

import java.time.Instant;
import java.util.UUID;

import com.ayb.tweetingestor.tweet_ingestor.model.Tweet;
import com.ayb.tweetingestor.tweet_ingestor.repository.TweetRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    private final TweetRepository tweetRepository;
    private final TweetProcessor tweetProcessor;

    @Autowired
    public KafkaConsumer(TweetRepository tweetRepository, TweetProcessor tweetProcessor) {
        this.tweetRepository = tweetRepository;
        this.tweetProcessor = tweetProcessor;
    }

    
    @KafkaListener(topics = "twitter-stream", groupId = "my-group")
    public void listen(String message) {
        System.out.println("🔥 Received tweet: " + message);

        try {
            if (message != null && !message.trim().isEmpty()) {
                String cleanedText = tweetProcessor.cleanText(message);
                String sentiment = tweetProcessor.analyzeSentiment(cleanedText);
                String username = "unknown";

                Tweet tweet = new Tweet();
                tweet.setId(UUID.randomUUID().toString());
                tweet.setContent(cleanedText);
                tweet.setUsername(username);
                tweet.setCreatedAt(Instant.now().toString());
                tweet.setSentiment(sentiment);  // ✅ Add sentiment

                tweetRepository.save(tweet);
                System.out.println("✅ Tweet saved with sentiment: " + sentiment);
            }
        } catch (Exception e) {
            System.err.println("❌ Failed to process tweet: " + e.getMessage());
        }
    }

}
