package com.securedloan.arthavedika.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.securedloan.arthavedika.model.PsyAns;
@Repository
public interface PsyAnsRepo extends JpaRepository<PsyAns,Long>{

}
