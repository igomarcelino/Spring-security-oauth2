package com.igomarcelino.demo_oauth_security.repository;

import com.igomarcelino.demo_oauth_security.entities.Tweet;
import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Integer> {
}
