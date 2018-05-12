package com.sjw.adaptor.datasource;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@ConfigurationProperties(prefix = "low.datasource")
@MapperScan(basePackages = { LowDataSourceConfig.PACKAGE }, sqlSessionFactoryRef = "lowSqlSessionFactory")
public class LowDataSourceConfig {

	public static final String PACKAGE = "com.sjw.adaptor.dao.low";
	public static final String MAPPER_LOCATION = "classpath:mapper/low/*.xml";

	private String url;
	private String username;
	private String password;
	private String driverClass;

	@Bean(name = "lowDataSource", destroyMethod = "close")
	public DataSource highDataSource() {
		org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
		dataSource.setUrl(url);
		dataSource.setUsername(username);// 用户名
		dataSource.setPassword(password);// 密码
		dataSource.setDriverClassName(driverClass);
		dataSource.setInitialSize(10);// 初始化时建立物理连接的个数
		dataSource.setMaxIdle(200);// 最大连接池数量
		dataSource.setMaxActive(200);
		dataSource.setMinIdle(10);// 最小连接池数量
		dataSource.setMaxWait(60000);// 获取连接时最大等待时间，单位毫秒。
		dataSource.setValidationQuery("select sysdate from dual");// 用来检测连接是否有效的sql
		dataSource.setTestOnBorrow(true);// 申请连接时执行validationQuery检测连接是否有效
		dataSource.setTestWhileIdle(true);// 建议配置为true，不影响性能，并且保证安全性。
		return dataSource;
	}

	@Bean(name = "lowTransactionManager")
	public DataSourceTransactionManager highTransactionManager(@Qualifier("lowDataSource") DataSource highDataSource) {
		return new DataSourceTransactionManager(highDataSource);
	}

	@Bean(name="lowSqlSessionFactory")
	public SqlSessionFactory highSqlSessionFactory(
			@Qualifier("lowDataSource") DataSource highDataSource)
			throws Exception {
		final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(highDataSource);
		sessionFactory
				.setMapperLocations(new PathMatchingResourcePatternResolver()
						.getResources(LowDataSourceConfig.MAPPER_LOCATION));
		return sessionFactory.getObject();
	}

	@Bean(name="lowJdbcTemplate")
	public JdbcTemplate highJdbcTemplate(@Qualifier("lowDataSource") DataSource highDataSource){
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
