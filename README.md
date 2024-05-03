# Food-Order-Microservices
Đồ án cuối môn học Hướng Dịch Vụ Thầy Trụ
- Xử lý cơ bản phần BE, có API Gateway, discovery server eureka
- SignIn, SignUp có security+jwt
- Xử lý thêm module notification-service, khi người dùng thanh toán thành công( tức payment được tạo) thì sẽ gọi tới module notification-service và gửi email thông tin thanh toán.

- Chưa có FE, kubernestes, mongDB, chỉ kết nối duy nhất với mysql

- Xử lý phần giao tiếp giữa order-service với user-service và product-service.
- Xử lý thêm phần giao tiếp giữa favourite-service với user-service và product-service.

- Đang hoàn thiện phần frontend, (user-service,)
