package com.example.phonebookfront.client;

import feign.okhttp.OkHttpClient;
import feign.opentracing.TracingClient;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayClientConfiguration {
    @Autowired
    Tracer tracer;

    @Bean
    public TracingClient client(){
        return new TracingClient(new OkHttpClient(), tracer);
    }
}
