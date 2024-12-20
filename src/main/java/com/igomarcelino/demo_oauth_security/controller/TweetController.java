package com.igomarcelino.demo_oauth_security.controller;

import com.igomarcelino.demo_oauth_security.dto.CreateTweetDTO;
import com.igomarcelino.demo_oauth_security.entities.Tweet;
import com.igomarcelino.demo_oauth_security.repository.TweetRepository;
import com.igomarcelino.demo_oauth_security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TweetController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    TweetRepository tweetRepository;

    @PostMapping("/tweets")
    public ResponseEntity<Void> createTweet(@RequestBody CreateTweetDTO createTweetDTO,
                                            JwtAuthenticationToken token){
        var user = userRepository.findById(Integer.valueOf(token.getName()));

        var tweet = new Tweet();
        tweet.setUser(user.get());
        tweet.setContent(createTweetDTO.content());
        tweetRepository.save(tweet);
        return ResponseEntity.ok().build();
    }
}
