package com.makeurpicks.message.service;

import org.springframework.cloud.stream.binder.PartitionKeyExtractorStrategy;
import org.springframework.messaging.Message;

import com.makeurpicks.domain.League;
import com.makeurpicks.domain.Season;

public class PartitionkeyExtractorImp implements PartitionKeyExtractorStrategy  {

	@Override
	public Object extractKey(Message<?> message) {
		//TODO: need to use instanceOf
		Object payload= message.getPayload();
		if(payload instanceof Season)
			return MessagePlatformConstants.Queues.SEASON.getQueueindex();
			
		if(payload instanceof League)
			return MessagePlatformConstants.Queues.LEAGUE.getQueueindex();
		return 1;
	}
}
