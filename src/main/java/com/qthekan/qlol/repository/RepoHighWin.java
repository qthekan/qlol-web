package com.qthekan.qlol.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.qthekan.qlol.model.HighWin;

public interface RepoHighWin extends MongoRepository<HighWin, String> {

//	public List<HighWin> findAll();
	
//	public Customer findByFirstName(String firstName);
//	public List<Customer> findByLastName(String lastName);

}
