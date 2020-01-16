package com.de.redispoc.controller;

import com.de.redispoc.model.Param;
import com.de.redispoc.model.User;
import com.de.redispoc.repository.RedisUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
  @Autowired
  private RedisUserRepository redisUserRepository;


  @Cacheable(cacheNames = "users", value = "users", key="#id")
  @GetMapping(value = "/{id}")
  public Param getParam(@PathVariable("id") String id) {
    log.info("Getting param with id {}", id);
    return  redisUserRepository.findById(id);
  }

  @CachePut(cacheNames = "users", value = "users", key="#user.id")
  @PostMapping
  public User setParam(@RequestBody User user) {
    log.info("Calling setParam with {}", user);
    redisUserRepository.save(user);
    return user;
  }

  @CachePut(cacheNames = "users", value = "users", key="#user.id")
  @PutMapping
  public User putParam(@RequestBody User user) {
    log.info("Calling setParam with {}", user);
    redisUserRepository.update(user);
    return user;
  }

}
