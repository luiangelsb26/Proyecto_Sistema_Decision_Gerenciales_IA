FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /build
COPY pom.xml ./
COPY src ./src
RUN mvn -B -ntp package

FROM tomcat:11.0-jdk21-temurin
COPY --from=build /build/target/Proyecto_Sistema_Decision_Gerenciales_IA-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war
RUN groupadd --system mypes && useradd --system --gid mypes --home-dir /usr/local/tomcat mypes \
    && chown -R mypes:mypes /usr/local/tomcat
USER mypes
ENV PORT=8080
EXPOSE 8080
CMD ["catalina.sh", "run"]
