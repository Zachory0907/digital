package com.zgt.digital.biz;

import com.jfinal.plugin.activerecord.DbPro;
import com.jfinal.plugin.activerecord.Record;

public class Test {

	public static Record testMysql(){
		Record list = DbPro.use().findFirst("select * from wocao");
		return list;
	}
}
