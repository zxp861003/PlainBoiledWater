package com.ucenter.channel.action;

import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ucenter.channel.action.base.BaseChargeAction;
import com.ucenter.channel.channel.type.TaiQiChannel;

public class OrderAction extends BaseChargeAction
{
	protected Object defaultMethod(HttpServletRequest request, HttpServletResponse response)
	{
		
		System.out.println("----------收到order请求！=====");
		System.out.println("request.getRequestURL()="+request.getRequestURL());
		System.out.println("request.getQueryString()="+request.getQueryString());
		
		Properties postParams = null;
		try
		{
			//TODO 看看是哪种传参方式
//			postParams = HttpUtil.parsePosParams(request,"utf-8");
			postParams =readPosParams(request);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "SUCCESS";
		}

		String loginResult = TaiQiChannel.receiveOrder(postParams);
		return loginResult;
	}
	
	@SuppressWarnings("unchecked")
	public static Properties readPosParams(HttpServletRequest request) throws Exception
	{
	   Properties result=new Properties();
	   Map<String, String[]> params = request.getParameterMap();   
       for (String key : params.keySet()) 
       {
           String[] values = params.get(key); 
           if(values!=null && values.length>0){
        	   result.setProperty(key, values[0]);
        	   System.out.println("key =" + key + "values =" + values[0]);
           }
           else{
        	   result.setProperty(key, "");
        	   System.out.println("key =" + key + "values =" );
           }
       }  
       return result;
	}
}
