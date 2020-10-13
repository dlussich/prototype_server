package com.nbastandings.prototype;

import com.nbastandings.prototype.controller.StandingsController;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PrototypeApplicationTests {

	@Autowired
	private StandingsController controller;

	@Value("${server.port}")
	private String portNumber;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
		assertThat(portNumber).isEqualTo("9082");
	}

}
