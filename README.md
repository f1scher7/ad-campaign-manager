# Ad Campaign Manager

Link for live app: http://ec2-35-159-110-112.eu-central-1.compute.amazonaws.com

The Ad Campaign Manager was created using Spring Boot and Angular. It is a CRUD application that allows administrators to manage marketing campaigns and users.

![AdCampaignManager](https://github.com/f1scher7/ad-campaign-manager/assets/115926717/be7de1e5-fc17-4952-9026-4a43fe8cadd5)

## Technologies

### Front-End
- Angular 18
- TypeScript
- HTML5
- CSS3
- Bootstrap5
- Font Awesome

### Back-End
- Java 21
- Spring Boot 3.3.1
- Spring Web MVC 
- Spring Security 6.3.1
- JWT 0.12.6
- Maven
- Docker Image
- nginx
- AWS EC2
- H2 (data base)
- Spring Data JPA
- Hibernate
- Unit tests
  
## Local testing

- The backend will start on `http://localhost:8080`
- The frontend will be available on `http://localhost:4200`

### Admin Login Credentials

- **Username:** admin
- **Password:** Admin123

### Database Access

- **H2 Database Console:**
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:file:~/campaign_manager`
- Username: sa
- Password: password

Make sure both the backend and frontend are running simultaneously for full functionality of the application. Access the H2 Database Console to manage database contents as needed.

![H2DataBase](https://github.com/f1scher7/ad-campaign-manager/assets/115926717/c6794c63-cf80-4993-ad9f-176b93dcea27)

## Author

Maks Szyło maksymilian.fischer7@gmail.com

