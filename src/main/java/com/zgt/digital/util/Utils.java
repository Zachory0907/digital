package com.zgt.digital.util;

import java.io.InputStream;
import java.util.Properties;

public class Utils {

	public static Properties getProperties(String cfgPath) throws Exception{
		Properties properties = new Properties();
		InputStream is = Utils.class.getResourceAsStream(cfgPath);
		try{
			properties.load(is);
			is.close();
			return properties;
		} catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}

}
