FROM maven:3.8.6-jdk-8 as maven_builder
WORKDIR /app
COPY . .
RUN ["mvn","clean","install","-DskipTests=true"]

FROM tomcat:9.0.65-jre8-openjdk-slim

ENV DB_HOST_NAME=postgres
ENV DB_USER=dashboard
ENV DB_PASSWORD=docker
ENV TOMCAT_CATALINA_OPTS_XMS=Xms1024m
ENV TOMCAT_CATALINA_OPTS_XMX=Xmx1024m
ENV TOMCAT_CATALINA_OPTS_REMOTE_DEBUG=agentlib:jdwp=transport=dt_socket,address=8765,server=y,suspend=n
ENV TOMCAT_CATALINA_LOGS_DIR=/var/log

ENV CATALINA_OPTS="-Ddb_host_name=${DB_HOST_NAME}"
ENV CATALINA_OPTS="${CATALINA_OPTS} -Ddb_user=${DB_USER}"
ENV CATALINA_OPTS="${CATALINA_OPTS} -Ddb_password=${DB_PASSWORD}" 
ENV CATALINA_OPTS="${CATALINA_OPTS} -${TOMCAT_CATALINA_OPTS_XMS}"
ENV CATALINA_OPTS="${CATALINA_OPTS} -${TOMCAT_CATALINA_OPTS_XMX}"
ENV CATALINA_OPTS="${CATALINA_OPTS} -${TOMCAT_CATALINA_OPTS_REMOTE_DEBUG}"
ENV CATALINA_OPTS="${CATALINA_OPTS} -Djava.awt.headless=true"
ENV CATALINA_OPTS="${CATALINA_OPTS} -Dlogs.dir=${TOMCAT_CATALINA_LOGS_DIR}"

COPY ./docker/apache-tomcat/conf/context.xml /usr/local/tomcat/conf/
COPY --from=maven_builder /app/dashboard.web/target/dashboard.war /usr/local/tomcat/webapps
EXPOSE 8080
CMD ["/usr/local/tomcat/bin/catalina.sh", "run"]
