package com.example.MyCommunity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MyCommunityApplication {

  public static void main(String[] args) {
    SpringApplication.run(MyCommunityApplication.class, args);
  }
}

// 단축키 CTRL + ALT + L 을 눌러주면 code convention에 따라 formatting 됩니다.
