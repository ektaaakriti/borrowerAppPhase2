package com.securedloan.arthavedika.repo;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.securedloan.arthavedika.model.Applicant;
@Repository
public interface ApplicantPaginationRepo extends PagingAndSortingRepository<Applicant, Long> {

   // List<Product> findAllByPrice(double price, Pageable pageable);
	@Query("SELECT a FROM Applicant a ")
	List<Applicant> findAllApplicant(Pageable pageable);
}
