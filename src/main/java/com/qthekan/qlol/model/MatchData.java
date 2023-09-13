package com.qthekan.qlol.model;

public class MatchData {

	public String matchId;

	public String date;
	public int duration;

	public boolean win;
	public int teamId;
	public String teamPosition;
	public String championName;
	public int kills;
	public int deaths;
	public int assists;

	public MatchData() {

	}

	@Override
	public String toString() {
		return "matchId: " + matchId + ", win: " + win + ", team: " + teamId + ", teamPosition: " + teamPosition
				+ ", champion: " + championName + ", kills: " + kills + ", deaths: " + deaths;
	}
}
