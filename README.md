# Phone Book Microservice demo applications

This simple project contains 4 main components:
- Frontend
- Gateway
- Backend service
- Database

| Service                                   | Language      | Description                                                                                                                       |
| ----------------------------------------- | ------------- | -----------------------------------------------------|
| [phonebookfront](./phonebookfront)        | Java          | Provide Thymeleaf template for basic CRUD.           |
| [gateway](./gateway)                      | Java          | Spring Cloud Gateway, exposes routes.                |
| [phonebookmanager](./phonebookmanager)    | Java          | Rest web service controller and database repository. |
| [embedded database](./phonebookmanager)   | H2            | Stores the items.                                    |


# Installation
First you need to git clone this repository

* Install Locust: https://docs.locust.io/en/latest/installation.html#

# Option 1: Run locally



* Add micrometer dependency (sur 2/3 projets)
* Add: management.endpoints.web.exposure.include=* (sur 1/3 projet)
* Show: http://localhost:8083/actuator/prometheus
* jmx exporter vs micrometer: https://github.com/prometheus/jmx_exporter
* install prometheus: https://prometheus.io/docs/prometheus/latest/installation/
* create prometheus config file
* docker run -d --name=prometheus -p 9090:9090 -v /Users/gosselinchristian/Sandboxes/cgos/microservice-app-demo/prometheus/prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus --config.file=/etc/prometheus/prometheus.yml
* show request per second metrics
* docker run -d --name=grafana -p 3000:3000 grafana/grafana
* http://localhost:3000
* import from grafana.com https://grafana.com/grafana/dashboards/4701
* build a dashboard with http_server_requests_seconds_count


## Start locust
* locust -f ./locustfile.py --no-web -c 4 -r 1
* http://localhost:8089/