package com.makeurpicks.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.makeurpicks.domain.Pick;

public interface PickRepository extends CrudRepository<Pick, String> {

	
	public List<Pick> findByLeagueIdAndWeekIdAndPlayerId(String leagueId, String weekId,String playerId);
	
	public List<Pick> findByLeagueIdAndWeekId(String leagueId, String weekId);
}
