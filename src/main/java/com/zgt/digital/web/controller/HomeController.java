package com.zgt.digital.web.controller;

import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Record;
import com.zgt.digital.base.BaseController;
import com.zgt.digital.biz.Login;
import com.zgt.digital.biz.Register;
import com.zgt.digital.util.Consts;
import com.zgt.digital.util.JSONUtil;
import com.zgt.digital.util.MailUitls;
import com.zgt.digital.util.UUID;

@ControllerBind(controllerKey = "/home", viewPath = Consts.VIEW_PATH + "/home")
public class HomeController extends BaseController{

	public void index() {
		render("index.html");
	}
	
	public void login() {
		render("login.html");
	}
	
	public void register() {
		render("register.html");
	}
	
	public void regist() {
		String dataText = getPostData();
		Record rec = JSONUtil.parseRecord(dataText);
		String NICKNAME = rec.get("NICKNAME");
		String PASSWORD = rec.get("PASSWORD");
		String TEL = rec.get("TEL");
		String EMAIL = rec.get("EMAIL");
		String HEAD = rec.get("HEAD");
		String CHECK = UUID.getUUID();
		if(Register.regist(NICKNAME, PASSWORD, TEL, EMAIL, HEAD, CHECK)>0){
			renderText(CHECK);
			//renderJson("{CHECK:"+CHECK+"}");
		}
		//Record rec = JSONUtil.parseRecord(dataText);
		//?记得看封装！！！
	}	
	
	public void sendMail(){
		String email = getPara("email");
		String check = getPara("check");
		System.out.println(check);
		MailUitls.sendMail(email, check);
		renderSuccess();
	}
	
	public void mailCheck(){
		String check = getPara("check");
		if(Register.mailCheck(check)){
			render("index.html");
		}else{
			renderError();
		}
	}
	
	public void loginValidate(){
		String dataText = getPostData();
		Record rec = JSONUtil.parseRecord(dataText);
		String logname = rec.get("logname");
		String logpass = rec.get("logpass");
		if(Login.loginValidate(logname, logpass)){
			renderSuccess();
		}else{
			renderError();
		}
	}
	
}
