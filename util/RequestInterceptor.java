package com.wemeow.web.util;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.wemeow.web.user.entity.User;
import com.wemeow.web.user.service.UserService;
import com.wemeow.web.util.state.StatusCode;
import com.wemeow.web.util.state.StatusException;

public class RequestInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private UserService userService;

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
		resp.addHeader("Access-Control-Allow-Origin", "*");
		resp.addHeader("Access-Control-Allow-Methods", "*");
		resp.addHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
		return roleControl(req, resp, handler);
	}

	/** 角色权限控制访问 */
	private boolean roleControl(HttpServletRequest request, HttpServletResponse response, Object handler) {
		if (handler instanceof HandlerMethod) {
			HandlerMethod hm = (HandlerMethod) handler;
			// Object target = hm.getBean();
			Class<?> clazz = hm.getBeanType();
			Method m = hm.getMethod();
			String extra = "";
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

					if (rc == null) {
						return true;
					}
					String value = rc.value();
					String viewId = (String) request.getParameter("viewId");
					String token = (String) request.getParameter("token");
					System.out.println("TEST " + viewId + " " + token + " " + value);
					User user = userService.getUserById(Integer.parseInt(viewId));
					//检测权限
					UserService.Util.checkAuthc(token, user.getAuthc());
					//检测身份
					if (value.equals(RequestLimit.SUPER_ADMIN_PRIVATE)) {
						boolean isSuper = user.getRole().equals(RequestLimit.SUPER_ADMIN);
						if (!isSuper) {
							extra = "仅超级管理员可以访问";
							throw new StatusException(StatusCode.PERMISSION_LOW);
						}

					} else if (value.equals(RequestLimit.ADMIN_PRIVATE)) {
						boolean isSuper = user.getRole().equals(RequestLimit.SUPER_ADMIN);
						boolean isAdmin = user.getRole().equals(RequestLimit.ADMIN);
						if (!(isSuper || isAdmin)) {
							extra = "仅管理员可以访问";
							throw new StatusException(StatusCode.PERMISSION_LOW);
						}

					} else if (value.equals(RequestLimit.USER_PRIVATE)) {
						boolean isSuper = user.getRole().equals(RequestLimit.SUPER_ADMIN);
						boolean isAdmin = user.getRole().equals(RequestLimit.ADMIN);
						if (!(isSuper || isAdmin)) {
							extra = "普通用户不可访问";
							throw new StatusException(StatusCode.PERMISSION_LOW);
						}
					}

					return true;
				}
			} catch (Exception e) {
				try {
					response.getWriter().write("{\"result\":false,'\"statusCode\":" + StatusCode.PERMISSION_LOW
							+ ",\"data\":null,\"extra\":" + extra + "}");
				} catch (IOException e1) {
					e1.printStackTrace();
					response.setStatus(StatusCode.PERMISSION_LOW);
					return false;
				}
				response.setStatus(StatusCode.PERMISSION_LOW);
				return false;
			}
		}

		return false;
	}

}
