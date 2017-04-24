package com.makeurpicks.message.channels;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import com.makeurpicks.domain.League;

@MessagingGateway
public interface LeagueWriter {
	@Gateway(requestChannel="output")
	public void write(League value);
}
