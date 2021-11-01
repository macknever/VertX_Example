# DOcker example using fatjar
# - docker build -t vertxexampleselfbuilt
# - docker run -t -i -p 8080:8080 vertxexampleselfbuilt

# https://hub.docker.com/_/adoptopenjdk
FROM adoptopenjdk:11-jre-hotspot

# Alternative https://hub.cocker.com/_/amazoncorretto
# FROM amazoncorretto:11

ENV FAT_JAR vertxexample_selfcode-1.0-SNAPSHOT.jar
ENV APP_HOME /usr/app