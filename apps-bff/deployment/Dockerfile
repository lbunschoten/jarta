FROM frolvlad/alpine-oraclejdk8:full

EXPOSE 8891

COPY ./target/apps-bff-1705.1-SNAPSHOT-jar-with-dependencies.jar jarta.jar

ENTRYPOINT ["java", "-cp", "jarta.jar", "AppsBffServer"]
