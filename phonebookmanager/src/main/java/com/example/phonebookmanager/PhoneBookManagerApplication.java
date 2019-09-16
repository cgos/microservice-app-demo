package com.example.phonebookmanager;

import io.jaegertracing.Configuration;
import io.jaegertracing.Configuration.ReporterConfiguration;
import io.jaegertracing.Configuration.SamplerConfiguration;
import io.jaegertracing.internal.samplers.ConstSampler;
import io.opentracing.Tracer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PhoneBookManagerApplication {

    @Bean
    public Tracer tracer() {
        SamplerConfiguration samplerConfiguration = SamplerConfiguration.fromEnv().withType(ConstSampler.TYPE).withParam(1);
        ReporterConfiguration reporterConfiguration = ReporterConfiguration.fromEnv().withLogSpans(true);
        Configuration configuration = new Configuration("phone-book-manager")
                .withSampler(samplerConfiguration)
                .withReporter(reporterConfiguration);
        return configuration.getTracer();
    }

    public static void main(String[] args) {
        SpringApplication.run(PhoneBookManagerApplication.class, args);
    }
}
