package com.wemeow.web.util;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.wemeow.web.user.entity.User;
import com.wemeow.web.user.service.UserService;

public class RequestInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler)
			throws Exception {
		resp.addHeader("Access-Control-Allow-Origin", "*");
		resp.addHeader("Access-Control-Allow-Methods", "*");
		resp.addHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
		return roleControl(req, resp, handler);
	}

	/** 角色权限控制访问 */
	private boolean roleControl(HttpServletRequest request, HttpServletResponse response, Object handler) {
		HttpSession session = request.getSession();
		if (handler instanceof HandlerMethod) {
			HandlerMethod hm = (HandlerMethod) handler;
			//Object target = hm.getBean();
			Class<?> clazz = hm.getBeanType();
			Method m = hm.getMethod();
			String extra="";
			try {
				if (clazz != null && m != null) {
					boolean isClzAnnotation = clazz.isAnnotationPresent(RequestLimit.class);
					boolean isMethondAnnotation = m.isAnnotationPresent(RequestLimit.class);
					RequestLimit rc = null;
					if (isMethondAnnotation) {
						rc = m.getAnnotation(RequestLimit.class);
					} else if (isClzAnnotation) {
						rc = clazz.getAnnotation(RequestLimit.class);
					}
					
					if(rc==null){
						return true;
					}
					String value = rc.value();
					String viewId = (String) request.getParameter("viewId");
					String token = (String) request.getParameter("token");
					System.out.println("TEST " + viewId + " " + token + " " + value);
					User user = userService.getUserById(Integer.parseInt(viewId));
					
					
					
					if (value.equals(RequestLimit.ADMIN_PRIVATE)) {
						String obj = (String) session.getAttribute("token");
						System.out.println("TEST " + obj);
						
					} else if (value.equals(RequestLimit.USER_PRIVATE)) {
						@SuppressWarnings("unchecked")
						Map<String,Object> obj = (Map<String, Object>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
						int userId = Integer.parseInt(obj.get("userId").toString());
						User u = userService.getUserById(userId);
						System.out.println("TEST " + u.getTokenId()+" "+u.getUpdateTime());


					} else {

					}

					return true;

					// request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

				}

				// response.setStatus(401);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return true;
	}

}
