package com.zgt.digital.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zgt.digital.util.Consts;

/**
 * 每一次请求的时候都会将编码进行过滤
 * @author ZGT
 *
 */
public class AuthorityFilter implements Filter {

	public void destroy() {
		
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		String url1 = "/login";
		if(request.getRequestURI().indexOf(url1)==-1 && request.getSession().getAttribute(Consts.SESSION_TOKEN_KEY) == null){
			response.sendRedirect(request.getContextPath() + "/home/login");
		}else{
			chain.doFilter(req, resp);
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
