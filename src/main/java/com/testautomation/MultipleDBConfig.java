package com.testautomation;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * @author sowmiya.r
 *
 */

@Configuration
public class MultipleDBConfig {

	// MySQL Database
	//@Primary //MySql
	@Bean(name = "mySqlDb")
	@ConfigurationProperties(prefix = "spring.datasourcemysql")
	public DataSource mySqlDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		// dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/test-automation");
		dataSource.setUsername("cfmavds");
		dataSource.setPassword("vdslite");

		return dataSource;
	}

	@Bean(name = "jdbcMySql")
	@Autowired
	public JdbcTemplate createMySqljdbcTemplate(@Qualifier("mySqlDb") DataSource dsMySql) {
		return new JdbcTemplate(dsMySql);
	}

	// SQL SERVER	
	@Primary //SQL Server
	@Bean(name = "dbSqlServer")
	@ConfigurationProperties(prefix = "spring.sqlserverdatasource")
	public DataSource createSqlServerDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		/*
		 * dataSource.setUrl("jdbc:sqlserver://127.0.0.1:1433;databaseName=springbootdb"
		 * ); dataSource.setUsername("sa"); dataSource.setPassword("tspl@123");
		 */
		dataSource.setUrl("jdbc:sqlserver://innovations.cij4hzpxsump.ap-south-1.rds.amazonaws.com:1433;databaseName=Testing_Automation");
		dataSource.setUsername("tsplsa");
		dataSource.setPassword("tspl123sa");
		return dataSource;
	}

	@Bean(name = "jdbcSqlServer")
	@Autowired
	public JdbcTemplate createSqlServerJdbcTemplate(@Qualifier("dbSqlServer") DataSource sqlserverDS) {
		return new JdbcTemplate(sqlserverDS);
	}

	// ORACLE 	
	@Bean(name = "oracleDb")
	@ConfigurationProperties(prefix = "spring.datasourceoracle")
	public DataSource oracleDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		/*
		 * dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:orcl");
		 * dataSource.setUsername("cfmavds"); dataSource.setPassword("vdslite");
		 */
		return dataSource;
	}

	@Bean(name = "jdbcOracleDb")
	@Autowired
	public JdbcTemplate createoraclejdbcTemplate(@Qualifier("oracleDb") DataSource dsOracle) {
		return new JdbcTemplate(dsOracle);
	}

}
