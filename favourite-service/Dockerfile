# Sử dụng một base image có Maven và OpenJDK 20 để build dự án
FROM openjdk:20-jdk-slim AS build

# Tạo thư mục /app trong container
WORKDIR /app

# Copy file JAR từ thư mục target vào container
COPY target/favourite-service-1.0-SNAPSHOT.jar /app/favourite-service.jar

# Sử dụng một base image nhẹ cho runtime
FROM openjdk:20-jdk-slim

# Tạo thư mục /app trong container
WORKDIR /app
ENV SPRING_PROFILES_ACTIVE dev
COPY --from=build /app/favourite-service.jar /app/favourite-service.jar

# Expose cổng mà ứng dụng chạy trên
EXPOSE 8085

CMD ["java", "-jar", "favourite-service.jar"]


#docker build -t favourite-service-image .