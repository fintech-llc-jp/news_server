# マルチアーキテクチャ対応
FROM --platform=$BUILDPLATFORM eclipse-temurin:17-jdk-jammy as builder

WORKDIR /app
COPY gradle/ gradle/
COPY gradlew build.gradle.kts ./
RUN chmod +x ./gradlew

COPY src/ src/
RUN ./gradlew clean build -x test

FROM --platform=$TARGETPLATFORM eclipse-temurin:17-jre-jammy

WORKDIR /app

RUN apt-get update && apt-get install -y \
    curl \
    && rm -rf /var/lib/apt/lists/*

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]