package com.qthekan.qlol.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.qthekan.qlol.model.HighWin;
import com.qthekan.qlol.model.MatchData;
import com.qthekan.qlol.model.MatchList;
import com.qthekan.qlol.repository.RepoHighWin;
import com.qthekan.qlol.repository.RepoMatchList;
import com.qthekan.qlol.repository.RepoMongo;

@Controller
public class qlolCtrl {

	org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

	String mPatchDate = "2023-08-30 06:00:00";
	Timestamp mUnixTime = Timestamp.valueOf(mPatchDate);

	@Autowired
	private RepoHighWin mHighRepo;

	@Autowired
	private RepoMatchList mMatchListRepo;

	@GetMapping("/highWin")
	public String highWin(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
			Model model) {

//		log.info("111");
//
//		List<HighWin> list = mHighRepo.findAll(Sort.by(Sort.Direction.ASC, "losses"));
//		model.addAttribute("highWinList", list);
//
//		for (int i = list.size() - 1; i > -1; i--) {
//			HighWin h = list.get(i);
//
//			if (h.wins < 15) {
//				list.remove(h);
//				continue;
//			}
//
//			if (checkLastTime(h.summonerName) == false) {
//				list.remove(h);
//				continue;
//			}
//
//			h.rate = (float) h.wins / ((float) h.wins + (float) h.losses) * 100;
//
//			log.info(h.toString());
//		}

		return "highWin";
	}

	@GetMapping("/highList")
	public @ResponseBody String highList(Model model) {

		List<HighWin> list = mHighRepo.findAll(Sort.by(Sort.Direction.ASC, "losses"));
		model.addAttribute("data", list);

		for (int i = list.size() - 1; i > -1; i--) {
			HighWin h = list.get(i);

			if (h.wins < 15) {
				list.remove(h);
				continue;
			}

			if (checkLastTime(h.summonerName) == false) {
				list.remove(h);
				continue;
			}

			h.rate = (float) h.wins / ((float) h.wins + (float) h.losses) * 100;

			log.info(h.toString());
		}

		String jstr = new Gson().toJson(list);
		return jstr;
	}

	/**
	 * 
	 * @param name
	 * @return false: not play this patch
	 */
	private boolean checkLastTime(String name) {
		MatchList list = mMatchListRepo.findByKey(name);

		for (int i = 0; i < list.data.size(); i++) {
			String id = list.data.get(i);
			Document doc = RepoMongo.getIns().getMatchData(id);

			if (doc == null) {
				continue;
			}

			Document info = (Document) doc.get("info");
			long stime = info.getLong("gameCreation");

			if (stime < mUnixTime.getTime()) {
				break;
			} else {
				return true;
			}
		}
		return false;
	}

	@GetMapping("/user")
	public String user(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
			Model model) {
		model.addAttribute("name", name);

		MatchList list = mMatchListRepo.findByKey(name);
		log.info(list.toString());

		List<MatchData> matches = new LinkedList<>();

		for (int i = 0; i < list.data.size(); i++) {
			// get each match detail
			String id = list.data.get(i);
			Document doc = RepoMongo.getIns().getMatchData(id);
			if (doc == null) {
				continue;
			}

			Document info = (Document) doc.get("info");
			List<Document> participants = info.getList("participants", Document.class);

			long stime = info.getLong("gameCreation");
			if (stime < mUnixTime.getTime()) {
				log.info("old " + id);
				break;
			}

//			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
			Date date = new Date(stime);

			for (int j = 0; j < participants.size(); j++) {
				Document p = participants.get(j);
				String summonerName = p.getString("summonerName");

				if (summonerName.equalsIgnoreCase(name)) {
					MatchData m = new MatchData();

					m.matchId = id;
					m.date = sdf.format(date);
					m.duration = info.getInteger("gameDuration") / 60;
					m.win = p.getBoolean("win");
					m.teamId = p.getInteger("teamId", 0);
					m.teamPosition = p.getString("teamPosition");
					m.championName = p.getString("championName");
					m.kills = p.getInteger("kills", 0);
					m.deaths = p.getInteger("deaths", 0);
					m.assists = p.getInteger("assists", 0);

					log.info(m.toString());
					matches.add(m);
				}
			}
		}
		model.addAttribute("matchList", matches);

		return "user";
	}
	
	@GetMapping("/riot.txt")
	public String riot(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
			Model model) {
		model.addAttribute("name", name);
		return "riot.txt";
	}

	@GetMapping("/greeting")
	public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
			Model model) {
		model.addAttribute("name", name);
		return "greeting";
	}

}
