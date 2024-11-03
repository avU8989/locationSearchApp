# Stage 1: Build the React frontend
FROM node:16-alpine AS frontend-builder
WORKDIR /app/frontend
COPY frontend/package.json frontend/package-lock.json ./
RUN npm install
COPY frontend/ ./
RUN npm run build

# Stage 2: Build the Spring Boot app
FROM maven:3.8.3-amazoncorretto-17 AS backend-builder
LABEL maintainer="office@omilab.org"
WORKDIR /app
COPY pom.xml ./
COPY src ./src
RUN mvn clean package -DskipTests -X

# Stage 3: Final image
FROM openjdk:17-jdk-alpine
LABEL maintainer="office@omilab.org"

# Update package repository and install required packages including Tesseract OCR
RUN apk update && apk add --no-cache tesseract-ocr tesseract-ocr-data-deu

# Verify Tesseract OCR version
RUN tesseract -v

# Copy the built frontend
COPY --from=frontend-builder /app/frontend/build /app/frontend/build

# Copy the built backend JAR file
COPY --from=backend-builder /app/target/*.jar /app/citizendev-spring-1.0.0.jar

# Copy the tessdata directory from the source
COPY src/main/resources/tessdata /usr/share/tesseract-ocr/4.00/tessdata

# Set the TESSDATA_PREFIX environment variable to /usr/share/tesseract-ocr/4.00/tessdata/
ENV TESSDATA_PREFIX=/usr/share/tesseract-ocr/4.00/tessdata/
ENV TESSERACT_LANG=deu

# Verify tessdata files
RUN ls -l /usr/share/tesseract-ocr/4.00/tessdata/

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/citizendev-spring-1.0.0.jar"]
