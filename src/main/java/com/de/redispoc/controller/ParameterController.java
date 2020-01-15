package com.de.redispoc.controller;

import com.de.redispoc.model.Param;
import com.de.redispoc.repository.RedisParamRepository;
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
@RequestMapping("/params")
public class ParameterController {
  @Autowired
  private RedisParamRepository redisParamRepository;


  @Cacheable(value = "params", key="#code")
  @GetMapping(value = "/{code}")
  public Param getParam(@PathVariable("code") String code) {
    log.info("Getting param with code {}", code);
    return  redisParamRepository.findById(code);
  }

  @CachePut(value = "params", key="#param.code")
  @PostMapping
  public Param setParam(@RequestBody Param param) {
    log.info("Calling setParam with {}", param);
    redisParamRepository.save(param);
    return param;
  }

  @CachePut(value = "params", key="#param.code")
  @PutMapping
  public Param putParam(@RequestBody Param param) {
    log.info("Calling setParam with {}", param);
    redisParamRepository.update(param);
    return param;
  }

}
