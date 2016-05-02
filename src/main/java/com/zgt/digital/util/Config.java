package com.zgt.digital.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.ext.route.AutoBindRoutes;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.render.ViewType;
import com.zgt.digital.db.ACDBCPPlugin;

/**
 * 初始化jfinal web.xml加载时会调用这个配置文件里面的参数
 * 
 * @author ZGT
 *
 */
public class Config extends JFinalConfig {

	@Override
	public void configConstant(Constants me) {
		me.setEncoding(Consts.DEFAULT_ENCODING);
		/*me.setViewType(ViewType.JSP);
		me.setJspViewExtension(".html");*/
		me.setViewType(ViewType.VELOCITY);
		me.setVelocityViewExtension("html");
		me.setEncoding(Consts.DEFAULT_ENCODING);
		me.setError500View("/error.html");
		me.setError401View("/40x.html");
		me.setErrorView(Consts.ERROR_AUTHENTIC_CODE, "/autherror.html");
		me.setError404View("/40x.html");
	}

	@Override
	public void configHandler(Handlers me) {

	}

	@Override
	public void configInterceptor(Interceptors me) {

	}

	private static ACDBCPPlugin buildAcdbcpFromProperties(String cfgPath) throws Exception {
		Properties properties = Utils.getProperties(cfgPath);
		return new ACDBCPPlugin(properties);
	}

	@Override
	public void configPlugin(Plugins me) {
		ACDBCPPlugin acdb = null;
		ActiveRecordPlugin db_name = null;
		try {
			acdb = buildAcdbcpFromProperties("/db.properties");
			if (acdb == null) {
				throw new SQLException("db.properties connection information is invalid [apache dbcp]");
			}
			acdb.start();
			db_name = new ActiveRecordPlugin(acdb);
			db_name.setDevMode(true);
			db_name.setDialect(new MysqlDialect());
			db_name.setTransactionLevel(Connection.TRANSACTION_SERIALIZABLE);// ???
			db_name.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void configRoute(Routes me) {
		// 自动扫描controller
		me.add(new AutoBindRoutes());
		System.out.println("System is started");
	}

}
