FROM tomee:8.0.2-microprofile

ADD gameon-vaadin-user/target/gameon-vaadin-user-1.0.war /usr/local/tomee/webapps/

COPY src/tomee/tomcat-users.xml /usr/local/tomee/conf/
COPY src/tomee/manager-context.xml /usr/local/tomee/webapps/manager/META-INF/context.xml
COPY src/tomee/host-manager-context.xml /usr/local/tomee/webapps/host-manager/META-INF/context.xml

EXPOSE 8080

CMD ["catalina.sh", "run"]



