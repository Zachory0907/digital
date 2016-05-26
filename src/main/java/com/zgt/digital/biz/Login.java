package com.zgt.digital.biz;

import com.jfinal.plugin.activerecord.DbPro;
import com.jfinal.plugin.activerecord.Record;

public class Login {

	public static Record testMysql(){
		Record list = DbPro.use().findFirst("select * from wocao");
		return list;
	}

	public static Record loginValidate(String logname, String logpass) {
		Record rec = DbPro.use().findFirst("SELECT * FROM digital_user WHERE NICKNAME = '" + logname + "' AND PASSWORD='" + logpass +"'");
		if(rec == null){
			return null;
		}
		return rec;
	}

	public static Record getUsr(int userId) {
		return DbPro.use().findFirst("select * from digital_user where u_id = " + userId);
	}
}
