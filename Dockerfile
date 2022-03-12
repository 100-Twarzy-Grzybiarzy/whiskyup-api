FROM amazoncorretto:11 as builder
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} whiskyup.jar
RUN ["java", "-Djarmode=layertools", "-jar", "whiskyup.jar", "extract"]

FROM amazoncorretto:11
COPY --from=builder dependencies/ ./
COPY --from=builder snapshot-dependencies/ ./
COPY --from=builder spring-boot-loader/ ./
COPY --from=builder application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]