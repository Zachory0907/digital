package com.zgt.digital.biz;

import java.util.List;

import com.jfinal.plugin.activerecord.DbPro;
import com.jfinal.plugin.activerecord.Record;

public class Pay {

	public static List<Record> getTypes() {
		return DbPro.use().find("SELECT c.shop, s.S_NAME SHOPNAME FROM digital_shop s LEFT JOIN digital_cart c ON c.SHOP = s.S_ID GROUP BY c.shop");
	}

	public static List<Record> getGoodsByShop(int shop, int user, int status) {
		return DbPro.use().find("SELECT s.S_NAME, c.*, g.G_LOGO LOGO, g.G_TITLE TITLE, g.G_COST COST FROM digital_cart c LEFT JOIN digital_shop s ON c.SHOP = s.S_ID LEFT JOIN digital_good g ON g.G_ID = c.GOOD WHERE shop = " + shop + " AND c.user= " + user + " AND status= " + status);
	}

	public static int likeIt(int id) {
		return DbPro.use().update("UPDATE digital_cart SET STATUS = 2 WHERE C_ID = " + id);
	}

	public static int saveAddr(String addr, int id) {
		Record rec = DbPro.use().findFirst("SELECT * FROM digital_address where user="+id);
		if(rec!=null){
			System.out.println("已经有");
			return DbPro.use().update("UPDATE digital_address SET ADDR= " + addr + " WHERE USER= " + id);
		}else{
			System.out.println("还没有");
			return DbPro.use().update("INSERT INTO  digital_address values(null, ?, ?)", id, addr);
		}
	}

	public static Record loadAddr(int user) {
		return DbPro.use().findFirst("SELECT ADDR FROM digital_address WHERE USER = " + user);
	}

	public static List<Record> loadItems(int user) {
		return DbPro.use().find("SELECT s.S_NAME, s.S_HOME, g.G_TITLE, g.G_ID, c.C_ID, c.NUMBER, c.PRICE FROM digital_cart c LEFT JOIN digital_shop s ON c.SHOP = s.S_ID LEFT JOIN digital_good g ON c.good = g.G_ID WHERE USER = " + user + " AND STATUS=3");
	}

	public static void changeStatus(int user, String ids) {
		String sql = "UPDATE digital_cart SET STATUS = 1 WHERE C_ID IN (" + ids + ") AND USER = 15";
		System.out.println(sql);
	}

}
