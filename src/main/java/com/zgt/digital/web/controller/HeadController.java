package com.zgt.digital.web.controller;

import com.jfinal.ext.route.ControllerBind;
import com.zgt.digital.base.BaseController;
import com.zgt.digital.biz.Login;
import com.zgt.digital.util.Consts;
import com.zgt.digital.util.Utils;

@ControllerBind(controllerKey = "/head", viewPath = Consts.VIEW_PATH + "/head")
public class HeadController extends BaseController{

	public void checkUser(){
		if(Utils.isLogin(this)){
			renderJson(Login.getUsr(Utils.getUserId(this)));
		}else{
			renderEmpty();
		}
	}
	
	public void toLogout(){
		if(Utils.isLogin(this)){
			Utils.setLogout(this);
			renderSuccess();
		}else{
			renderError();
		}
	}
}
