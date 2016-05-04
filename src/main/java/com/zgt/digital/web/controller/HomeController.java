package com.zgt.digital.web.controller;

import com.jfinal.ext.route.ControllerBind;
import com.zgt.digital.base.BaseController;
import com.zgt.digital.util.Consts;

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
}
