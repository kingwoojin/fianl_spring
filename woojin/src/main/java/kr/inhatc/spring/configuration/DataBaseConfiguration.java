package kr.inhatc.spring.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration //설정과 관련. 설정 파일로 쓸 수 있게 됨.
@PropertySource("classpath:/application.properties") //DB와 관련된 정보를 적어둔 파일의 위치 알려줌
public class DataBaseConfiguration {
	
	@Autowired
	private ApplicationContext appContext;
	
	//접속과 관련된 동작
	//hikari 설정관 관련된 메소드
	@Bean //메모리가 시작되는 순간 bean으로 된 애들은 메모리에 올라옴.
	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	public HikariConfig hikariConfig() {
		return new HikariConfig(); //Hikari를 설정할 수 있는 객체.
	}
	
	@Bean
	public DataSource dataSource() throws Exception {
		DataSource dataSource = new HikariDataSource(hikariConfig());
		System.out.println("=================>" + dataSource.toString());
		return dataSource;
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception{
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		
		//매퍼연결
		sqlSessionFactoryBean.setMapperLocations(
				appContext.getResources("classpath:/mapper/**/sql-*.xml")
				);
		sqlSessionFactoryBean.setConfiguration(myBatisConfig());
		
		return sqlSessionFactoryBean.getObject();
	}
	
	@Bean
	@ConfigurationProperties(prefix = "mybatis.configuration")
	public org.apache.ibatis.session.Configuration myBatisConfig(){
		return new org.apache.ibatis.session.Configuration();
	}
	
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	
	/**
	 * 
	 * <pre>
	 * 1. 개요 : JPA 설정
	 * 2. 처리내용 : JPA 설정 빈 등록
	 * </pre>
	 * @Method Name = hibernateConfig
	 * @return
	 * 
	 * @return
	 */
	
	@Bean
	@ConfigurationProperties(prefix = "spring.jpa")
	public Properties hibernateConfig() {
		return new Properties();
	}
	
}
