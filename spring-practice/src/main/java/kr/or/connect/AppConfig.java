package kr.or.connect;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@ComponentScan	//@Repository 가 붙은 클래스를 찾아 DataSource를 DI하고 해당 클래스를 context에 등록한다. *****
@PropertySource("application.properties")
public class AppConfig {
	@Value("${spring.datasource.url}")
	private String url;
	
	@Value("${spring.datasource.driver-class-name}")
	private String drivername;
	
	@Value("${spring.datasource.username}")
	private String username;
	
	@Value("${spring.datasource.password}")
	private String password;
	
	
	@Bean	//context에 등록될 객체(DataSource)를 반환하는 곳에 붙힌다.
	public DataSource datasource(){
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(drivername);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(){
		return new DataSourceTransactionManager(datasource());
	}
	
}
