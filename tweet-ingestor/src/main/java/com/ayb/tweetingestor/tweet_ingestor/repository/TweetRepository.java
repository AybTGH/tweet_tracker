package com.ayb.tweetingestor.tweet_ingestor.repository;


import com.ayb.tweetingestor.tweet_ingestor.model.Tweet;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface TweetRepository extends ElasticsearchRepository<Tweet, String> {
}
