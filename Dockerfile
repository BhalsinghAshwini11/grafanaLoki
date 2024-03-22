FROM openjdk:21

ADD build/libs/grafanaLoki-0.0.1-SNAPSHOT.jar /grafanaLoki-0.0.1-SNAPSHOT.jar

ENTRYPOINT java -jar /grafanaLoki-0.0.1-SNAPSHOT.jar