# Getting Started WIKI 
Please note this POC is gradle project and latest Java and spring boot, created via https://start.spring.io/!!
There are many ways you can configure your app to expose logs to monitoring tools(for searching, monitoring, and analyzing your logs)
see Excalidraw in the project linked for tried Options in this project. TODO

# Disclaimer
The POC is purely for learning/exploration purpose locally and can have bugs or misconfigurations for diff env or machines. 
Needed proper testing correct optimizations. 

Few options tried as recommended here: https://grafana.com/docs/loki/latest/send-data/

## Option 1, Third party client
loki-logback-appender (Java) https://github.com/loki4j/loki-logback-appender
* This option is sending the logs directly from you local running app to LOKI server.
* Take a look at logback-spring-xml file. Quite of documentation and steps covered in file itself.

## Option 2, Promtail client
Promtail is the client of choice when we are running Kubernetes, as we can configure it to automatically scrape logs from pods running on the same node that Promtail runs on.
* This option is very much feels like Prometheus with scrapping job but for Logs.
* Purely file based, hence can also be suitable for deployments like baremetal
* Take a look at docker compose file and ../promtail/config.yml file. It will give you the glimse how it will work. 
* Again, in this POC I am scrapping it from my docker container to get the idea, also needed to get deep if we wanted to play with labelling and stuff.

## Option 3, OTEL Collector/Exporter 
This option is exploring the OTEL ecosystem capabilities directly for learning purpose. 
* In this option our otel collector pipeline is configured to receive logs from file and then transform to LOKI in otlp format. 
* Take a look at parsing logic of receiver, processor.. here actual stuff we can play around for labels and differnt log formats coming in K8s world.
* Take a look at docker compose file and ../otel/local-config.yml file 
* Due to some permission issues with windows docker desktop, I have manually copied logs from loki app docker container to /logs/locallokilogs.log.
* Having manual log dump not good but server the purpose of learning from **OTEL filelog receiver** and exporter.
* With this option you can play with different log formats and play with it.
* TODO fix multi line logs, do not work as expected
* TODO, play around with bigger logs size/ error stacktrace etc. 

## Option 4, Grafana Agent (NOT tried yet)
Recommended from Grafana community, they have implemented a wrapper client named which can collect and export data to OTEL/PROMETHEUS world
* NOT tried locally yet for this POC, learn from option 3 at local scale I feel better to start with. 
* Then on bigger scale we could use grafana agent to have all our Instrumented data (metrics/logs/traces) from one source. 
* https://grafana.com/docs/agent/latest/

## How to run, WIKI
We will use docker compose.
* gradle assemble or bootJar from terminal (needed specific java config and gradle version) or do it via IDE
* docker-compose up --build (check all the services in container are ready)

##  Generate noise for log generation in application
Do call some api end points towards respective apis. Or run test 
### Test Example 
* http://localhost:8484/ping 
* http://localhost:8484/random
* http://localhost:8484/health
* http://localhost:8484/addCustomer?customerId=123
* http://localhost:8484/balance?customerId=123
* http://localhost:8484/deposit?customerId=123
* http://localhost:8484/withdraw?customerId=123

## Grafana local url
It will ask you to login use admin/admin, and on prompt use create new password
* http://localhost:3000/login

## Extra information for visualization configuration
* Add the respective data sources to Grafana. (Need to automate that, current grafana provisioning I have to look/fix)
* Add Loki Datasource
* Play around with labels and LokiQL. https://grafana.com/docs/loki/latest/query/
* AIM is to use as much of query filter with necessary labels to get result faster.

### How to clean up
docker-compose down -v

### Troubleshooting
WINDOWS specific only! If 8080 or any needed port is taken or not cleaned properly, Find it first.
* netstat -ano | findstr :<PORT>

Replace <PORT> with 8080 then kill the process
* taskkill /PID 21260 /F