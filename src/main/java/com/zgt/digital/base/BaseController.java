package com.zgt.digital.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.render.Render;
import com.jfinal.upload.UploadFile;
import com.zgt.digital.util.Consts;

public class BaseController extends Controller{

	protected String getMethod() {
		return getRequest().getMethod();
	}

	protected String getPostData() {
		InputStream is;
		try {
			is = getRequest().getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, Consts.DEFAULT_ENCODING));
			StringBuilder sb = new StringBuilder();
			String line = null;
			try {
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	protected int getPage() {
		String p = getPara("p");
		if (p == null)
			return 0;
		return Integer.valueOf(p);
	}

	protected int getLimit() {
		String l = getPara("l");
		if (l == null)
			return 10;
		return Integer.valueOf(l);
	}

	protected void renderSuccess() {
		renderJson("{\"status\": \"ok\"}");
	}

	protected void renderError() {
		renderJson("{\"status\": \"error\"}");
	}

	protected void renderEmpty() {
		renderJson("[]");
	}

	protected void renderResultSet(Object rs, int total) {
		JSONObject obj = new JSONObject();
		obj.put("data", rs);
		obj.put("totalItems", total);
		renderJson(obj);
	}

	final class BinaryRender extends Render {
		private byte[] buffer;

		public BinaryRender(byte[] buffer) {
			this.buffer = buffer;
		}

		@Override
		public void render() {
			OutputStream out;
			try {
				out = getResponse().getOutputStream();
				out.write(buffer);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	protected void renderBytes(byte[] buffer) {
		this.render(new BinaryRender(buffer));
	}

	public UploadFile getFile(String param) {
		return super.getFile(param);
	}
}
