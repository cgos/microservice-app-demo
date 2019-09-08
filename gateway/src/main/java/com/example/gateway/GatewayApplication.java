package com.example.gateway;

import io.jaegertracing.Configuration;
import io.jaegertracing.internal.samplers.ConstSampler;
import io.jaegertracing.internal.samplers.ProbabilisticSampler;
import io.opentracing.Tracer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

	@Bean
	public Tracer tracer() {
		Configuration.SamplerConfiguration samplerConfiguration= Configuration.SamplerConfiguration.fromEnv()
				.withType(ConstSampler.TYPE)
				.withParam(1);
		Configuration.ReporterConfiguration reporterConfiguration = Configuration.ReporterConfiguration.fromEnv()
				.withLogSpans(true);
		Configuration configuration = new Configuration("gateway")
				.withSampler(samplerConfiguration)
				.withReporter(reporterConfiguration);
		return configuration.getTracer();
	}


	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

}
