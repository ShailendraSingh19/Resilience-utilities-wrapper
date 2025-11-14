# Resilience Utilities Wrapper -- Integration Guide

This README gives a full, step-by-step guide to integrate the
**Resilience Utilities Wrapper JAR** into any Spring Boot microservice.

------------------------------------------------------------------------

## 📦 1. Project Structure

Create the folder manually:

    your-microservice/
     ├── src/
     ├── pom.xml
     ├── libs/
     │     └── resilience-utilities-1.0.0.jar

Paste the JAR inside `libs/`.

------------------------------------------------------------------------

## ⚙️ 2. Add Local JAR Dependency in `pom.xml`

``` xml
<dependency>
    <groupId>com.example</groupId>
    <artifactId>resilience-utilities</artifactId>
    <version>1.0.0</version>
    <scope>system</scope>
    <systemPath>${project.basedir}/libs/resilience-utilities-1.0.0.jar</systemPath>
</dependency>
```

------------------------------------------------------------------------

## 🔧 3. Required Dependencies

``` xml
<!-- Actuator -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <!-- AOP -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-aop</artifactId>
        </dependency>

        <!-- Resilience4j Annotations (needed) -->
        <dependency>
            <groupId>io.github.resilience4j</groupId>
            <artifactId>resilience4j-spring-boot3</artifactId>
            <version>2.0.2</version>
        </dependency>
```

------------------------------------------------------------------------

## 🏗️ 4. Add YAML Config to access the health metrics

``` yaml
management:
  endpoints:
    web:
      exposure:
        include: health,info,prometheus,circuitbreakers,retries,ratelimiters
  endpoint:
    health:
      show-details: always
```

------------------------------------------------------------------------

## 🚀 5. Usage

``` java
This method calls an external API and uses Resilience4j annotations to add CircuitBreaker, Retry, and RateLimiter protections.

**What each annotation does:**
- **@CircuitBreaker**: Opens the circuit when failures exceed threshold
- **@Retry**: Automatically retries failed calls before falling back
- **@RateLimiter**: Limits how many requests are allowed per time window

**The fallback methods are executed whenever:**
- All retries fail
- Circuit Breaker is OPEN
- RateLimiter blocks the request

**Important:** The flow of a request is upwards hence the order of the annotations is important.

```java
@RateLimiter(name = "externalApiLimiter", fallbackMethod = "rateLimiterFallback")
@Retry(name = "externalApiRetry")
@CircuitBreaker(name = "externalApiCB", fallbackMethod = "fallback")
public String callExternalApi() {
    return restTemplate.getForObject("https://example.com", String.class);
}

// Runs when CircuitBreaker is OPEN or when retries fail
public String fallback(Throwable t) {
    return "CircuitBreaker/Retry Fallback: " + t.getMessage();
}

// Runs ONLY when RateLimiter blocks the request
public String rateLimiterFallback(Throwable t) {
    return "RateLimiter Fallback: " + t.getMessage();
}
```

```

------------------------------------------------------------------------

## 📊 6. Metrics Endpoints

    /actuator/health
    /actuator/circuitbreakers
    /actuator/retries
    /actuator/ratelimiters

Example:

    http://localhost:8080/actuator/circuitbreakers

------------------------------------------------------------------------

## 🧪 7. Running the App

    mvn clean install
    mvn spring-boot:run

------------------------------------------------------------------------

## 🔍 8. Troubleshooting

**Circuit Breaker not recording failures?**

-   Do not swallow exceptions\
-   Ensure fallback has `Throwable`\
-   Remove try/catch unless rethrowing

------------------------------------------------------------------------

## 🧴 9. JAR Version

Keep a consistent version:

    resilience-utilities-1.0.0.jar

------------------------------------------------------------------------

## 📝 Summary

1.  Create folder\
2.  Paste JAR\
3.  Add dependency\
4.  Add YAML config\
5.  Annotate methods\
6.  Hit actuator endpoints
