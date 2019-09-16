package com.example.phonebookfront;

import io.jaegertracing.Configuration;
import io.jaegertracing.internal.samplers.ConstSampler;
import io.opentracing.Tracer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableFeignClients
public class PhoneBookFrontApplication {

    @Bean
    public Tracer tracer() {
        Configuration.SamplerConfiguration samplerConfiguration = Configuration.SamplerConfiguration.fromEnv()
                .withType(ConstSampler.TYPE)
                .withParam(1);

        Configuration.ReporterConfiguration reporterConfiguration = Configuration.ReporterConfiguration.fromEnv().
                withLogSpans(true);

        Configuration configuration = new Configuration("phone-book-frontend")
                .withSampler(samplerConfiguration)
                .withReporter(reporterConfiguration);

        return configuration.getTracer();
    }

    public static void main(String[] args) {
        SpringApplication.run(PhoneBookFrontApplication.class, args);
    }
}
