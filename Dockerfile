FROM amazoncorretto:17

WORKDIR .
COPY . .
RUN ./gradlew clean bootJar

CMD java -jar build/libs/compressor-*.jar
