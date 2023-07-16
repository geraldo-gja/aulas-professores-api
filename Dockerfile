FROM openjdk

WORKDIR /app

COPY target/teste-fourd-0.0.1-SNAPSHOT.jar /app/teste-fourd.jar

ENTRYPOINT ["java", "-jar", "teste-fourd.jar"]