package com.makeurpicks.writer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.messaging.Message;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

import com.makeurpicks.domain.Season;
import com.makeurpicks.repository.SeasonRepository;

@Component
@MessageEndpoint
public class SeasonProcessor {
	private SeasonRepository seasonRepository;
	@Autowired
	public  SeasonProcessor(SeasonRepository seasonRepository) {
		this.seasonRepository=seasonRepository;
	}
	
	@StreamListener(SeasonChannels.INPUT)
	public void onSeason(Message<Season> message) {
		seasonRepository.save(message.getPayload());
	}

	
	public interface SeasonChannels {
		public String INPUT="season";
		@Input(SeasonChannels.INPUT)
		SubscribableChannel season();
	}
}
