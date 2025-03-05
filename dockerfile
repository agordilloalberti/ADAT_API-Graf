FROM azul/zulu-openjdk:17-latest
VOLUME /tmp
COPY build/libs/*.war
CMD ["java", "-jar", "/app.war"]