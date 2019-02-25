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
import org.springframework.web.bind.annotation.RestController;

import com.prs.business.product.Product;
import com.prs.business.product.ProductRepository;

@RestController
@RequestMapping(path="/product")
public class ProductController {
	@Autowired
	ProductRepository productRepo;
	
	@GetMapping("/")
	public JsonResponse getAll(@RequestParam int start, int limit) {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(productRepo.findAll(PageRequest.of(start, limit)));
			
		} catch(Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		
		return jr;
	}
	
	@GetMapping("/{id}")
	public JsonResponse get(@PathVariable int id) {
		JsonResponse jr = null;
		
		try {
			Optional<Product> product = productRepo.findById(id);
			if(product.isPresent()) {
				jr = JsonResponse.getInstance(product);
				
			} else {
				jr = JsonResponse.getInstance("No product found for id: " + id);
			}
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	
	@DeleteMapping(path="/{id}")
	public JsonResponse deleteProduct(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			Optional<Product> p = productRepo.findById(id);
			if(p.isPresent()) {
				productRepo.deleteById(id);
			} else {
				jr = JsonResponse.getInstance("Delete failed. No product for id: " + id);
			}
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	@PostMapping(path="/")
	public JsonResponse addProduct(@RequestBody Product p) {
		return saveProduct(p);
	}
	
	@PutMapping(path="/{id}")
	public JsonResponse updateProduct(@RequestBody Product p, @PathVariable int id) {
		return saveProduct(p);
	}
	
	private JsonResponse saveProduct(Product p) {
		JsonResponse jr = null;
		try {
			productRepo.save(p);
			jr = JsonResponse.getInstance(p);
		} catch (DataIntegrityViolationException cve) {
			jr = JsonResponse.getInstance(cve.getMessage());
		}
		return jr;
	}
	
}
