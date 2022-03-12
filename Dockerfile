FROM amazoncorretto:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} whiskyup.jar
CMD ["java", "-jar", "whiskyup.jar"]