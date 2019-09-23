package com.ucenter.channel.action.base;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public abstract class BaseChargeAction extends AbstractController
{
	private Map<String, Method> methodMap;
	private String actionName;

	public BaseChargeAction()
	{
		super();
		actionName = "default";
		methodMap = new HashMap<String, Method>();
		initHandlers();
	}

	public String getActionName()
	{
		return actionName;
	}

	public void setActionName(String actionName)
	{
		this.actionName = actionName;
	}

	private void initHandlers()
	{
		Method[] methodList = getClass().getDeclaredMethods();
		Method method;
		String methodName;
		Class[] params;
		Class returnType;
		for (int i = 0; i < methodList.length; i++)
		{
			method = methodList[i];
			methodName = method.getName();
			if (methodName.length() <= 6 || !method.getName().endsWith("Method"))
			{
				continue;
			}
			returnType = method.getReturnType();
			if (returnType == null || void.class.equals(returnType))
			{
				continue;
			}
			params = method.getParameterTypes();
			if (params == null || params.length != 2)
			{
				continue;
			}
			if (!HttpServletRequest.class.equals(params[0]) || !HttpServletResponse.class.equals(params[1]))
			{
				continue;
			}
			methodName = methodName.substring(0, methodName.length() - 6).toLowerCase();
			if (!Modifier.isPublic(method.getModifiers()))
			{
				method.setAccessible(true);
			}
			methodMap.put(methodName, method);
		}
	}

	protected Object defaultMethod(HttpServletRequest request, HttpServletResponse response)
	{
		return "OK";
	}

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String methodName = request.getParameter("m");
		if (methodName == null || methodName.length() <= 0)
		{
			Object dftData = defaultMethod(request, response);
			return new ModelAndView(actionName + "/main", "action", dftData);
		}

		methodName = methodName.toLowerCase();
		if (!methodMap.containsKey(methodName))
		{
			return new ModelAndView("error");
		}

		Method subMethod = methodMap.get(methodName);
		Object modelObject = subMethod.invoke(this, request, response);
		return new ModelAndView(actionName + "/" + methodName, "action", modelObject);
	}
}
