package edu.bit.kuber_demo;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfiguration {

    @Bean
    public Counter totalLogRequests(MeterRegistry registry) {
        return Counter.builder("app.log.requests.total")
                .description("Total number of /log requests")
                .register(registry);
    }

    @Bean
    public Counter successLogRequests(MeterRegistry registry) {
        return Counter.builder("app.log.requests.success")
                .description("Successful log requests")
                .register(registry);
    }

    @Bean
    public Counter failedLogRequests(MeterRegistry registry) {
        return Counter.builder("app.log.requests.failed")
                .description("Failed log requests")
                .register(registry);
    }

    @Bean
    public Timer logProcessingTimer(MeterRegistry registry) {
        return Timer.builder("app.log.processing.time")
                .description("Time taken to process log requests")
                .register(registry);
    }

}
