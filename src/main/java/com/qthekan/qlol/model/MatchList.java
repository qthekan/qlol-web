package com.qthekan.qlol.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "high_win_match_list")
public class MatchList {

	@Id
	public String id;
	
	public String key;
	public List<String> data;
	
	@Override
	public String toString() {
		return "key: " + key + ", cnt: " + data.size();
	}
}
