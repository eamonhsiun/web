package com.wemeow.web.util;

import java.lang.reflect.Method;
import java.util.Enumeration;
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
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// HttpSession s=request.getSession();
		// s.setAttribute("host", PathUtil.getHost());
		// s.setAttribute("siteName", GeneUtil.SITE_NAME);
		// 角色权限控制访问
		return roleControl(request, response, handler);
	}

	/** 角色权限控制访问 */
	private boolean roleControl(HttpServletRequest request, HttpServletResponse response, Object handler) {
		HttpSession session = request.getSession();
		if (handler instanceof HandlerMethod) {
			HandlerMethod hm = (HandlerMethod) handler;
			Object target = hm.getBean();
			Class<?> clazz = hm.getBeanType();
			Method m = hm.getMethod();
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

					if (value.equals(RequestLimit.ADMIN_PRIVATE)) {
						String obj = (String) session.getAttribute("token");
						System.out.println("TEST " + obj);
					} else if (value.equals(RequestLimit.ADMIN_PUBLIC)) {

					} else if (value.equals(RequestLimit.USER_PRIVATE)) {
						Map<String,Object> obj = (Map<String, Object>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
						int userId = Integer.parseInt(obj.get("userId").toString());
						User u = userService.getUserById(userId);
						System.out.println("TEST " + u.getTokenId()+" "+u.getUpdateTime());

						
//						System.out.println("TEST " + request.getParameter("token"));
//						Enumeration<String> keys = request.getParameterNames();
//						while (keys.hasMoreElements()) {
//							String k = keys.nextElement();
//							System.out.println(k + " = " + request.getParameter(k));
//						}
						
					} else if (value.equals(RequestLimit.USER_PUBLIC)) {

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

		// try {
		// if (clazz!=null && m != null ) {
		// boolean isClzAnnotation=
		// clazz.isAnnotationPresent(RoleControl.class);
		// boolean isMethondAnnotation=m.isAnnotationPresent(RoleControl.class);
		// RoleControl rc=null;
		// //如果方法和类声明中同时存在这个注解，那么方法中的会覆盖类中的设定。
		// if(isMethondAnnotation){
		// rc=m.getAnnotation(RoleControl.class);
		// }else if(isClzAnnotation){
		// rc=clazz.getAnnotation(RoleControl.class);
		// }
		// String value=rc.value();
		// Object obj=session.getAttribute(GeneUtil.SESSION_USERTYPE_KEY);
		// String curUserType=obj==null?"":obj.toString();
		// //进行角色访问的权限控制，只有当前用户是需要的角色才予以访问。
		// boolean isEquals=StringUtils.checkEquals(value, curUserType);
		// if(!isEquals){
		// //401未授权访问
		// response.setStatus(401);
		// return false;
		// }
		// }

		return true;
	}

}
