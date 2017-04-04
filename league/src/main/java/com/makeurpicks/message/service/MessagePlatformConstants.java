package com.makeurpicks.message.service;

public class MessagePlatformConstants {
	public enum Queues {
		SEASON(1),LEAGUE(2);
		int queueindex;
		Queues(int value) {
			queueindex=value;
		}
		
		public int getQueueindex() {
			return queueindex;
		}
	}

}
