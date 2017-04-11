package com.makeurpicks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.makeurpicks.message.channels.SqlChannels;


//@EnableResourceServer
@IntegrationComponentScan
@EnableBinding(SqlChannels.class)
@SpringBootApplication
@EnableEurekaClient
@EnableJpaRepositories
public class LeagueApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(LeagueApplication.class, args);
	}

	
//	@Configuration
    @EnableWebSecurity
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    protected static class SecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            /*http.authorizeRequests()
            .antMatchers("/leagues/types", "/leagues/env", "/leagues/info", "/leagues/health").permitAll()
                    .anyRequest().authenticated()
                    .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
            ;*/
        	http.authorizeRequests().anyRequest().authenticated().and().sessionManagement()
        	 					.sessionCreationPolicy(SessionCreationPolicy.NEVER);
        }
        @Override
        public void configure(WebSecurity web) throws Exception {
        			web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources",
        					"/configuration/security", "/swagger-ui.html", "/webjars/**");
        }
    }
  

	/*@Autowired
	LeagueService leagueService;
	@Bean 
	public CommandLineRunner populateDb() {
		return args->{
			String player1Id = UUID.randomUUID().toString();
//			int leagueId = UUID.randomUUID().hashCode();
			String seasonId = UUID.randomUUID().toString();
			League league = new LeagueBuilder()
					.withAdminId(player1Id)
					.withName("pickem")
					.withPassword("football")
					.withSeasonId(seasonId).withNoSpreads()
					.build();
			leagueService.createLeague(league);
			PlayerLeague playerLeague = new PlayerLeague(new PlayerLeagueId(league.getId(),player1Id));
			playerLeague.setLeagueId(league.getId());
			playerLeague.setLeagueName(league.getLeagueName());
			playerLeague.setPassword(league.getPassword());
			playerLeague.setPlayerId(player1Id);
			leagueService.joinLeague(playerLeague);
			
		};
	};*/
	
}
