package com.securedloan.arthavedika.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.securedloan.arthavedika.model.M_location;
@Repository
public interface LocationRepo extends JpaRepository<M_location,Integer> {
@Query("select a from M_location a")
public List<M_location> getAllLocation();
}
