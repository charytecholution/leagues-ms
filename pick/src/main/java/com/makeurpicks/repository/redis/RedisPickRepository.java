package com.makeurpicks.repository.redis;

import java.util.List;

import org.springframework.data.redis.core.RedisTemplate;

import com.makeurpicks.domain.Pick;
import com.makeurpicks.repository.PickRepository;

public class RedisPickRepository extends AbstractRedisHashCRUDRepository<Pick> implements PickRepository {

public static final String PICK_KEY = "picks";
	
	public RedisPickRepository(RedisTemplate<String, Pick> redisTemplate)
	{
		super(redisTemplate);
	}

	@Override
	public String getKey() {
		return PICK_KEY;
	}

	

	@Override
	public List<Pick> findByLeagueIdAndWeekIdAndPlayerId(String leagueId, String weekId, String playerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pick> findByLeagueIdAndWeekId(String leagueId, String weekId) {
		// TODO Auto-generated method stub
		return null;
	}	

}
