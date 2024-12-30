package com.igomarcelino.demo_oauth_security.controller;

import com.igomarcelino.demo_oauth_security.dto.CreateTweetDTO;
import com.igomarcelino.demo_oauth_security.dto.FeedDTO;
import com.igomarcelino.demo_oauth_security.dto.FeedItemDTO;
import com.igomarcelino.demo_oauth_security.entities.Roles;
import com.igomarcelino.demo_oauth_security.entities.Tweet;
import com.igomarcelino.demo_oauth_security.repository.TweetRepository;
import com.igomarcelino.demo_oauth_security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @GetMapping("/feed")
    public ResponseEntity<FeedDTO> feed(@RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "pagesize", defaultValue = "10") int pagesize){
      var tweets =   tweetRepository.findAll(
                PageRequest.of(page,pagesize, Sort.Direction.DESC, "creationTimestamp")).
                map(tweet-> new FeedItemDTO(tweet.getId(), tweet.getContent(), tweet.getUser().getUserLogin()));

      return ResponseEntity.ok(new FeedDTO(tweets.getContent(), page,pagesize, tweets.getTotalPages(),  tweets.getTotalElements()));

    }

    @DeleteMapping("/tweet/{id}")
    public ResponseEntity<Void> deleteTweetById(@PathVariable("id") Integer id, JwtAuthenticationToken token){
            var tweet = tweetRepository.findById(id).
                    orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        System.out.println("Tweet not found");
            if (tweet.getUser().getId().toString().equals(token.getName()) || tweet.getUser().getId() == 1) {
                tweetRepository.deleteById(tweet.getId());
            }else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }


        return ResponseEntity.ok().build();
    }
}
