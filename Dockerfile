FROM tomcat:latest
COPY /gen-war/SpConsole.war /usr/local/tomcat/webapps/
