package com.makeurpicks.service;

import java.util.HashSet;
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

	/*@Autowired
	private LeagueRepository leagueRepository;*/
	
	public League createLeague(League league) throws LeagueValidationException {
		validateLeague(league);

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
		return HelperUtils.getLeagueNameFromLeagues(playerLeagueRepository.findLeaguesByPlayerId(playerId));
	}
	
	public Set<String> getPlayersInLeague(int leagueid) throws LeagueValidationException {
		 return new HashSet<String> (leagueRepository.findPlayerIdsById(leagueid));
		
	}

	public void joinLeague(PlayerLeague playerLeague)
	{
		/*if (playerLeague.getLeague() == null)
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
//				playerLeague.setLeague(league);
			}
		}
		
//		joinLeague(playerLeague.getLeague().getId(), playerLeague.getPlayerId(), playerLeague.getPassword());
*/	}

	protected void joinLeague(int leagueId, String playerId, String password) throws LeagueValidationException {
		
//		if (!isValidPlayer(playerId))
//			throw new LeagueValidationException(LeagueExceptions.PLAYER_NOT_FOUND);
		
		League league = leagueRepository.findOne(leagueId);
		if (league == null)
			throw new LeagueValidationException(LeagueExceptions.LEAGUE_NOT_FOUND);
		
		if (league.getPassword()!=null&&!"".equals(league.getPassword())&& !league.getPassword().equals(password))
			throw new LeagueValidationException(LeagueExceptions.INVALID_LEAGUE_PASSWORD);
		
		addPlayerToLeague(league, playerId);

	}
	
	protected void addPlayerToLeague(League league, String playerId)
	{
		/*playerLeagueRepository.addPlayerToLeague(playerId, league.getId());
		leagueRepository.addPlayerToLeague(league, playerId);*/
	}

	public League getLeagueById(int leagueId) {
		return leagueRepository.findOne(leagueId);
	}

	public League getLeagueByName(String name) {
		Iterable<League> leagues = leagueRepository.findAll();
		for (League league:leagues)
		{
			if (league.getLeagueName().equals(name))
				return league;
		}
		
		return null;
	}

	public void removePlayerFromLeagye(int leagueId, String playerId) {
		League league = leagueRepository.findOne(leagueId);
		if (league == null)
			throw new LeagueValidationException(LeagueExceptions.LEAGUE_NOT_FOUND);
		
		/*playerLeagueRepository.removePlayerFromLeague(playerId, league.getId());
		leagueRepository.removePlayerFromLeague(league, playerId);*/
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

	private boolean isLeagueValid(int leagueId) {
		League league = leagueRepository.findOne(leagueId);
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

	public void deleteLeague(int leagueId)
	{
		Set<String> playerIds = getPlayersInLeague(leagueId);
		for (String playerId:playerIds)
		{
			try {removePlayerFromLeagye(leagueId, playerId);} catch (Exception e) {e.getMessage();}
		}
		
		leagueRepository.delete(leagueId);
	}
}
