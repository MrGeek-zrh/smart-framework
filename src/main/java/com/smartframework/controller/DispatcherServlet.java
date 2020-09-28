package com.smartframework.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.smartframework.constant.Data;
import com.smartframework.constant.Handler;
import com.smartframework.constant.Param;
import com.smartframework.constant.View;
import com.smartframework.helper.BeanHelper;
import com.smartframework.helper.ConfigHelper;
import com.smartframework.helper.ControllerHelper;
import com.smartframework.helper.HelperLoader;
import com.smartframework.utils.CodeUtil;
import com.smartframework.utils.JsonUtil;
import com.smartframework.utils.ReflectionUtil;
import com.smartframework.utils.StreamUtil;

/**
 * 整个MVC框架唯一的一个Servlet，用于处理web项目中所有的请求
* <p>Title: DispatcherServlet.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2020<／p>
* <p>Company: CUIT<／p>
* @author MrGeek
* @date 2020-09-22_22:42:53
* @version 1.0
 */
public class DispatcherServlet extends HttpServlet{

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		//初始化相关Helper类
		HelperLoader.init();
		//获取ServletContext对象（用于注册Servlet）
		ServletContext servletContext = servletConfig.getServletContext();
		//注册处理JSP的Servlet
		ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
		jspServlet.addMapping(ConfigHelper.getAPPJspBasePath()+"*");
		//注册处理静态资源的默认Servlet
		ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
		defaultServlet.addMapping(ConfigHelper.getAPPStaticResourceBasePath()+"*");
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//获取请求方法与请求路径
		String requestMethod = request.getMethod().toLowerCase();
		String requestPath = request.getPathInfo();
		
		//调用ControllerHelper方法，通过Request对象获取对应的Handler处理器
		Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
		//判断有没有成功获取handler处理器
		if (handler!=null) {
			//成功获取hanlder处理器
			//获取Controller类及其Bean实例
			Class<?>controllerClass = handler.getControllerClass();
			Object controllerBean = BeanHelper.getBean(controllerClass);
			
			//创建请求参数对象，用于保存request中所有的参数的key和value
			Map<String, Object>paramMap = new HashedMap();
			
			//获取request中所有的参数的名称
			Enumeration<String>paramNames = request.getParameterNames();
			//遍历集合，将所有的参数封装到Map集合中
			while (paramNames.hasMoreElements()) {
				//获取参数的名称
				String paramName = (String) paramNames.nextElement();
				//通过参数名称获取对应的值
				String paramValue = request.getParameter(paramName);
				//将参数的name和value封装到Map集合中
				paramMap.put(paramName, paramValue);
			}
			
			//将request中的参数进行解码，将参数还原
			//body代表的是url中的参数部分，比如：https://r1---sn-ci5gup-cags.googlevideo.com/videoplayback?pcm2cms=yes&mime=video%2Fmp4
										// 的？后面部分
			String body = CodeUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
			if (StringUtils.isNotEmpty(body)) {
				//将字符串初步分割
				String[] params = StringUtils.split(body, "&");
				if (ArrayUtils.isNotEmpty(params)) {
					for (String param : params) {
						//将字符串进一步分割
						String[] array = StringUtils.split(param, "=");
						if (ArrayUtils.isNotEmpty(array)&&array.length==2) {
							String paramName = array[0];
							String paramValue = array[1];
							//将处理好的参数封装进Map集合中
							paramMap.put(paramName, paramValue);
						}
					}
				}
			}
			
			//将paramMap封装到Param实体类中
			Param param = new Param(paramMap);
			
			//调用Action方法
			Method actionMethod = handler.getActionMethod();
			Object result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
			
			//处理Action方法返回值
			//判断返回值的类型
			//判断返回结果是否是JSP页面
			if (result instanceof View) {
				//是View的实例，则返回JSP页面
				
				//将result强转为view类
				View view = (View) result;
				
				//获取需要返回的jsp页面的具体路径
				String path = view.getPath();
				
				if (StringUtils.isNotEmpty(path)) {
					if (path.startsWith("/")) {
						response.sendRedirect(request.getContextPath()+path);
					}else {
						Map<String, Object>model = view.getModel();
						for (Map.Entry<String, Object> entry: model.entrySet()) {
							request.setAttribute(entry.getKey(), entry.getValue());
						}
						request.getRequestDispatcher(ConfigHelper.getAPPJspBasePath()+path).forward(request, response);
					}
				}
				//返回结果是JSON数据
			}else if (result instanceof Data) {
				//返回JSON数据
				Data data = (Data) result;
				Object model = data.getModel();
				if (model!=null) {
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					//将数据转换为JSON字符串
					PrintWriter writer = response.getWriter();
					String json = JsonUtil.toJson(model);
					//将字符床写入流中
					writer.write(json);
					writer.flush();
					writer.close();
				}
			}
		}
	}
}
