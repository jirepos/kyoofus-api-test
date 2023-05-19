package com.jirepo;


import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
// import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

/** Spring Event를 비동기적으로 처리하기 위한 어노테이션 적용 */
@EnableAsync 
@SpringBootApplication
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
// war 파일로 빌드하는 경우 예전에는 configure 메소드를 오버라이드가 필요했던 것으로 보이지만, 현재 버전은 해당 메소드를 오버라이드하지 않아도 동작합니다.
// 
public class JirepoApplication   { // extends SpringBootServletInitializer {

 
	// SpringBoot 웹 애플리케이션을 배포할 때는 주로 embedded tomcat이 내장된 jar파일을 이용한다. 
	// 하지만 특별한 경우에는 전통적인 배포 방식인 war 파일로 배포를 진행해야 하는 경우가 있을 수 있다.
	// 이럴 경우 SpringBootServletInitializer를 상속받으면 된다. 
	// 즉, war 파일로 빌드하고 배포하지 않을 거라면 SpringBootServletInitializer를 상속할 필요가 없다는 의미다.
	// Spring 웹 애플리케이션을 외부 Tomcat에서 동작하도록 하기 위해서는 web.xml (Deployment Descriptor, DD)에 애플리케이션 컨텍스트를 등록해야만 한다.
	// 이는, Apache Tomcat(Servlet Container)이 구동될 때 /WEB-INF 디렉토리에 존재하는 web.xml을 읽어 웹 애플리케이션을 구성하기 때문이다. 
	// 하지만 Servlet 3.0 스펙으로 업데이트되면서 web.xml이 없어도 동작이 가능해졌다
	// 이는, web.xml 설정을 WebApplicationInitializer인터페이스를 구현하여 대신할 수 있게 됐고
	// 프로그래밍적으로 ServletContext에 Spring IoC 컨테이너(AnnotationConfigWebApplicationContext)를 생성하여 추가할 수 있도록 변경됐기 때문이다.
	// 이와 비슷한 맥락에서, web.xml이 없는 SpringBoot 웹 애플리케이션을 외부 Tomcat에서 동작하도록 하기 위해서는 
	// WebApplicationInitializer 인터페이스를 구현한 SpringBootServletInitializer를 상속을 받는 것이 필요했던 것이다.
	//  web.xml 역할을 WebApplicationInitializer 인터페이스를 구현하여 프로그래밍적으로 ServletContext에 Spring IoC 
	// 컨테이너(AnnotationConfigWebApplicationContext)를 생성하여 추가할 수 있다.
	// SpringBootServletInitializer 상속 한다는 결국 Tomcat 같은 Servlet Container 환경에서 Spring Boot 애플리케이션 
	// 동작 가능 하도록 웹 애플리케이션 컨텍스트 구성 한다는 의미 이다.

	// https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/web/servlet/support/SpringBootServletInitializer.html
	// Springboot 2.74 에서 SpringBootServletInitializer가 존재함 
	// Note that a WebApplicationInitializer is only needed if you are building a war file and deploying it. 
	// war 파일로 빌드하고 디플로이 할 경우에만 필요하다.
	// SpringBootServletInitializer와  WebApplicationInitializers를 같이 사용하는 경우  @Ordered를 사용하여 
	// 시작 순서를 조정할 필요가 있을 수 있다. 


    public static void main(String[] args) {
		SpringApplication.run(JirepoApplication.class, args);
	}

	// // 배포 시 동작하지 않는 경우 해당 주석을 풀고 다시 빌드
    // @Override
    // protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    //     return builder.sources(JirepoApplication.class);
    // }
    
}
