package com.qthekan.qlol.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.qthekan.qlol.model.MatchList;

public interface RepoMatchList extends MongoRepository<MatchList, String> {

	public MatchList findByKey(String key);
}
