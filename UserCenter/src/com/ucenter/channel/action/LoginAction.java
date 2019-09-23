package com.ucenter.channel.action;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fire.utils.HttpUtil;
import com.fire.utils.StrUtils;
import com.ucenter.channel.action.base.BaseChargeAction;
import com.ucenter.channel.channel.type.TaiQiChannel;
import com.ucenter.channel.channel.type.UCChannel;

public class LoginAction extends BaseChargeAction
{
	protected static final Logger loger = Logger.getLogger(LoginAction.class);

	protected String defaultMethod(HttpServletRequest request, HttpServletResponse response)
	{
		loger.error("try login");
		Properties postParams = null;
		try
		{
			postParams = HttpUtil.parsePosParams(request,"utf-8");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "{\"respCode\":400,\"message\":\"throw exception\"}";
		}
		
		String responseText=UCChannel.doLogin(postParams);
		return responseText;
	}
	
//	protected String testMethod(HttpServletRequest request, HttpServletResponse response)
//	{
//		loger.error("test login");
//		String userId =request.getParameter("userId");
//		if(userId==null || userId.length()<=0)
//		{
//			return "{\"userId\":\"\",\"message\":\"userId is Null\",\"respCode\":\"400\"}";
//		}
//		
//		AccountBean account = AccountService.findAccount(userId, "Test", userId, true);
//		if (account != null)
//			return "{\"userId\":\"" + userId + "\",\"userUUID\":\"" + account.getAccountId()
//					+ "\",\"respCode\":\"200\"}";
//		else
//			return "{\"userId\":\"\",\"message\":\"login fail\",\"respCode\":\"400\"}";
//	}
	
	protected String taiqiLoginMethod(HttpServletRequest request, HttpServletResponse response){
		loger.error("try login");
		Properties postParams = null;
		try
		{
			postParams = HttpUtil.parsePosParams(request,"utf-8");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "{\"userId\":\"\",\"message\":\"throw exception\",\"respCode\":\"400\"}";
		}
		
		String clientIp= StrUtils.nvl(request.getRemoteAddr());
		String xfIp=StrUtils.nvl(request.getHeader("X-FORWARDED-FOR"));
		
		postParams.setProperty("remoteIp", clientIp);
		postParams.setProperty("xfIp", xfIp);
		String responseText=TaiQiChannel.doLogin(postParams);
		return responseText;
	}
}
