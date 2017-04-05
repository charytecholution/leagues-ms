package com.makeurpicks.message.service;

import org.springframework.cloud.stream.binder.PartitionSelectorStrategy;

public class PartionkeySelectorStrategyImp implements PartitionSelectorStrategy   {

	@Override
	public int selectPartition(Object key, int partCount) {
		return (int)key;
	}
	
}

