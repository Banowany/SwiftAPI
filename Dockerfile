FROM gradle as build
COPY --chown=gradle:gradle ./project /home/gradle/project
COPY --chown=gradle:gradle ./swift.csv /home/gradle/project/src/main/resources/swift.csv
WORKDIR /home/gradle/project
RUN gradle build -x test

FROM openjdk:21
COPY --from=build /home/gradle/project/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]