package com.prs.business.purchaserequestlineitem;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.prs.business.purchaserequest.PurchaseRequest;

public interface PurchaseRequestLineItemRepository extends CrudRepository<PurchaseRequestLineItem, Integer>,
	PagingAndSortingRepository<PurchaseRequestLineItem, Integer>{
	List<PurchaseRequestLineItem> findByRequest(PurchaseRequest request);
}
