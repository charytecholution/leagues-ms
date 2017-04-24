package com.makeurpicks.message.channels;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import com.makeurpicks.domain.Season;

@MessagingGateway
public interface SeasonWriter {
	@Gateway(requestChannel="output")
	public void write(Season value);
	
}
