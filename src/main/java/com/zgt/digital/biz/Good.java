package com.zgt.digital.biz;

import com.jfinal.plugin.activerecord.DbPro;
import com.jfinal.plugin.activerecord.Record;

public class Good {

	public static Record getGood(int id) {
		return DbPro.use().findFirst("SELECT * FROM digital_good WHERE g_id=" + id);
	}

	public static int add(Record rec, int user) {
		int shop = rec.getInt("shop");
		int good = rec.getInt("good");
		int number = rec.getInt("number");
		int status = rec.getInt("status");
		String price = rec.getStr("price");
		String options = rec.getStr("options");
		int i = DbPro.use().update("insert into digital_cart(C_ID,SHOP,GOOD,NUMBER,PRICE,OPTIONS,STATUS,USER) values(null,?,?,?,?,?,?,?)", shop, good, number, price, options, status, user);
		return i;
	}

}
