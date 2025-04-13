package com.example.Sahtech;

import com.example.Sahtech.config.MongoTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataMongoTest
@Import(MongoTestConfig.class)
@ActiveProfiles("test")
class SahtechApplicationTests {

	@Test
	void contextLoads() {
		// Ce test vérifie que le contexte de l'application se charge correctement
	}

}
