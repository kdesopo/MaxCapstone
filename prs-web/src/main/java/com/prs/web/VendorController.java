package com.prs.web;

import java.util.Optional;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.prs.business.vendor.Vendor;
import com.prs.business.vendor.VendorRespository;

@RestController
@RequestMapping(path="/vendor")
public class VendorController {
	@Autowired
	VendorRespository vendorRepo;
	
	@GetMapping("/")
	public @ResponseBody JsonResponse getAll(@RequestParam int start, int limit) {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(vendorRepo.findAll(PageRequest.of(start,  limit)));
			
		} catch(Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		
		return jr;
	}
	
	@GetMapping("/{id}")
	public JsonResponse get(@PathVariable int id) {
		JsonResponse jr = null;
		
		try {
			Optional<Vendor> vendor = vendorRepo.findById(id);
			if(vendor.isPresent()) {
				jr = JsonResponse.getInstance(vendor);
				
			} else {
				jr = JsonResponse.getInstance("No vendor found for id: " + id);
			}
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	
	@DeleteMapping(path="/{id}")
	public JsonResponse deleteVendor(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			Optional<Vendor> v = vendorRepo.findById(id);
			if(v.isPresent()) {
				vendorRepo.deleteById(id);
			} else {
				jr = JsonResponse.getInstance("Delete failed. No vendor for id: " + id);
			}
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	@PostMapping(path="/")
	public JsonResponse addVendor(@RequestBody Vendor v) {
		return saveVendor(v);
	}
	
	@PutMapping(path="/{id}")
	public JsonResponse updateVendor(@RequestBody Vendor v, @PathVariable int id) {
		return saveVendor(v);
	}
	
	private JsonResponse saveVendor(Vendor v) {
		JsonResponse jr = null;
		try {
			vendorRepo.save(v);
			jr = JsonResponse.getInstance(v);
		} catch (DataIntegrityViolationException cve) {
			jr = JsonResponse.getInstance(cve.getMessage());
		}
		return jr;
	}
	
}
