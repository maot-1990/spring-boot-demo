package com.sjw.adaptor.datasource;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@ConfigurationProperties(prefix = "high.datasource")
@MapperScan(basePackages = { HighDataSourceConfig.PACKAGE }, sqlSessionFactoryRef = "highSqlSessionFactory")
public class HighDataSourceConfig {

	public static final String PACKAGE = "com.sjw.adaptor.dao.high";
	public static final String MAPPER_LOCATION = "classpath:mapper/high/*.xml";

	private String url;
	private String username;
	private String password;
	private String driverClass;

	@Bean(name = "highDataSource", destroyMethod = "close")
	@Primary
	public DataSource highDataSource() {
		org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
		dataSource.setUrl(url);
		dataSource.setUsername(username);// 用户名
		dataSource.setPassword(password);// 密码
		dataSource.setDriverClassName(driverClass);
		dataSource.setInitialSize(20);// 初始化时建立物理连接的个数
		dataSource.setMaxIdle(250);// 最大连接池数量
		dataSource.setMaxActive(250);
		dataSource.setMinIdle(20);// 最小连接池数量
		dataSource.setMaxWait(60000);// 获取连接时最大等待时间，单位毫秒。
		dataSource.setValidationQuery("select sysdate from dual");// 用来检测连接是否有效的sql
		dataSource.setTestOnBorrow(true);// 申请连接时执行validationQuery检测连接是否有效
		dataSource.setTestWhileIdle(true);// 建议配置为true，不影响性能，并且保证安全性。
		return dataSource;
	}

	@Bean(name = "highTransactionManager")
	@Primary
	public DataSourceTransactionManager highTransactionManager(@Qualifier("highDataSource") DataSource highDataSource) {
		return new DataSourceTransactionManager(highDataSource);
	}

	@Bean(name="highSqlSessionFactory")
	@Primary
	public SqlSessionFactory highSqlSessionFactory(
			@Qualifier("highDataSource") DataSource highDataSource)
			throws Exception {
		final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(highDataSource);
		sessionFactory
				.setMapperLocations(new PathMatchingResourcePatternResolver()
						.getResources(HighDataSourceConfig.MAPPER_LOCATION));
		return sessionFactory.getObject();
	}

	@Bean(name="highJdbcTemplate")
	@Primary
	public JdbcTemplate highJdbcTemplate(@Qualifier("highDataSource") DataSource highDataSource){
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(highDataSource);
		return jdbcTemplate;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

}
