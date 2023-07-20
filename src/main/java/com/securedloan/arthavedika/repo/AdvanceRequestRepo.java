package com.securedloan.arthavedika.repo;
import com.securedloan.arthavedika.model.*;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvanceRequestRepo extends JpaRepository<AdvanceRequest,String> {
@Query("select a from AdvanceRequest a")
List<AdvanceRequest> getAllAdvanceRequest();

}
