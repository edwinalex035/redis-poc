package com.de.redispoc.repository;

import com.de.redispoc.model.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParamRepository extends JpaRepository<Param, Long> {

  public Param findByCode(String code);

}
