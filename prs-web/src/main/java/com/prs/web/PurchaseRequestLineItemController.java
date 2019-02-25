package com.prs.web;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
import com.prs.business.purchaserequestlineitem.PurchaseRequestLineItem;
import com.prs.business.purchaserequestlineitem.PurchaseRequestLineItemRepository;

@RestController
@RequestMapping(path="/purchase-request-line-items")
public class PurchaseRequestLineItemController {
	@Autowired
	PurchaseRequestLineItemRepository prliRepo;
	
	@Autowired
	PurchaseRequestRespository prRepo;
	
	@Autowired
	EntityManager em;
	
	@GetMapping("/")
	public JsonResponse getAll(@RequestParam int start, int limit) {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(prliRepo.findAll(PageRequest.of(start, limit)));
			
		} catch(Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		
		return jr;
	}
	
	@GetMapping("/{id}")
	public JsonResponse get(@PathVariable int id) {
		JsonResponse jr = null;
		
		try {
			Optional<PurchaseRequestLineItem> prli = prliRepo.findById(id);
			if(prli.isPresent()) {
				jr = JsonResponse.getInstance(prli);
				
			} else {
				jr = JsonResponse.getInstance("No purchase request line item found for id: " + id);
			}
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	
	@DeleteMapping(path="/{id}")
	public JsonResponse deletePurchaseRequestLineItem(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			Optional<PurchaseRequestLineItem> prli = prliRepo.findById(id);
			if(prli.isPresent()) {
				prliRepo.deleteById(id);
				recalculateTotal(prli.get().getRequest());
			} else {
				jr = JsonResponse.getInstance("Delete failed. No purchase request line item for id: " + id);
			}
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	@PostMapping(path="/")
	public JsonResponse addPurchaseRequestLineItem(@RequestBody PurchaseRequestLineItem prli) {
		JsonResponse jr = savePurchaseRequestLineItem(prli);
		recalculateTotal(prli.getRequest());
		return jr;
	}
	
	@PutMapping(path="/{id}")
	public JsonResponse updatePurchaseRequestLineItem(@RequestBody PurchaseRequestLineItem prli, @PathVariable int id) {
		JsonResponse jr = savePurchaseRequestLineItem(prli);
		recalculateTotal(prli.getRequest());
		return jr;
	}
	
	private void recalculateTotal(@RequestBody PurchaseRequest pr) {
		double newTotal = 0.0;
		List<PurchaseRequestLineItem> prlis = prliRepo.findByRequest(pr);
		newTotal = prlis.stream()
		.map(prli -> prli.getProduct().getPrice() * prli.getQuantity())
		.reduce(newTotal, (a,b) -> a + b);
		pr.setTotal(newTotal);
		prRepo.save(pr);
		em.clear();
	}
	
	private JsonResponse savePurchaseRequestLineItem(PurchaseRequestLineItem prli) {
		JsonResponse jr = null;
		try {
			prliRepo.save(prli);
			jr = JsonResponse.getInstance(prli);
		} catch (DataIntegrityViolationException cve) {
			jr = JsonResponse.getInstance(cve.getMessage());
		}
		return jr;
	}
	
}
