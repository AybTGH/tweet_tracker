package com.ayb.tweetingestor.tweet_ingestor.service;

import com.ayb.tweetingestor.tweet_ingestor.service.KafkaProducer;  // Import KafkaProducer
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import io.github.cdimascio.dotenv.Dotenv;

@Service
public class TwitterApiService {

    private final WebClient webClient;
    private final KafkaProducer kafkaProducer;  // KafkaProducer to send messages to Kafka

    // Constructor to initialize WebClient with authorization and inject KafkaProducer
    @Autowired
    public TwitterApiService(WebClient.Builder webClientBuilder, Dotenv dotenv, KafkaProducer kafkaProducer) {
        String bearerToken = dotenv.get("TWITTER_API_BEARER_TOKEN");
        System.out.println("Using bearer token: " + bearerToken);

        this.webClient = webClientBuilder.baseUrl("https://api.twitter.com/2/tweets/search/recent")
                                        .defaultHeader("Authorization", "Bearer " + bearerToken)
                                        .build();
        this.kafkaProducer = kafkaProducer;  // Inject the KafkaProducer to send tweets to Kafka
    }

    // Method to search the last 10 tweets with the hashtag #cnss
    // public void startStream() {
    //     // Define the hashtag query parameter
    //     String hashtag = "#cnss";

    //     // Create a GET request to the Twitter API to fetch tweets with the hashtag
    //     webClient.get()  // Make a GET request to the Twitter API
    //              .uri(uriBuilder -> uriBuilder.queryParam("query", hashtag)  // Set the query parameter for #cnss
    //                                          .queryParam("max_results", 10)  // Limit the results to 10 tweets
    //                                          .queryParam("tweet.fields", "text,author_id")  // Specify the fields you want to receive
    //                                          .build())  
    //              .retrieve()  // Perform the actual HTTP request
    //              .bodyToMono(String.class)  // Convert the response body into a Mono (single response)
    //              .doOnError(error -> {
    //                  if (error instanceof WebClientResponseException) {
    //                      WebClientResponseException ex = (WebClientResponseException) error;
    //                      System.out.println("Error: " + ex.getStatusCode() + " " + ex.getResponseBodyAsString());
    //                  }
    //              })
    //              .subscribe(response -> {  // Handle the response
    //                  System.out.println("Received Tweets: " + response);
                     
    //                  // Extract tweet content from response (you may need to parse the JSON here)
    //                  // In this example, we're assuming the response is a simple string. If you get JSON, you may need to parse it.
    //                  String tweetMessage = response;  // Just an example; parse the response to get the actual tweet text.

    //                  // Send the tweet message to Kafka
    //                  kafkaProducer.sendMessage(tweetMessage);  // Send to Kafka producer
    //              });
    // }
    public void startStream() {
      // Simulated tweet response with multiple topics
      String fakeResponse = """
      {
        "data": [
          {
            "id": "1234567890",
            "text": "This is a test tweet about #cnss",
            "author_id": "111"
          },
          {
            "id": "0987654321",
            "text": "Another test tweet about #cnss!",
            "author_id": "222"
          },
          {
            "id": "2233445566",
            "text": "I love how sunny it is today — perfect weather to chill! ☀️",
            "author_id": "333"
          },
          {
            "id": "3344556677",
            "text": "The new movie I watched yesterday was absolutely terrible. Worst plot ever.",
            "author_id": "444"
          },
          {
            "id": "4455667788",
            "text": "Just had a great coffee while coding. Productivity level: 9000 ☕️💻",
            "author_id": "555"
          }
        ]
      }
      """;
  
      // Parse the fake JSON and send each tweet to Kafka
      try {
          org.json.JSONObject jsonObject = new org.json.JSONObject(fakeResponse);
          org.json.JSONArray data = jsonObject.getJSONArray("data");
  
          for (int i = 0; i < data.length(); i++) {
              String tweet = data.getJSONObject(i).getString("text");
              kafkaProducer.sendMessage(tweet);
          }
      } catch (Exception e) {
          e.printStackTrace();
      }
  }
  
    
}
