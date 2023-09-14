FROM openjdk:17-alpine
ARG JAR_FILE=target/ChildrenPage-0.0.1-SNAPSHOT.jar
RUN mkdir /childrenPage
WORKDIR /childrenPage
COPY ${JAR_FILE} /childrenPage
ENTRYPOINT java -jar /childrenPage/ChildrenPage-0.0.1-SNAPSHOT.jar