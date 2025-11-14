package com.example.servicea;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
    "service.b.url=http://localhost:8081/api/b/data"
})
class ServiceAApplicationTest {

    @Test
    void contextLoads() {
        // Test that the Spring context loads successfully
    }
}