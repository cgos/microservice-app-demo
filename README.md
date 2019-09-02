# Phone Book Microservice demo applications

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



* Add micrometer dependency (sur 2/3 projets)
* Add: management.endpoints.web.exposure.include=* (sur 1/3 projet)
* Show: http://localhost:8083/actuator/prometheus
* jmx exporter vs micrometer: https://github.com/prometheus/jmx_exporter

## Prometheus
* Install prometheus: https://prometheus.io/docs/prometheus/latest/installation/
* Create prometheus config file: fix targets IP addresses
* Start Prometheus ```docker run -d --name=prometheus -p 9090:9090 -v /Users/gosselinchristian/Sandboxes/cgos/microservice-app-demo/prometheus/prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus --config.file=/etc/prometheus/prometheus.yml```
* Show request per second metrics

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