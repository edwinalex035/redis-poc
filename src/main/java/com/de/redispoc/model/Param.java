package com.de.redispoc.model;

import java.io.Serializable;
import lombok.Data;

@Data
public class Param implements Serializable {
  private String code;
  private String value;

}
