package com.shinhan.sbproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/*
 * 기본패키지 하위는 추가하는 코드가 없다 : com.shinhan.sbproject
 * 그 외의 폴더라면 @ComponentScan 추가한다. 
 * 
 * JpaRepositories가 기본 패키지 하위에 존재하지 않는 경우 추가함 : @EnableJpaRepositories
 * @EnableJpaRepositories를 사용하면 Repository가 있는 폴더는 모두 등록해야 한다.
 */


@ComponentScan(basePackages = {"com.shinhan.sbproject","com.shinhan.firstzone","com.shinhan.firstzone2"})
@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.shinhan.firstzone", "com.shinhan.sbproject"})
public class SbprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbprojectApplication.class, args);
	}
}
