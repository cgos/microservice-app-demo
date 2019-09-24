# Phone Book Microservice Metrics and OpenTracing demo application

Welcome, this application is a basic walkthough for how to instruments metrics and traces in a Java code base application.

There are four main git branches:
* master: contains everything
* base: the basic java application without metrics and tracing
* metric
* tracing

This simple project contains 4 main components:
- Frontend
- Gateway
- Backend service
- Database

| Service                                   | Language      | Description                                          |
| ----------------------------------------- | ------------- | -----------------------------------------------------|
| [phonebookfront](./phonebookfront)        | Java          | Provide Thymeleaf template for basic CRUD.           |
| [gateway](./gateway)                      | Java          | Spring Cloud Gateway, exposes routes.                |
| [phonebookmanager](./phonebookmanager)    | Java          | Rest web service controller and database repository. |
| [embedded database](./phonebookmanager)   | H2            | Stores the information.                              |
| [loadgenerator](./loadgenerator)          | Python/Locust | Sends requests through the frontend.                 |


# Installation
First you need to git clone this repository

* Install Locust: https://docs.locust.io/en/latest/installation.html#
* Install python BeautifulSoup: pip install beautifulsoup4

# Option 1: Run locally

## Prometheus
* Install prometheus: https://prometheus.io/docs/prometheus/latest/installation/
* Create prometheus config file: fix targets IP addresses
* Start Prometheus ```docker run -p 9090:9090 -v /Users/gosselinchristian/Sandboxes/cgos/observability-app-demo/prometheus/prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus --config.file=/etc/prometheus/prometheus.yml --log.level=debug```
* Go to: http://localhost:9090/graph
* Show request per second metrics

## Jaeger
* start Jaeger: ```docker run -p5775:5775/udp -p6831:6831/udp -p6832:6832/udp -p16686:16686 jaegertracing/all-in-one:latest --log-level=debug```
* goto: http://localhost:16686


## Demo
* Add micrometer dependency (sur 2/3 projets)
* Add: management.endpoints.web.exposure.include=* (sur 1/3 projet)
* Show: http://localhost:8083/actuator/prometheus
* jmx exporter vs micrometer: https://github.com/prometheus/jmx_exporter



## Grafana
* Start Grafana ```docker run -d --name=grafana -p 3000:3000 grafana/grafana```
* http://localhost:3000
* Add Data Source with the local real IP
* Import [dashboard]./grafana/dashboard Import from grafana.com https://grafana.com/grafana/dashboards/4701
* Show available metrics at: http://localhost:8080/actuator/prometheus
* build a dashboard with http_server_requests_seconds_count


## Start locust
* locust -f ./locustfile.py --no-web -c 4 -r 1
* http://localhost:8089/


## Tracing

### Java
* Add ``` 		<dependency>
			<groupId>io.jaegertracing</groupId>
			<artifactId>jaeger-client</artifactId>
			<version>0.32.0</version>
		</dependency>```
    ```<dependency>
			<groupId>io.github.openfeign.opentracing</groupId>
			<artifactId>feign-opentracing</artifactId>
			<version>0.3.0</version>
		</dependency>```
* Enable logging for Jaeger: ```logging.level.io.jaegertracing=DEBUG```
* Add opentracing-spring-cloud-starter dependency
* Add jaeger-client dependency
* Add bean tracer in the spring boot application:
``` 	@Bean
	public Tracer tracer() {
		Configuration.SamplerConfiguration samplerConfiguration = Configuration.SamplerConfiguration.fromEnv()
				.withType(ConstSampler.TYPE)
				.withParam(1);
		Configuration.ReporterConfiguration reporterConfiguration = Configuration.ReporterConfiguration.fromEnv().withLogSpans(true);
		Configuration configuration = new Configuration("phone-book-frontend")
				.withSampler(samplerConfiguration)
				.withReporter(reporterConfiguration);
		return configuration.getTracer();
	}```




# Option 2: Run in Kubernetes
Not yet