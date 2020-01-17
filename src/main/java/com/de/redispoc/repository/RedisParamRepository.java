package com.de.redispoc.repository;

import com.de.redispoc.model.Param;
import com.de.redispoc.model.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class RedisParamRepository {
  private HashOperations hashOperations;

  private RedisTemplate redisTemplate;

  public RedisParamRepository(RedisTemplate redisTemplate) {
    this.redisTemplate = redisTemplate;
    this.hashOperations = this.redisTemplate.opsForHash();
  }

  public void save(Param param) {
    hashOperations.put("params", param.getCode(), param);
  }

  public void update(Param param){
    save(param);
  }

  public Param findById(String code) {
    log.info("Calling findById with code {}, ", code);
    return (Param) hashOperations.get("params", code);
  }

  public List findAll(){
    return hashOperations.values("params");
  }

}
