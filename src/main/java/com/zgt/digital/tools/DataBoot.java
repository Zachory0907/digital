package com.zgt.digital.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.DbPro;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.zgt.digital.db.ACDBCPPlugin;
import com.zgt.digital.util.Utils;

@SuppressWarnings("unused")
public class DataBoot {

	private static ACDBCPPlugin buildAcdbcpFromProperties(String cfgPath) throws Exception {
		Properties properties = Utils.getProperties(cfgPath);
		return new ACDBCPPlugin(properties);
	}

	private static List<String> getSQLPaths(String srcDir, List<String> list) {
		if (list == null)
			list = new ArrayList<String>();
		File dir = new File(srcDir);
		if (dir.isDirectory()) {
			File[] files = dir.listFiles();
			for (File f : files) {
				if (f.isFile() && f.getName().toLowerCase().endsWith(".sql"))
					list.add(f.getAbsolutePath());
				else if (f.isDirectory()) {
					getSQLPaths(srcDir + File.separator, list);
				}
			}
		}
		return list;
	}

	private static String getTrimSQL(String filePath) throws IOException {
		File file = new File(filePath);
		FileInputStream fs = new FileInputStream(file);
		FileChannel channel = fs.getChannel();
		MappedByteBuffer mb = channel.map(MapMode.READ_ONLY, 0, file.length());
		Charset ascii = Charset.forName("GBK");
		CharBuffer cb = ascii.decode(mb);
		Pattern p = Pattern.compile("/\\*.*?\\*/|--.*?$", Pattern.DOTALL | Pattern.MULTILINE);
		fs.close();
		return p.matcher(cb).replaceAll("");
	}

	public static void main(String[] args) {
//		if (args.length < 1) {
//			System.out.println("Usage:……");
//			return;
//		}
		File path = new File(DataBoot.class.getResource("/").getFile());
		path = new File(path.getParent());
		System.setProperty("webroot", path.getAbsolutePath());
		System.out.println(System.getProperty("webroot"));// F:\All_WorkSpace\Eclipse_mars\digital\target
//		String sqlDirs = args[0];
//		System.out.println("SQL files directory: " + sqlDirs);
		ACDBCPPlugin acdb = null;
		ActiveRecordPlugin db_name = null;
		final List<String> fireErrors = new ArrayList<String>();
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
			List<Record> list = DbPro.use().find("select * from wocao");
			System.out.println(list.size());
			/*
			final List<String> sqlFiles = getSQLPaths(sqlDirs, null);
			final DbPro mysqlPro = new DbPro();
			mysqlPro.tx(new IAtom() {

				public boolean run() throws SQLException {
					for (String sqlFile : sqlFiles) {
						System.out.println("Processing " + sqlFile);
						String sql = "";
						try {
							sql = getTrimSQL(sqlFile);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						if (sql.equals(""))
							return true;
						String[] statements = sql.split(";");
						for (String st : statements) {
							if (st.trim().equals(""))
								continue;
							try {
								mysqlPro.update(st);
							} catch (Exception e) {
								String msg = e.getMessage();
								if (msg.indexOf("MySql?") == -1 && msg.indexOf("MySql?") == -1) {
									e.printStackTrace();
									fireErrors.add(st);
								}
							}
						}
					}
					return false;
				}

			});
			*/
			if (fireErrors.isEmpty()) {
				System.out.println("Database initialized");
			} else
				System.out.println("Database initialize opr failed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db_name != null)
				db_name.stop();
			if (acdb != null)
				acdb.stop();
		}
	}

}
