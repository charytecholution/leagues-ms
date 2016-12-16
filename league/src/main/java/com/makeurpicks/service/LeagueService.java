package com.makeurpicks.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.makeurpicks.domain.League;
import com.makeurpicks.domain.LeagueName;
import com.makeurpicks.domain.PlayerLeague;
import com.makeurpicks.exception.LeagueValidationException;
import com.makeurpicks.exception.LeagueValidationException.LeagueExceptions;
import com.makeurpicks.repository.LeagueRepository;
import com.makeurpicks.repository.PlayerLeagueRepository;
import com.makeurpicks.utils.HelperUtils;

@Component
public class LeagueService {

	@Autowired
	private LeagueRepository leagueRepository;

	@Autowired
	private PlayerLeagueRepository playerLeagueRepository;

	public League createLeague(League league) throws LeagueValidationException {
		validateLeague(league);
		/*String id = UUID.randomUUID().toString();
		league.set(id);*/
		leagueRepository.save(league);
		addPlayerToLeague(league, league.getAdminId());
		return league;
	}

	public League updateLeague(League league) throws LeagueValidationException {
		validateLeague(league);

		leagueRepository.save(league);
		
		return league;
	}

	public Set<LeagueName> getLeaguesForPlayer(String playerId) throws LeagueValidationException {
		List<League> leagues = leagueRepository.findAll(playerLeagueRepository.findLeagueIdsByPlayerId(playerId));
		return HelperUtils.getLeagueNameFromLeagues(leagues);
	}
	
	public Set<String> getPlayersInLeague(String leagueid) throws LeagueValidationException {
		 return new HashSet<String> (playerLeagueRepository.findPlayerIdsByLeagueId(leagueid));
		
	}

	public void joinLeague(PlayerLeague playerLeague)
	{
		if (playerLeague.getLeagueId() == null)
		{
			if (playerLeague.getLeagueName()==null)
			{
				throw new LeagueValidationException(LeagueExceptions.LEAGUE_NOT_FOUND);
			}
			else
			{
				League league = getLeagueByName(playerLeague.getLeagueName());
				if (league == null)
					throw new LeagueValidationException(LeagueExceptions.LEAGUE_NOT_FOUND);
				playerLeague.setLeagueId(league.getId());
			}
		}
		
		joinLeague(playerLeague.getLeagueId(), playerLeague.getPlayerId(), playerLeague.getPassword());
	
	}

	protected void joinLeague(Integer leagueId, String playerId, String password) throws LeagueValidationException {

		// if (!isValidPlayer(playerId))
		// throw new
		// LeagueValidationException(LeagueExceptions.PLAYER_NOT_FOUND);

		League league = leagueRepository.findOne(leagueId);
		if (league == null)
			throw new LeagueValidationException(LeagueExceptions.LEAGUE_NOT_FOUND);

		if (league.getPassword() != null && !"".equals(league.getPassword()) && !league.getPassword().equals(password))
			throw new LeagueValidationException(LeagueExceptions.INVALID_LEAGUE_PASSWORD);

		addPlayerToLeague(league, playerId);

	}
	
	protected void addPlayerToLeague(League league, String playerId)
	{
		//TODO: need to create playerleague builder
		PlayerLeague playerLeague = new PlayerLeague();
		playerLeague.setLeagueId(league.getId());
		playerLeague.setLeagueName(league.getLeagueName());
		playerLeague.setPassword(league.getPassword());
		playerLeague.setPlayerId(playerId);
		playerLeagueRepository.save(playerLeague);
	}

	public League getLeagueById(int leagueId) {
		return leagueRepository.findOne(leagueId);
	}
	
	public League getLeagueById(String leagueId) {
		Integer id = Integer.valueOf(leagueId);
		if (id==null)
			throw new LeagueValidationException(LeagueExceptions.INVALID_LEAGUE_ID);
		return leagueRepository.findOne(id);
	}
	
	public League getLeagueByName(String leagueName) {
		return leagueRepository.findByLeagueName(leagueName);
	}

	/*public League getLeagueByName(String name) {
		Iterable<League> leagues = leagueRepository.findAll();
		for (League league:leagues)
		{
			if (league.getLeagueName().equals(name))
				return league;
		}
		
		return null;
	}*/

	public void removePlayerFromLeague(Integer leagueId, String playerId) {
		League league = getLeagueById(leagueId);
		if (league == null)
			throw new LeagueValidationException(LeagueExceptions.LEAGUE_NOT_FOUND);
		PlayerLeague playerLeague = playerLeagueRepository.findByLeagueIdAndPlayerId(league.getId(),playerId);
		if(playerLeague!=null)
			playerLeagueRepository.delete(playerLeague);
		
	}
	
	/*
	 * 
	 * 
	 */
	protected void validateLeague(League league) throws LeagueValidationException {
		if (league.getLeagueName() == null || league.getLeagueName().equals(""))
			throw new LeagueValidationException(
					LeagueExceptions.LEAGUE_NAME_IS_NULL);
		if (getLeagueByName(league.getLeagueName()) != null)
			throw new LeagueValidationException(
					LeagueExceptions.LEAGUE_NAME_IN_USE);
		if (league.getSeasonId().isEmpty())
			throw new LeagueValidationException(
					LeagueExceptions.SEASON_ID_IS_NULL);

		if (league.getAdminId().isEmpty())
			throw new LeagueValidationException(
					LeagueExceptions.ADMIN_NOT_FOUND);

		if (!isValidPlayer(league.getAdminId()))
			throw new LeagueValidationException(
					LeagueExceptions.ADMIN_NOT_FOUND);
	}

	private boolean isLeagueValid(String leagueId) {
		League league = getLeagueById(leagueId);
		if (league == null)
			return false;
		else
			return true;
	}

	
	protected boolean isValidPlayer(String playerId) {
		// InstanceInfo instance =
		// discoveryClient.getNextServerFromEureka("player", false);
//		URI uri = UriComponentsBuilder.fromHttpUrl(instance.getHomePageUrl())
//				.path("player").path("/id/").path(playerId).build().encode()
//				.toUri();

		
//		ServiceInstance instance = loadBalancer.choose("player");
//		URI uri = UriComponentsBuilder.fromHttpUrl(String.format("http://%s:%d",
//				instance.getHost(), instance.getPort()))
//				.path("player").path("/id/").path(playerId).build().encode()
//				.toUri();
				

//		RestTemplate restTemplate = new RestTemplate();
//		
//		
//		PlayerResponse response = restTemplate.getForObject(uri,
//				PlayerResponse.class);

//		PlayerResponse response = getPlayer(playerId);
//		if (response != null && response.getId() != null)
			return true;
//		else
//			return false;

	}
	
	
	public Iterable<League> getAllLeagues()
	{
		return leagueRepository.findAll();
	}

//	public void setPlayerClient(PlayerClient playerClient) {
//		this.playerClient = playerClient;
//	}

	public void deleteLeague(String leagueId)
	{
		Integer id = Integer.valueOf(leagueId);
		if (id==null)
			throw new LeagueValidationException(LeagueExceptions.INVALID_LEAGUE_ID);
		Set<String> playerIds = getPlayersInLeague(leagueId);
		for (String playerId:playerIds)
		{
			try {removePlayerFromLeague(id, playerId);} catch (Exception e) {e.getMessage();}
		}
		
		leagueRepository.delete(id);
	}
	
	
}
