# WSO2 User Registration Notifier Event Handler

This repository contains a custom event handler for **WSO2 Identity Server (IS)** that sends an email notification to all administrators when a new user is onboarded.

## 📌 Features
- Subscribes to the **POST_ADD_USER** event.
- Retrieves user details such as **username** and **tenant domain**.
- Fetches email addresses of users with the **admin role**.
- Sends an email notification to all admins about the newly onboarded user.

## 📂 Project Structure
```
WSO2-UserReg-Notifier-Event-Handler/
│── src/
│   ├── main/
│   │   ├── java/org/wso2/custom/handler/
│   │   │   ├── UserRegistrtaionNotifierEventHandler.java
│   │   │   ├── UserRegistrtaionNotifierEventComponent.java
│   │   │   ├── UserUtils.java
│   │   │   ├── EmailSender.java
│   │   ├── resources/
│   │   │   ├── OSGI-INF/
│   │   │   │   ├── org.wso2.custom.handler.UserRegistrtaionNotifierEventComponent.xml
│── pom.xml
│── README.md
```

## ⚡ Getting Started

### 1️⃣ Clone the Repository
```sh
git clone https://github.com/aaujayasena/wso2-user-registration-notifier-handler.git
cd wso2-user-registration-notifier-handler
```

### 2️⃣ Configure Dependencies
Ensure that your `pom.xml` includes the necessary dependencies for WSO2 Identity Server and OSGi components.

```xml
<dependency>
    <groupId>org.wso2.carbon.identity.framework</groupId>
    <artifactId>org.wso2.carbon.identity.event</artifactId>
    <version>7.7.163</version>
</dependency>
```

### 3️⃣ Build the Project
```sh
mvn clean install
```
This generates a `.jar` file inside `target/`.

### 4️⃣ Deploy to WSO2 Identity Server
Copy the generated `.jar` file to the **dropins** directory:
```sh
cp target/wso2-user-registration-notifier-handler-1.0-SNAPSHOT.jar <WSO2_HOME>/repository/components/dropins/
```

### 5️⃣ Start WSO2 Identity Server with OSGi Console
```sh
sh <WSO2_HOME>/bin/wso2server.sh -DosgiConsole
```

### 6️⃣ Verify the Bundle in OSGi Console
Once the server starts, enter the OSGi console and check if the bundle is active:
```sh
osgi> ss | grep wso2-user-registration-notifier-handler
```

If it is **INSTALLED** but not **ACTIVE**, start it manually:
```sh
osgi> start <BUNDLE_ID>
```

## 📨 Email Configuration
The email sending mechanism is implemented using **JavaMail API**. Make sure to configure SMTP details in WSO2's deployment configuration.

### SMTP Configuration Example
```properties
[output_adapter.email]
from_address= "email@gmail.com"
username= "email@gmail.com"
password= "yourpassword"
hostname= "smtp.gmail.com"
port= 587
enable_start_tls= true
enable_authentication= true
signature = "gmail.com"
```
## 🧪 How to Test

### 1️⃣ Create a New User via SCIM API
```sh
curl -X POST -H "Authorization: Bearer <TOKEN>" -H "Content-Type: application/json" -d '{
  "schemas": ["urn:ietf:params:scim:schemas:core:2.0:User"],
  "userName": "testuser",
  "password": "P@ssw0rd",
  "emails": [{"value": "testuser@example.com", "primary": true}]
}' "https://localhost:9443/scim2/Users"
```

### 2️⃣ Check Server Logs for Email Sending Confirmation
```sh
tail -f <WSO2_HOME>/repository/logs/wso2carbon.log
```

## 🛠 Future Improvements
- Integrate with WSO2 email templates for structured email notifications.
- Allow configurable admin roles instead of hardcoded "admin".

## 📜 License
This project is licensed under the [MIT License](LICENSE).
