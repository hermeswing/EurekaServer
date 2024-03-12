# 유레카 서버(Erureka Server)

## 개발환경
- JDK : Zulu JDK 17.0.10
- SpringBoot : 3.2.3 
  - `implementation 'org.springframework.cloud:spring-cloud-starter-netflix-eureka-server`
  - dependency에 포함되어 있다.
- SpringBoot Eureka Server : 현재 최신버전 4.1.0
- build tools : gradle

## Source
- 유레카 서버(Erureka Server)의 개발은 간단하다.
- 기본적으로 3개의 파일만 필요하다.
  - `build.gradle`, `EurekaServerApplication.java`,`application.yml`

#### build.gradle
```gradle
plugins {
  id 'java'
  id 'org.springframework.boot' version '3.2.3'
  id 'io.spring.dependency-management' version '1.1.4'
}

group = 'octopus'
version = '0.0.1-SNAPSHOT'

java {
  sourceCompatibility = '17'
}

repositories {
  mavenCentral()
}

  ext {
  set('springCloudVersion', "2023.0.0")
}

dependencies {
  implementation 'org.springframework.cloud:spring-cloud-starter-netflix-eureka-server'
  testImplementation 'org.springframework.boot:spring-boot-starter-test'
}

dependencyManagement {
  imports {
    mavenBom "org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}"
  }
}

tasks.named('test') {
  useJUnitPlatform()
}
```
#### EurekaServerApplication.java
```java
@SpringBootApplication
@EnableEurekaServer               // 스프링 서비스에서 유레카 서버 활성화
public class EurekaServerApplication {

    public static void main(String[] args) {

        SpringApplication.run(EurekaServerApplication.class, args);
    }

}
```
#### application.yml
```yml
# 유레카 서버 Port
server:
port: 8761

spring:
application:
name: discovery-service

# eureka 설정
eureka:
client:
register-with-eureka: false     # eureka server에 자신을 등록하지 않음. registry에 등록할지 여부. false로 하지 않으면 자기 자신을 클라이언트로 등록함
fetch-registry: false           # 레지스트리 정보를 로컬에 캐싱하지 않음. registry에 있는 정보들을 가져올지 여부
```
## Build
#### 프로젝트 폴더에서 실행한다.
```text
$ ./gradlew bootJar
```

## 실행
#### 파일 찾기
```text
$ find . -name '*.jar'                                   
./gradle/wrapper/gradle-wrapper.jar
./build/libs/EurekaServer-0.0.1-SNAPSHOT.jar
```
#### jar 실행
- [프로젝트 디렉토리]/build/libs 폴더에 Build 됨. 
- `java -jar EurekaServer-0.0.1-SNAPSHOT.jar `
```text
# java -jar ./workspace/EurekaServer/build/libs/EurekaServer-0.0.1-SNAPSHOT.jar
# java -jar EurekaServer-0.0.1-SNAPSHOT.jar                               

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.3)
```
## Git Push
```git
git init
git add README.md
git commit -m "first commit"
git branch -M main
git remote add origin https://github.com/hermeswing/EureakServer.git
git push -u origin main
```

## 오류
```text
Execution failed for task ':compileJava'.
> Could not resolve all files for configuration ':compileClasspath'.
   > Could not resolve org.springframework.boot:spring-boot-starter:3.2.0.
     Required by:
         project : > org.springframework.cloud:spring-cloud-starter-netflix-eureka-server:4.1.0 > org.springframework.cloud:spring-cloud-starter:4.1.0
      > No matching variant of org.springframework.boot:spring-boot-starter:3.2.3 was found. The consumer was configured to find a library for use during compile-time, compatible with Java 11, preferably in the form of class files, preferably optimized for standard JVMs, and its dependencies declared externally but:
          - Variant 'apiElements' capability org.springframework.boot:spring-boot-starter:3.2.3 declares a library for use during compile-time, packaged as a jar, and its dependencies declared externally:
              - Incompatible because this component declares a component, compatible with Java 17 and the consumer needed a component, compatible with Java 11
              - Other compatible attribute:
                  - Doesn't say anything about its target Java environment (preferred optimized for standard JVMs)
          - Variant 'javadocElements' capability org.springframework.boot:spring-boot-starter:3.2.3 declares a component for use during runtime, and its dependencies declared externally:
              - Incompatible because this component declares documentation and the consumer needed a library
              - Other compatible attributes:
                  - Doesn't say anything about its target Java environment (preferred optimized for standard JVMs)
                  - Doesn't say anything about its target Java version (required compatibility with Java 11)
                  - Doesn't say anything about its elements (required them preferably in the form of class files)
```
- 기본적올 Springboot 3.x는 11버전 이상이어야 함.
- JVM Version을 올려서 해결하였음. -> Zulu JDK 17.0.10
#### 대부분 JVM Version 문제로 발생된 문제였음.
- 차후 JDK 11 버전으로 개발 해봐야 겠음. 아마도 SpringBoot 2.x 버전으로 다운그래드 할 듯...