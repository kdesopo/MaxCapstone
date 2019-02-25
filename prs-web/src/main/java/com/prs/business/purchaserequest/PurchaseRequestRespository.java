package com.prs.business.purchaserequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.prs.business.user.User;

public interface PurchaseRequestRespository extends CrudRepository<PurchaseRequest, Integer>,
	PagingAndSortingRepository<PurchaseRequest, Integer>{
	Page<PurchaseRequest> findByStatusAndUserNot(String status, User u, Pageable pageable);
}
