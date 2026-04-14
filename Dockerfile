FROM eclipse-temurin:21-jre-alpine
# image de base : Linux Alpine + Java 21 JRE

WORKDIR /app
# crée le dossier /app dans le conteneur et s'y place

COPY build/libs/todo-0.0.1-SNAPSHOT.jar app.jar
# copie ton JAR local dans le conteneur, le renomme app.jar

EXPOSE 8080
# documente que l'app écoute sur le port 8080 (informatif)

ENTRYPOINT ["java", "-jar", "app.jar"]
# commande executée au démarrage du conteneur