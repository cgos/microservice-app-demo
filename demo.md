# Start the services
* Gateway port 8080
* Front port 8082
* Backend port 8083

## Logs
* skip

## Metric
* Actuator
* http://localhost:8083/actuator/
* add in application.properties: ``` management.endpoints.web.exposure.include=*
spring.jackson.serialization.indent_output=true```

## Traces

# Start Prometheus
* Prometheus: already installed
* Go to: http://localhost:9090/graph

# Start Jaeger
* Jaeger: already installed
* goto: http://localhost:16686

