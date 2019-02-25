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

import com.prs.business.user.User;
import com.prs.business.user.UserRepository;

@RestController
@RequestMapping(path="/users")
public class UserController {
	@Autowired
	UserRepository userRepo;
	
	@GetMapping("/")
	public @ResponseBody JsonResponse getAll(@RequestParam int start, int limit) {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(userRepo.findAll(PageRequest.of(start, limit)));
		} catch(Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		
		return jr;
	}
	
	@GetMapping("/{id}")
	public JsonResponse get(@PathVariable int id) {
		JsonResponse jr = null;
		
		try {
			Optional<User> user = userRepo.findById(id);
			if(user.isPresent()) {
				jr = JsonResponse.getInstance(user);
				
			} else {
				jr = JsonResponse.getInstance("No user found for id: " + id);
			}
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	
	@PostMapping("/authenticate")
	public JsonResponse authenticate(@RequestBody User u) {
		JsonResponse jr = null;
		
		try {
			Optional<User> user = userRepo.findByUsernameAndPassword(
									u.getUsername(), u.getPassword());
			if(user.isPresent()) {
				jr = JsonResponse.getInstance(user);
				
			} else {
				jr = JsonResponse.getInstance("No user found for entered credentials.");
			}
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	
	@DeleteMapping(path="/{id}")
	public JsonResponse deleteUser(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			Optional<User> u = userRepo.findById(id);
			if(u.isPresent()) {
				userRepo.deleteById(id);
			} else {
				jr = JsonResponse.getInstance("Delete failed. No user for id: " + id);
			}
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	@PostMapping(path="/")
	public JsonResponse addUser(@RequestBody User u) {
		return saveUser(u);
	}
	
	@PutMapping(path="/{id}")
	public JsonResponse updateUser(@RequestBody User u, @PathVariable int id) {
		return saveUser(u);
	}
	
	private JsonResponse saveUser(User u) {
		JsonResponse jr = null;
		try {
			userRepo.save(u);
			jr = JsonResponse.getInstance(u);
		} catch (DataIntegrityViolationException cve) {
			jr = JsonResponse.getInstance(cve.getMessage());
		}
		return jr;
	}
	
}
