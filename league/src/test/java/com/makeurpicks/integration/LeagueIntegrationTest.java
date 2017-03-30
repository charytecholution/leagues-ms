/**
 * 
 */
package com.makeurpicks.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makeurpicks.domain.League;
import com.makeurpicks.domain.PlayerLeague;
import com.makeurpicks.domain.PlayerLeagueId;

/**
 * @author Shyam
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class LeagueIntegrationTest {

	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext context;
	
	@Before
	public void init() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	@SqlGroup({
		@Sql(scripts = "classpath:League_insert.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
		@Sql(scripts = "classpath:League_delete.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD),
		})
	public void getAllLeague() throws Exception {
		mockMvc.perform(get("/"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		.andExpect(jsonPath("$[0].id", is("10")))
		.andExpect(jsonPath("$[0].leagueName", is("NFL_GA")))
		.andExpect(jsonPath("$[0].seasonId", is("555")))
		.andExpect(jsonPath("$[0].adminId", is("admin")));
	}
	
	
	@Test
	public void getLeagueTypes() throws Exception {
		mockMvc.perform(get("/types"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		.andExpect(content().json("[pickem, suicide]"));
	}
	
	@Test
	@SqlGroup({
		@Sql(scripts = "classpath:League_insert.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
		@Sql(scripts = "classpath:League_delete.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD),
		})
	public void getLeagueById() throws Exception {
		 mockMvc.perform(get("/10"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		.andExpect(jsonPath("id", is("10")))
		.andExpect(jsonPath("leagueName", is("NFL_GA")))
		.andExpect(jsonPath("seasonId", is("555")))
		.andExpect(jsonPath("adminId", is("admin")));
	}
	
	
	@Test
	@SqlGroup({
		@Sql(scripts = "classpath:League_insert.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
		@Sql(scripts = "classpath:League_delete.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD),
		})
	public void getLeagueBySeasonId() throws Exception {
		mockMvc.perform(get("/seasonid/555"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		.andExpect(jsonPath("$[0].id", is("10")))
		.andExpect(jsonPath("$[0].leagueName", is("NFL_GA")))
		.andExpect(jsonPath("$[0].seasonId", is("555")))
		.andExpect(jsonPath("$[0].adminId", is("admin")));
	}
	
	@Test
	@Transactional
	public void createLeague() throws Exception {
		final League league = new League();
		league.setId("20");
		league.setLeagueName("NFL");
		league.setSeasonId("555");
		league.setAdminId("admin");
		final ObjectMapper mapper = new ObjectMapper();
		final String leagueJson = mapper.writeValueAsString(league);
		
		mockMvc.perform(post("/").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).principal(() -> "admin")
				.content(leagueJson)).andExpect(status().isOk())
		.andExpect(jsonPath("id", is("20")))
		.andExpect(jsonPath("leagueName", is("NFL")))
		.andExpect(jsonPath("seasonId", is("555")))
		.andExpect(jsonPath("adminId", is("admin")));
	}
	
	@Test
	@SqlGroup({
		@Sql(scripts = "classpath:League_insert.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
		@Sql(scripts = "classpath:League_delete.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD),
		})
	public void updateLeague() throws Exception {
		final League league = new League();
		league.setId("10");
		league.setLeagueName("NFL");
		league.setSeasonId("555");
		league.setAdminId("admin");
		final ObjectMapper mapper = new ObjectMapper();
		final String leagueJson = mapper.writeValueAsString(league);
		mockMvc.perform(put("/").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(leagueJson)).andExpect(status().isOk())
		.andExpect(jsonPath("id", is("10")))
		.andExpect(jsonPath("leagueName", is("NFL")))
		.andExpect(jsonPath("seasonId", is("555")))
		.andExpect(jsonPath("adminId", is("admin")));
	}
	
	
	@Test
	@SqlGroup({
		@Sql(scripts = "classpath:PlayerLeague_insert.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
		@Sql(scripts = "classpath:PlayerLeague_delete.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD),
		})
	public void getLeaguesForPlayer() throws Exception {
		mockMvc.perform(get("/player/200")).andExpect(status().isOk())
		.andExpect(jsonPath("$[0].leagueName", is("NFL_GA")));
	} 
	
	@Test
	@SqlGroup({
		@Sql(scripts = "classpath:PlayerLeague_insert.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
		@Sql(scripts = "classpath:PlayerLeague_delete.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD),
		})
	public void addPlayerToLeague() throws Exception {
		final PlayerLeague playerLeague = new PlayerLeague(new PlayerLeagueId("10","200"));
		playerLeague.setId(new PlayerLeagueId("10","200"));
		playerLeague.setLeagueId("10");
		playerLeague.setLeagueName("NFL_GA");
		final ObjectMapper objectMapper = new ObjectMapper();
		final String playerLeagueJson = objectMapper.writeValueAsString(playerLeague);
		mockMvc.perform(post("/player").principal(()->"admin").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(playerLeagueJson)).andExpect(status().isOk());
	}
	
	@Test
	@SqlGroup({
		@Sql(scripts = "classpath:PlayerLeague_insert.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
		@Sql(scripts = "classpath:PlayerLeague_delete.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD),
		})
	public void addPlayerToLeague_admin() throws Exception {
		final PlayerLeague playerLeague = new PlayerLeague(new PlayerLeagueId("10","200"));
		playerLeague.setId(new PlayerLeagueId("10","200"));
		playerLeague.setLeagueId("10");
		playerLeague.setLeagueName("NFL_GA");
		final ObjectMapper objectMapper = new ObjectMapper();
		final String playerLeagueJson = objectMapper.writeValueAsString(playerLeague);
		mockMvc.perform(post("/player/admin").principal(()->"admin").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(playerLeagueJson)).andExpect(status().isOk());
	}
	
	
	@Test
	@SqlGroup({
		@Sql(scripts = "classpath:League_insert.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
		@Sql(scripts = "classpath:League_delete.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD),
		})
	public void getLeagueByName() throws Exception {
		mockMvc.perform(get("/name/NFL_GA").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isOk())
		.andExpect(jsonPath("id", is("10")))
		.andExpect(jsonPath("leagueName", is("NFL_GA")))
		.andExpect(jsonPath("seasonId", is("555")))
		.andExpect(jsonPath("adminId", is("admin")));;
	}
	
	
	@Test
	@SqlGroup({
		@Sql(scripts = "classpath:League_insert.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
		@Sql(scripts = "classpath:League_delete.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD),
		})
	public void removePlayerFromLeagye() throws Exception {
		final PlayerLeague playerLeague = new PlayerLeague(new PlayerLeagueId("10","200"));
		playerLeague.setId(new PlayerLeagueId("10","200"));
		playerLeague.setLeagueId("10");
		playerLeague.setLeagueName("NFL_GA");
		final ObjectMapper objectMapper = new ObjectMapper();
		final String playerLeagueJson = objectMapper.writeValueAsString(playerLeague);
		mockMvc.perform(delete("/player").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(playerLeagueJson)).andExpect(status().isOk());
	}
	
	@Test
	@SqlGroup({
		@Sql(scripts = "classpath:League_insert.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
		@Sql(scripts = "classpath:League_delete.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD),
		})
	public void getPlayersInLeague() throws Exception {
		mockMvc.perform(get("/player/leagueid/10"))
		.andExpect(status().isOk()).andExpect(content().string("[\"admin\"]"));
	}
	
	
	@Test
	@SqlGroup({
		@Sql(scripts = "classpath:League_insert.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
		@Sql(scripts = "classpath:League_delete.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD),
		})
	public void deleteLeague() throws Exception {
		mockMvc.perform(delete("/10")).andExpect(status().isOk());
	}
	
	
	@Test
	@SqlGroup({
		@Sql(scripts = "classpath:League_insert.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
		@Sql(scripts = "classpath:League_delete.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD),
		})
	public void deletePlayerFromLeague() throws Exception {
		mockMvc.perform(delete("/player/league/10/200").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		.andExpect(status().isOk()).andExpect(content().string("true"));
	}
}
