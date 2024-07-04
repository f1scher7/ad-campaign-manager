FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /AdCampaignManager

COPY pom.xml .
COPY src ./src

RUN mvn clean install

FROM openjdk:17-alpine
WORKDIR /AdCampaignManager
COPY --from=build /AdCampaignManager/target/AdCampaignManager-0.0.1-SNAPSHOT.jar /AdCampaignManager/adcampaignmanager.jar

EXPOSE 8080
CMD ["java", "-jar", "adcampaignmanager.jar"]