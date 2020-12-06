package br.casara.sigu;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(
  classes = Application.class,
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class ApplicationTests {

  @Test
  public void test_run_shouldLoadContext() {
    final var applicationContext = SpringApplication.run(Application.class, "Hello", "World");
    assertTrue(applicationContext.isActive(), "Application context is not active");
  }

}
