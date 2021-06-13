package kr.inhatc.spring.configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
//어댑터클래스 여러개의 인터페이스중 하나를 쓰기위해? 공부 다시~ A는 추상클래스래요 ㅋㅋㅋㅋ
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(HttpSecurity security) throws Exception {
		//루트는 누구든 들어오게
		security.authorizeRequests().antMatchers("/","/login/**").permitAll(); //String 다음에 ...있는건 축약 표시로 인자가 여러개일수도 있다라는 의미
		//인증 받은 사용자만 접근 가능<유저 밑에 있는 모든 놈들>
		security.authorizeRequests().antMatchers("/boardjpa/**").hasRole("ADMIN");
		security.authorizeRequests().antMatchers("/user/**").hasAnyRole("ADMIN");
		
		//레스트풀방식 쓰랴먄 있어야함 (변조방지)
		security.csrf().disable();
		//기본제공
//		security.formLogin();
		//로그인에 성공을 하면 루트로 이동
		security.formLogin().loginPage("/login/login").defaultSuccessUrl("/", true);
		//로그인 실패시
		security.exceptionHandling().accessDeniedPage("/login/accessDenied");
		security.logout().logoutUrl("/login/logout").invalidateHttpSession(true).logoutSuccessUrl("/");
	}
	
}
