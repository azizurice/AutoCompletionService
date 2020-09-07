FROM tomcat:latest
LABEL maintainer="Azizur Rahman"
ADD target/auto-completion-service.war /usr/local/tomcat/webapps/
EXPOSE 8080
CMD ["catalina.sh", "run"]




# FROM java:14
# EXPOSE 8080
#ADD target/auto-completion-service.war auto-completion-service.war
#ENTRYPOINT ["java","-war","auto-completion-service"]