package com.demoJunit.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookController {
	
	@Autowired
	BookRepo repo;
	
	@GetMapping
	public List<Book> getAllBooks(){
		return repo.findAll();}
	
	@GetMapping("{bookId}")
	public Book getById(@PathVariable("bookId") int id ) {
		return repo.findById(id).get();
		
	}
	
	@PostMapping
	public Book createRecord(@RequestBody @Validated Book record) {
		return repo.save(record);
		
	}
	
	
	@PutMapping
	public void updateRecord(@RequestBody @Validated Book record) throws Exception {
		if(record==null || repo.existsById(record.getB_id()) ) {
			throw new Exception("Record not found");
		}
		repo.getById(record.getB_id()).setName(record.getName());
		repo.findById(record.getB_id()).get().setPrice(record.getPrice());;
	}
}
