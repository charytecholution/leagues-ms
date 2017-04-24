package com.makeurpicks.message.channels;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface SqlChannels {

	@Output
	MessageChannel output();
}


