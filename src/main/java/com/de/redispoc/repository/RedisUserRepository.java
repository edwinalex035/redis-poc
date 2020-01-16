package com.de.redispoc.repository;

import com.de.redispoc.model.Param;
import com.de.redispoc.model.User;
import java.util.List;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisUserRepository {
  private HashOperations hashOperations;

  private RedisTemplate redisTemplate;

  public RedisUserRepository(RedisTemplate redisTemplate) {
    this.redisTemplate = redisTemplate;
    this.hashOperations = this.redisTemplate.opsForHash();
  }

  public void save(User user) {
    hashOperations.put("users", user.getId(), user);
  }

  public void update(User user){
    save(user);
  }

  public Param findById(String id) {
    return (Param) hashOperations.get("users", id);
  }

  public List findAll(){
    return hashOperations.values("users");
  }

}
