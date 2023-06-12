package com.securedloan.arthavedika.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.securedloan.arthavedika.model.GroupData;


@Repository
public interface GroupRepo extends JpaRepository<GroupData,Long>{

}
