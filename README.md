Yêu cầu trước khi chạy: 
cài JDK 22 và chọn làm Java đang dùng,
cài SQL Server,
Mở terminal tại thư mục gốc dự án.

Sử dụng database ở Cinema.csv

Chạy server trong terminal bằng lệnh: mvnw.cmd spring-boot:run (./mvnw spring-boot:run nếu chạy trên Mac OS và Linux)

Mở trình duyệt vào http://localhost:8080/

Tạo tài khoản guest bằng chức năng Register của web

Để tạo tài khoản admin thì nhập vào terminal: 
curl -i -X POST "http://localhost:8080/user/register" \
  -H "Content-Type: application/json" \
  -d "{\"email\":\"Email của bạn\",\"password\":\"mật khẩu của bạn\",\"fullName\":\"Tên\",\"dateOfBirth\":\"ngày sinh\",\"tel\":\"sđt\",\"role\":\"ADMIN\"}"
