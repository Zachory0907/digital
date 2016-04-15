package com.zgt.digital.web.controller;

import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Record;
import com.zgt.digital.biz.Test;
import com.zgt.digital.util.Consts;

@ControllerBind(controllerKey = "/test", viewPath = Consts.VIEW_PATH + "/test")
public class TestController extends Controller{

	public void index() {
		render("index.html");
	}
	
	public void testMysql() {
		Record rec = Test.testMysql();
		renderJson(rec);
	}
}
