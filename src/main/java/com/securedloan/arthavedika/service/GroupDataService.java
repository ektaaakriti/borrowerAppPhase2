package com.securedloan.arthavedika.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.securedloan.arthavedika.model.GroupData;
import com.securedloan.arthavedika.repo.GroupRepo;

@Service
@Transactional
public class GroupDataService {
	@Autowired
 private GroupRepo groupRepo;
	
	public List<GroupData> findAll() throws Exception {
		return groupRepo.findAll();
	}

	public GroupData save(GroupData groupDta) throws Exception{
		return groupRepo.save(groupDta);
	}
	
}

