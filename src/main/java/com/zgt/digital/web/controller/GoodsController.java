package com.zgt.digital.web.controller;

import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Record;
import com.zgt.digital.base.BaseController;
import com.zgt.digital.biz.Good;
import com.zgt.digital.biz.Shop;
import com.zgt.digital.util.Consts;
import com.zgt.digital.util.JSONUtil;
import com.zgt.digital.util.Utils;

@ControllerBind(controllerKey = "/goods", viewPath = Consts.VIEW_PATH + "/goods")
public class GoodsController extends BaseController{

	public void index() {
		render("index.html");
	}
	
	public void getGood() {
		int id = getParaToInt("id");
		renderJson(Good.getGood(id));
	}
	
	public void getShop() {
		int id = getParaToInt("id");
		renderJson(Shop.getShop(id));
	}
	
	public void add(){
		String dataText = getPostData();
		Record rec = JSONUtil.parseRecord(dataText);
		int user = Utils.getUserId(this);
		if(user!=-1){
			int status = rec.getInt("status");
			int i = Good.add(rec, user);
			if(i == 1){
				if(status==0){
					System.out.println("加入购物车成功！！！");
					renderSuccess();
				}else{
					System.out.println("购买！！！");
				}
			}else{
				System.out.println("添加失败！！！");
				renderError();
			}
		}else{
			renderEmpty();
		}
	}
}
