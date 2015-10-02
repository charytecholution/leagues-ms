package com.makeurpicks.service;

import java.util.List;

import javax.ws.rs.PathParam;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.makeurpicks.domain.LeagueResponse;
 
@FeignClient("league")
public interface LeagueClient {
	
	@RequestMapping(value = "/leauges/{id}", method=RequestMethod.GET ,produces="application/json", consumes="application/json")
    public @ResponseBody LeagueResponse getLeagueById(@PathVariable("id") String id);
	
	@RequestMapping(method=RequestMethod.GET, value="/leagues/player/playerId/{id}",produces="application/json", consumes="application/json")
	public @ResponseBody List<LeagueResponse> getLeaguesForPlayer(@PathParam("id")String playerId);

}
