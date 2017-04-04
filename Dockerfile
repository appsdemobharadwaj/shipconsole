FROM tomcat:latest
COPY /gen-war/SpConsole.war /usr/local/tomcat/webapps/
EXPOSE 8080
CMD ["catalina.sh", "run"]
