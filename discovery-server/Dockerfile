# Sử dụng một base image có Maven và OpenJDK 20 để build dự án
FROM openjdk:20-jdk-slim AS build

# Tạo thư mục /app trong container
WORKDIR /app

# Copy file JAR từ thư mục target vào container
COPY target/discovery-server-1.0-SNAPSHOT.jar /app/discovery-server.jar

# Sử dụng một base image nhẹ cho runtime
FROM openjdk:20-jdk-slim

# Tạo thư mục /app trong container
WORKDIR /app
ENV SPRING_PROFILES_ACTIVE dev
COPY --from=build /app/discovery-server.jar /app/discovery-server.jar

# Expose cổng mà ứng dụng chạy trên
EXPOSE 8761

CMD ["java", "-jar", "discovery-server.jar"]

#docker build -t discovery-server-image .