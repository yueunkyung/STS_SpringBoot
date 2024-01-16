package com.shinhan.sbproject.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

// log를 남기는 어드바이스

//보조업무
//Aspect : Pointcut 과 Advise를 합쳐 놓은 코드(보조업무)
//Advice : 조인 포인트에 삽입되어 동작할 수 있는 공통 관심 사항의 코드
//		(aspect 실제 구현체)
//		*동작시점 : 조인포인트 실행 전, 후로 before, after, after returning, after throwing, around로 구분 

@Component	// project4.xml => <context:component-scan base-package="com.shinhan" />에 의해 스캔됨
@Aspect	// project4.xml => <aop:aspectj-autoproxy />에 의해 스캔됨
@Order(2)
public class LoggingAdvice {
//	@Pointcut("execution( * select*(..))")
	@Pointcut("within(com.shinhan.sbproject.webboard.WebBoardController)")
	public void aa() {}
		//메서드 이름은 상관없음, 메서드 형태를 만들고 @Pointcut 작성
	
	@AfterReturning("aa()")
	public void f3(JoinPoint jp) {
		System.out.println("@AfterReturning===>>"+jp.getSignature().getName());
	}
	
	@AfterThrowing("aa()")
	public void f4(JoinPoint jp) {
		System.out.println("@@AfterThrowing===>>"+jp.getSignature().getName());
	}

	@After("aa()")
	public void f2(JoinPoint jp) {
		System.out.println("@After===>>"+jp.getSignature().getName());
	}
	
	@Before("aa()")
	public void f1(JoinPoint jp) {
		System.out.println("before===>>"+jp.getSignature().getName());
	}
	
	@Around("aa()")
	public Object arroundMethod2(ProceedingJoinPoint jp) throws Throwable {
		//보조업무
		//1.around
		//2.before
		System.out.println("[arround]=============LoggingAdvice보조업무==============");
		System.out.println(jp.getSignature().getName()+"[arround]!!!!!!!!메서드 호출전!!!!!!!!");
		//3.주업무
		Object obj = jp.proceed();
		//보조업무
		//4.AfterReturning
		//5.after
		//6.around
		System.out.println(jp.getSignature().getName()+"[arround]!!!!!!!!메서드 호출후!!!!!!!!");
		System.out.println("[arround]******************************");
		
		return obj;
	}
	
}
