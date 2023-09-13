package com.qthekan.qlol.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "high_win")
public class HighWin {

	@Id
	private String id;

	public String leagueId;
	public String queueType;

	public String tier;
	public String rank;

	public String summonerId;
	public String summonerName;

	public int leaguePoints;
	public int wins;
	public int losses;
	public float rate;

	public boolean veteran;
	public boolean inactive;
	public boolean freshBlood;
	public boolean hotStreak;

	@Override
	public String toString() {
		return "name: " + summonerName + ", tier: " + tier + ", rank: " + rank + ", win: " + wins + ", loss: " + losses;
	}
}
