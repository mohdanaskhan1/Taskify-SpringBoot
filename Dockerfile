
# Base Image
FROM eclipse-temurin:25-jdk

#Working Directory
WORKDIR /app

#Copy Files
COPY target/Taskify-0.0.1-SNAPSHOT.jar app.jar

#Expose Port
EXPOSE 8082

#Startup Command
ENTRYPOINT ["java","-jar", "app.jar"]

