package com.de.redispoc.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

@Data
@AllArgsConstructor
@RedisHash("Student")
public class Student implements Serializable {

  public enum Gender {
    MALE, FEMALE
  }

  private String id;
  private String name;
  private Gender gender;
  private int grade;
}