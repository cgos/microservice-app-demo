
# Preparation
* Configure prometheus.yml
* Start Grafana
* All code and presentation on github account
* skip log and kubernetes
* Quickly show the Phone Book application
- Gateway
- Front end
- Backend + BD
- Load testing Locust


# Grafana
* Start Grafana ```docker run -d --name=grafana -p 3000:3000 grafana/grafana```
* http://localhost:3000
* Add Data Source with the local real IP e.g.: ```http://10.62.26.21:9090```
* Go to Dashboard, import

# Start the services
* Gateway port 8080
* Front port 8082
* Backend port 8083
* Show the application: http://localhost:8082/

# Start Prometheus


# Metric
* Configure prometheus.yml
* Go to Prometheus: http://localhost:9090/graph
* Actuator (not running) http://localhost:8083/actuator/
* Add micrometer and actuator dependencies (micrometer is a wrapper around metric systems):
```xml
        <dependency>
			<groupId>io.micrometer</groupId>
			<artifactId>micrometer-registry-prometheus</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>
```
* Activate actuator web endpoint in application.properties: 
``` 
management.endpoints.web.exposure.include=*
spring.jackson.serialization.indent_output=true
``` 
* Show metrics: http://localhost:8082/actuator
* Show more metrics: http://localhost:8082/actuator/loggers
* Show metric prometheus in actuator: http://localhost:8080/actuator/prometheus
* Run locust: 
```
locust -f ./locustfile.py --no-web -c 4 -r 1
```
* Show metric in actuator: http://localhost:8080/actuator/prometheus
* Show metric in prometheus: http://localhost:9090/graph
* Show metric in grafana: http://localhost:3000 
* and add this basic query: http_server_requests_seconds_count
* grafana: show basic spring boot actuator metrics

# Traces
* show pom.xml
* show Tracer, Span and SpanContext
## Agent / Sidecar / Framework
* start Jaeger: ```docker run -p5775:5775/udp -p6831:6831/udp -p6832:6832/udp -p16686:16686 jaegertracing/all-in-one:latest --log-level=debug```
* goto: http://localhost:16686
* Run one query: http://localhost:8082/
* Grab trace id from logs and search in Jaeger
* Enable random failures and attach logs
* Run Locust, show latency
* Show trace error with log attached: http.status_code=500

