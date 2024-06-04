# Sử dụng base image chứa OpenJDK và môi trường chạy Java
FROM adoptopenjdk/openjdk11:alpine

# Thiết lập thư mục làm việc trong container
WORKDIR /app

# Copy file .jar được build từ mã nguồn Java vào thư mục /app trong container
COPY target/order-service-1.0-SNAPSHOT.jar /app/order-service.jar

# Expose cổng mà ứng dụng của bạn chạy
EXPOSE 8083

# Command để chạy ứng dụng khi container được khởi chạy
CMD ["java", "-jar", "order-service.jar"]
