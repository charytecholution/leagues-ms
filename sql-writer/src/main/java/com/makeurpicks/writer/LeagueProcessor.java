package com.makeurpicks.writer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.messaging.Message;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

import com.makeurpicks.domain.League;
import com.makeurpicks.repository.LeagueRepository;

@Component
@MessageEndpoint
public class LeagueProcessor {
	private LeagueRepository leagueRepository;
	@Autowired
	public  LeagueProcessor(LeagueRepository leagueRepository) {
		this.leagueRepository=leagueRepository;
	}
	@StreamListener(LeagueChannels.INPUT)
	public void onLeague(Message<League> message) {
		leagueRepository.save(message.getPayload());
	}

	public interface LeagueChannels {
		public String INPUT="league";
		@Input(LeagueChannels.INPUT)
		SubscribableChannel league();
	}
}

