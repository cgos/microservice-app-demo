## Grafana
* Start Grafana ```docker run -d --name=grafana -p 3000:3000 grafana/grafana```
* http://localhost:3000
* Add Data Source with the local real IP e.g.: ```http://192.168.1.138:9090```
* Go to Dashboard, import

# Start the services
* Gateway port 8080
* Front port 8082
* Backend port 8083

# Start Prometheus
* Prometheus: already installed
* Go to: http://localhost:9090/graph

## Logs
* skip

## Metric
* Actuator
* http://localhost:8083/actuator/
* Add micrometer and actuator dependencies:```xml
        <dependency>
			<groupId>io.micrometer</groupId>
			<artifactId>micrometer-registry-prometheus</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>```
* Activate actuator web endpoint in application.properties: ``` management.endpoints.web.exposure.include=*
spring.jackson.serialization.indent_output=true```
* 
* Run locust
* Show metric in actuator: http://localhost:8080/actuator/prometheus
* Show metric in prometheus: 
* Show metric in grafana and add this basic query: http_server_requests_seconds_count
* In grafana add the json spring-boot dashboard

## Traces


# Start Jaeger
* Jaeger: already installed
* goto: http://localhost:16686

