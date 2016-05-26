package com.zgt.digital.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Record;
import com.zgt.digital.base.BaseController;
import com.zgt.digital.biz.Cart;
import com.zgt.digital.util.Consts;
import com.zgt.digital.util.Utils;

@ControllerBind(controllerKey = "/cart", viewPath = Consts.VIEW_PATH + "/cart")
public class CartController extends BaseController{

	public void index() {
		render("index.html");
	}
	
	public void getTypes(){
		int user = Utils.getUserId(this);
		int status = getParaToInt("status");
		System.out.println(user!=-1);
		if(user!=-1){
			System.out.println("bbb");
			List<Record> types = Cart.getTypes();
			List<Map<String, List<Record>>> goods = new ArrayList<Map<String, List<Record>>>();
			for(Record li : types){
				Map<String, List<Record>> map = new HashMap<String, List<Record>>();
				int shop = li.getInt("shop");
				//String shopname = li.getStr("SHOPNAME");
				List<Record> good = Cart.getGoodsByShop(shop, user, status);
				System.out.println(good.toString());
				//map.put("shop", shopname);
				map.put("goods", good);
				goods.add(map);
			}
			renderJson(goods);
		}else{
			System.out.println("aaa");
			//forwardAction("/home/login");
			redirect("/home/login");
		}
	}
	
	public void likeIt() {
		int id = getParaToInt("id");
		if(Cart.likeIt(id)>0){
			renderSuccess();
		}else{
			renderError();
		}
	}
	
	public void deleteIt() {
		int id = getParaToInt("id");
		if(Cart.deleteIt(id)>0){
			renderSuccess();
		}else{
			renderError();
		}
	}
	
	public void toPay(){
		String ids = getPara("ids");
		if(Cart.toPay(ids)>0){
			renderSuccess();
		}else{
			renderError();
		}
	}
}
