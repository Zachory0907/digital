package com.zgt.digital.biz;

import com.jfinal.plugin.activerecord.DbPro;
import com.jfinal.plugin.activerecord.Record;

public class Register {

	public static Record testMysql(){
		Record list = DbPro.use().findFirst("select * from wocao");
		return list;
	}

	public static int regist(String nICKNAME, String pASSWORD, String tEL, String eMAIL, String hEAD, String cHECK) {
		int i =  DbPro.use().update("insert into digital_user values (null, '" + nICKNAME + "', '" + pASSWORD + "', '" + tEL + "', '" + eMAIL + "', '" + hEAD + "', 0, '" + cHECK + "')");
		System.out.println(i);
		return i;
	}

	public static boolean mailCheck(String check) {
		try {
			DbPro.use().update("update digital_user set YZ=1 where CHECKED='"+check+"'");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
