package com.qthekan.qlol.repository;

import org.bson.Document;
import org.slf4j.LoggerFactory;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class RepoMongo {
	static RepoMongo mIns = null;

	MongoClient mClient;
	MongoDatabase mDatabase;
	MongoCollection<Document> mCollMatch;

	org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

	static public RepoMongo getIns() {
		if (mIns == null) {
			mIns = new RepoMongo();
		}

		return mIns;
	}

	RepoMongo() {
		mClient = MongoClients.create("mongodb://127.0.0.1:27017");
		mDatabase = mClient.getDatabase("qlol_v5");
		mCollMatch = mDatabase.getCollection("high_win_match");
	}

	public Document getMatchData(String id) {
		log.info("getMatchData: " + id);
		Document doc = mCollMatch.find(Filters.eq("metadata.matchId", id)).first();

		if (doc == null) {
//			log.error("No results found.");
			return null;
		} else {
			log.info(doc.toJson());
			return doc;
		}
	}

}
