# Sử dụng một base image có Maven và OpenJDK 20 để build dự án
FROM openjdk:20-jdk-slim AS build

# Tạo thư mục /app trong container
WORKDIR /app

# Copy file JAR từ thư mục target vào container
COPY target/api-gateway-1.0-SNAPSHOT.jar /app/api-gateway.jar

# Sử dụng một base image nhẹ cho runtime
FROM openjdk:20-jdk-slim

# Tạo thư mục /app trong container
WORKDIR /app
ENV SPRING_PROFILES_ACTIVE dev
COPY --from=build /app/api-gateway.jar /app/api-gateway.jar

# Expose cổng mà ứng dụng chạy trên
EXPOSE 9000

CMD ["java", "-jar", "api-gateway.jar"]

#docker build -t api-gateway-image .
#docker-compose up

