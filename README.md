# Spring Boot JWT Role-Based Authentication API

Bu proje, **Spring Boot 3** ve **Spring Security 6** kullanarak JWT (JSON Web Token) tabanlı kimlik doğrulama ve rol tabanlı yetkilendirme (RBAC) işlemlerini örnekler. Kullanıcılar kayıt olabilir, giriş yapabilir ve sahip oldukları role göre farklı API uç noktalarına erişebilir. Veritabanı olarak **MySQL** kullanılır ve kullanıcı şifreleri **BCrypt** ile güvenli şekilde saklanır.

---

## 📌 Özellikler

- ✅ JWT tabanlı stateless kimlik doğrulama
- ✅ Kullanıcı kayıt & giriş işlemleri
- ✅ BCrypt ile güvenli parola saklama
- ✅ Rol bazlı erişim yetkilendirme: **USER**, **MODERATOR**, **ADMIN**
- ✅ Spring Security filtre zinciri ve custom JWT filtresi
- ✅ Açık, temiz proje yapısı — kolay geliştirme

---

## ⚙️ Kullanılan Teknolojiler

- Java 17+
- Spring Boot 3.x
- Spring Security 6
- JJWT (Java JWT library)
- MySQL
- Maven

---

## 🗂️ Proje Yapısı

- **config/** → Spring Security ayarları
- **jwt/** → JWT filter ve şifreleme
- **controller/** → REST API uç noktaları: Auth, User, Moderator, Admin
- **model/** → Entity sınıfları: User, Role
- **repository/** → Spring Data JPA arayüzleri
- **service/** → JWT servisleri, kullanıcı işlemleri, UserDetailsService implementasyonu

---

## 🔑 Örnek Uç Noktalar

| Endpoint                  | Metot | Yetki               |
|---------------------------|-------|---------------------|
| `/api/auth/register`      | POST  | Herkese açık        |
| `/api/auth/login`         | POST  | Herkese açık        |
| `/api/user/`              | GET   | USER, MODERATOR, ADMIN |
| `/api/moderator/`         | GET   | MODERATOR, ADMIN    |
| `/api/admin/`             | GET   | ADMIN               |

Kullanıcı giriş yaptıktan sonra JWT token alır ve bu token'ı `Authorization` header'ında **`Bearer <token>`** şeklinde göndermelidir.

---

## ⚡ Çalışma Mantığı

1. Kullanıcı `/api/auth/register` üzerinden kayıt olur. Parola BCrypt ile hashlenir.
2. `/api/auth/login` ile giriş yapar, geçerli bilgilerle birlikte backend JWT üretir.
3. Her istek `JwtAuthFilter` tarafından kontrol edilir; JWT geçerli değilse istek reddedilir.
4. Rol bilgisi token içinde taşınır, erişim yetkisi Spring Security yapılandırması ile kontrol edilir.

---

## ⚙️ Hızlı Başlangıç

1️⃣ Projeyi klonla:  

2️⃣ `application.properties` dosyasında veritabanı ve JWT secret ayarlarını yap:  
- `spring.datasource.url=jdbc:mysql://localhost:3306/<db_adi>?useSSL=false&serverTimezone=UTC`
- `spring.datasource.username=<kullanici>`
- `spring.datasource.password=<sifre>`
- `spring.jpa.hibernate.ddl-auto=update`
- `spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver`
