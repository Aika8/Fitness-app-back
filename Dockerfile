FROM openjdk:15
ADD target/fitness-app.jar fitness-app.jar
ENTRYPOINT ["java", "-jar","fitness-app.jar"]
EXPOSE 8080
CMD ["java", "-jar", "fitness-app.jar"]