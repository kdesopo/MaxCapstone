package com.prs.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prs.business.purchaserequest.PurchaseRequest;
import com.prs.business.purchaserequest.PurchaseRequestRespository;
import com.prs.business.user.User;

@RestController
@RequestMapping(path="/purchase-requests")
public class PurchaseRequestController {
	@Autowired
	PurchaseRequestRespository prRepo;
	
	@PostMapping(path="/submit-new")
	public JsonResponse createPurchaseRequest(@RequestBody PurchaseRequest pr) {
		return addPurchaseRequest(pr);
	}
	
	@GetMapping("/")
	public JsonResponse getAll(@RequestParam int start, int limit) {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(prRepo.findAll(PageRequest.of(start, limit)));
			
		} catch(Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		
		return jr;
	}
	
	@GetMapping("/{id}")
	public JsonResponse get(@PathVariable int id) {
		JsonResponse jr = null;
		
		try {
			Optional<PurchaseRequest> pr = prRepo.findById(id);
			if(pr.isPresent()) {
				jr = JsonResponse.getInstance(pr);
				
			} else {
				jr = JsonResponse.getInstance("No purchase request found for id: " + id);
			}
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	
	@GetMapping(path="/list-review")
	public JsonResponse getReviewPrs(@RequestBody User u, @RequestParam int start, @RequestParam int limit) {
		JsonResponse jr = null;
		
		try {
			Page<PurchaseRequest> pr = prRepo.findByStatusAndUserNot(PurchaseRequest.SUBMITTED, u, PageRequest.of(start, limit));
			
			if(pr != null) {
				jr = JsonResponse.getInstance(pr);
			} else {
				jr = JsonResponse.getInstance("No purchase requests in the Submitted status");
			}
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	
	@DeleteMapping(path="/{id}")
	public JsonResponse deletePurchaseRequest(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			Optional<PurchaseRequest> pr = prRepo.findById(id);
			if(pr.isPresent()) {
				prRepo.deleteById(id);
			} else {
				jr = JsonResponse.getInstance("Delete failed. No purchase request for id: " + id);
			}
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	@PostMapping(path="/")
	public JsonResponse addPurchaseRequest(@RequestBody PurchaseRequest pr) {
		return savePurchaseRequest(pr);
	}
	
	@PutMapping(path="/{id}")
	public JsonResponse updatePurchaseRequest(@RequestBody PurchaseRequest pr, @PathVariable int id) {
		return savePurchaseRequest(pr);
	}
	
	@PutMapping(path="/submit-review")
	public JsonResponse submitPurchaseRequest(@RequestBody PurchaseRequest pr) {
		if(pr.getTotal() <= 50) {
			pr.setStatus(PurchaseRequest.APPROVED);
		} else {
			pr.setStatus(PurchaseRequest.SUBMITTED);
		}
		return savePurchaseRequest(pr);
	}
	
	@PutMapping(path="/approve")
	public JsonResponse approvePurchaseRequest(@RequestBody PurchaseRequest pr) {
		pr.setStatus(PurchaseRequest.APPROVED);
		return savePurchaseRequest(pr);
	}
	
	@PutMapping(path="/reject")
	public JsonResponse rejectPurchaseRequest(@RequestBody PurchaseRequest pr, String reason) {
		pr.setStatus(PurchaseRequest.REJECTED);
		pr.setReasonforrejection(reason);
		return savePurchaseRequest(pr);
	}
	
	private JsonResponse savePurchaseRequest(PurchaseRequest pr) {
		JsonResponse jr = null;
		try {
			prRepo.save(pr);
			jr = JsonResponse.getInstance(pr);
		} catch (DataIntegrityViolationException cve) {
			jr = JsonResponse.getInstance(cve.getMessage());
		}
		return jr;
	}
	
}
