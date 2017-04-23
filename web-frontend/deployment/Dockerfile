FROM frolvlad/alpine-oraclejdk8:full

EXPOSE 8890

COPY ./target/jarta-web-frontend-1705.1-SNAPSHOT-jar-with-dependencies.jar jarta.jar

ENTRYPOINT ["java", "-cp", "jarta.jar", "jarta.frontend.WebFrontend"]
