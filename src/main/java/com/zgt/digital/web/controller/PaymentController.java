package com.zgt.digital.web.controller;

import com.jfinal.ext.route.ControllerBind;
import com.zgt.digital.base.BaseController;
import com.zgt.digital.biz.Pay;
import com.zgt.digital.util.Consts;
import com.zgt.digital.util.Utils;

@ControllerBind(controllerKey = "/pay", viewPath = Consts.VIEW_PATH + "/pay")
public class PaymentController extends BaseController{

	public void index() {
		render("index.html");
	}
	
	public void saveAddr(){
		int id = Utils.getUserId(this);
		if(id!=-1){
			String addr = getPostData();
			int count = Pay.saveAddr(addr, id);
			if(count == 0){
				renderError();
			}else{
				renderSuccess();
			}
		}
	}
	
	public void loadAddr(){
		int user = Utils.getUserId(this);
		if(user!=-1){
			renderJson(Pay.loadAddr(user));
		}else{
			renderEmpty();
		}
	}
	
	public void loadItems(){
		int user = Utils.getUserId(this);
		if(user!=-1){
			renderJson(Pay.loadItems(user));
		}
	}
	
	public void changeStatus(){
		int user = Utils.getUserId(this);
		String ids = getPara("ids");
		System.out.println(ids);
		Pay.changeStatus(user, ids);
		renderSuccess();
	}
}
