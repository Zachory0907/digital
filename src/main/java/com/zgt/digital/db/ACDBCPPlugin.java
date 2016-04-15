package com.zgt.digital.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.IPlugin;
import com.jfinal.plugin.activerecord.IDataSourceProvider;

public class ACDBCPPlugin implements IPlugin, IDataSourceProvider{
	
	/**
	 * 数据库连接字符串
	 */
	private String url;
	/**
	 * 用户名
	 */
	private String user;
	/**
	 * 用户密码
	 */
	private String password;
	/**
	 * 数据库连接驱动名
	 */
	private String driverClass = "com.mysql.jdbc.Driver";
	/**
	 * 初始连接池连接个数
	 */
	private int initialSize = 100;
	/**
	 * 最大激活连接数
	 */
	private int maxActive = 10;
	/**
	 * 最大闲置连接数
	 */
	private int maxIdle = 10;
	/**
	 * 最小闲置连接数
	 */
	private int minIdle = 2;
	/**
	 * 获得连接的最大等待毫秒数
	 */
	private int maxWait = 1000;

	/**
	 * 数据源
	 */
	private BasicDataSource dataSource;
	
	public ACDBCPPlugin setDriverClass(String driverClass){
		if(StrKit.isBlank(driverClass))
			throw new IllegalArgumentException("DriverClass can not be blank");
		this.driverClass = driverClass;
		return this;
	}
	
	public ACDBCPPlugin setMaxPoolSize(int initialSize) {
		if (initialSize < 1)
			throw new IllegalArgumentException("initialSize must more than 0.");
		this.initialSize = initialSize;
		return this;
	}

	public ACDBCPPlugin setMinPoolSize(int maxActive) {
		if (maxActive < 1)
			throw new IllegalArgumentException("maxActive must more than 0.");
		this.maxActive = maxActive;
		return this;
	}

	public ACDBCPPlugin setInitialPoolSize(int maxIdle) {
		if (maxIdle < 1)
			throw new IllegalArgumentException("maxIdle must more than 0.");
		this.maxIdle = maxIdle;
		return this;
	}

	public ACDBCPPlugin setMaxIdleTime(int minIdle) {
		if (minIdle < 1)
			throw new IllegalArgumentException("minIdle must more than 0.");
		this.minIdle = minIdle;
		return this;
	}

	public ACDBCPPlugin setAcquireIncrement(int maxWait) {
		if (maxWait < 1)
			throw new IllegalArgumentException("maxWait must more than 0.");
		this.maxWait = maxWait;
		return this;
	}
	
	public ACDBCPPlugin(String Url, String user, String password) {
		this.url = Url;
		this.user = user;
		this.password = password;
	}
	
	public ACDBCPPlugin(String Url, String user, String password,
			String driverClass) {
		this.url = Url;
		this.user = user;
		this.password = password;
		this.driverClass = driverClass != null ? driverClass : this.driverClass;
	}
	
	public ACDBCPPlugin(String jdbcUrl, String user, String password,
			String driverClass, Integer initialSize, Integer maxActive,
			Integer maxIdle, Integer minIdle, Integer maxWait) {
		initTomcatProperties(jdbcUrl, user, password, driverClass, initialSize, maxActive, maxIdle, minIdle, maxWait);
	}

	private void initTomcatProperties(String jdbcUrl, String user,
			String password, String driverClass, Integer initialSize,
			Integer maxActive, Integer maxIdle, Integer minIdle, Integer maxWait) {
		this.url = jdbcUrl;
		this.user = user;
		this.password = password;
		this.driverClass = driverClass != null ? driverClass : this.driverClass;
		this.initialSize = initialSize != null ? initialSize : this.initialSize;
		this.maxActive = maxActive != null ? maxActive : this.maxActive;
		this.maxIdle = maxIdle != null ? maxIdle : this.maxIdle;
		this.minIdle = minIdle != null ? minIdle : this.minIdle;
		this.maxWait = maxWait != null ? maxWait : this.maxWait;
	}
	
	public ACDBCPPlugin(File propertyfile){
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(propertyfile);
			Properties ps = new Properties();
			ps.load(fis);
			
			initTomcatProperties(
				ps.getProperty("jdbcUrl"),
				ps.getProperty("user"), ps.getProperty("password"),
				ps.getProperty("driverClass"),
				toInt(ps.getProperty("initialSize")),
				toInt(ps.getProperty("maxActive")),
				toInt(ps.getProperty("maxIdle")),
				toInt(ps.getProperty("minIdle")),
				toInt(ps.getProperty("maxWait"))
			);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null)
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	public ACDBCPPlugin(Properties properties){
		Properties ps = properties;
		initTomcatProperties(
			ps.getProperty("jdbcUrl"), ps.getProperty("user"),
			ps.getProperty("password"), ps.getProperty("driverClass"),
			toInt(ps.getProperty("initialSize")),
			toInt(ps.getProperty("maxActive")),
			toInt(ps.getProperty("maxIdle")),
			toInt(ps.getProperty("minIdle")),
			toInt(ps.getProperty("maxWait"))
		);
	}

	private Integer toInt(String str) {
		return Integer.parseInt(str);
	}
	
	public BasicDataSource getDataSource() {
		return dataSource;
	}

	public boolean start() {
		dataSource = new BasicDataSource();
		dataSource.setUrl(url);
		dataSource.setUsername(user);
		dataSource.setPassword(password);
		dataSource.setDriverClassName(driverClass);
		dataSource.setInitialSize(initialSize);
		dataSource.setMaxTotal(maxActive);
		dataSource.setMaxIdle(maxIdle);
		dataSource.setMinIdle(minIdle);
		dataSource.setMaxWaitMillis(maxWait);
		return true;
	}

	public boolean stop() {
		if (dataSource != null)
			try {
				dataSource.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return true;
	}

}
