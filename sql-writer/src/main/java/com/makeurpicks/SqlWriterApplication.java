package com.makeurpicks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

import com.makeurpicks.writer.LeagueProcessor.LeagueChannels;
import com.makeurpicks.writer.SeasonProcessor.SeasonChannels;

@EnableBinding({SeasonChannels.class,LeagueChannels.class})
@SpringBootApplication
public class SqlWriterApplication {

	public static void main(String[] args) {
		SpringApplication.run(SqlWriterApplication.class, args);
	}
}
