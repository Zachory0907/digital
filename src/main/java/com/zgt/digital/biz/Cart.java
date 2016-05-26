package com.zgt.digital.biz;

import java.util.List;

import com.jfinal.plugin.activerecord.DbPro;
import com.jfinal.plugin.activerecord.Record;

public class Cart {

	public static List<Record> getTypes() {
		return DbPro.use().find("SELECT c.shop, s.S_NAME SHOPNAME FROM digital_shop s LEFT JOIN digital_cart c ON c.SHOP = s.S_ID GROUP BY c.shop");
	}

	public static List<Record> getGoodsByShop(int shop, int user, int status) {
		return DbPro.use().find("SELECT s.S_NAME, c.*, g.G_LOGO LOGO, g.G_TITLE TITLE, g.G_COST COST FROM digital_cart c LEFT JOIN digital_shop s ON c.SHOP = s.S_ID LEFT JOIN digital_good g ON g.G_ID = c.GOOD WHERE shop = " + shop + " AND c.user= " + user + " AND status= " + status);
	}

	public static int likeIt(int id) {
		return DbPro.use().update("UPDATE digital_cart SET STATUS = 2 WHERE C_ID = " + id);
	}

	public static int deleteIt(int id) {
		return DbPro.use().update("DELETE FROM digital_cart WHERE C_ID = " + id);
	}

	public static int toPay(String ids) {
		return DbPro.use().update("UPDATE digital_cart SET STATUS=3 WHERE C_ID IN (" + ids + ")");
	}

}
