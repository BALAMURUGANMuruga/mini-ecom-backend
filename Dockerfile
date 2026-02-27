# -------- Build Stage --------
FROM eclipse-temurin:17-jdk-alpine AS build

WORKDIR /app

# Copy everything
COPY . .

# Give execute permission to gradlew
RUN chmod +x gradlew

# Build jar
RUN ./gradlew build -x test

# -------- Run Stage --------
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy built jar from build stage
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]