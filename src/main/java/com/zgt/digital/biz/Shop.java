package com.zgt.digital.biz;

import com.jfinal.plugin.activerecord.DbPro;
import com.jfinal.plugin.activerecord.Record;

public class Shop {

	public static Record getShop(int id) {
		return DbPro.use().findFirst("SELECT * FROM digital_shop WHERE s_id=" + id);
	}

}
