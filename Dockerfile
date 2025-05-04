FROM gradle:jdk21
COPY --chown=gradle:gradle ./project /home/gradle/project
COPY --chown=gradle:gradle ./swift.csv /home/gradle/project/src/main/resources/swift.csv
WORKDIR /home/gradle/project
RUN gradle build -x test
EXPOSE 8080
CMD ["java", "-jar", "build/libs/SwiftAPI-0.0.1-SNAPSHOT.jar"]