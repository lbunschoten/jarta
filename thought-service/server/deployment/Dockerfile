FROM frolvlad/alpine-oraclejdk8:full

COPY ./target/jarta-thought-service-server-1649.1-SNAPSHOT-jar-with-dependencies.jar jarta.jar

EXPOSE 9997

ENTRYPOINT ["java", "-cp", "jarta.jar", "jarta.thoughtservice.ThoughtServiceServer"]
