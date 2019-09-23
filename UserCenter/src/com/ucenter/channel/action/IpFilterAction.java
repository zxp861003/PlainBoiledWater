package com.ucenter.channel.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fire.utils.HttpUtil;
import com.fire.utils.MD5;
import com.fire.utils.StrUtils;
import com.ucenter.channel.action.base.BaseChargeAction;
import com.ucenter.common.UcenterConst;
import com.ucenter.service.IpFilterCache;

public class IpFilterAction extends BaseChargeAction
{
	protected String defaultMethod(HttpServletRequest request, HttpServletResponse response)
	{
		return "ok";
	}
	
	protected String uploadMethod(HttpServletRequest request, HttpServletResponse response)
	{
		int gameId=StrUtils.parseint(request.getParameter("g"), 0);
		if(gameId<=0)
			return "fail:2";
		
		String passData = request.getParameter("pass");
		if (passData == null || passData.length() <= 32)
			return "fail:1";
		
		String code = passData.substring(0, 32);
		String vcode = passData.substring(32);
		vcode = MD5.getHashString(vcode + UcenterConst.P_KEY);
		if (!code.equals(vcode))
			return "fail:2";
		
		int filterMode =StrUtils.parseint(request.getParameter("mode"),0);
		
		try
		{
			String deployText = HttpUtil.readPosDataAsString(request, "utf-8");
			IpFilterCache.uploadIpFilterData(gameId,filterMode,deployText);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "fail:3";
		}

		return "ok";
	}
	
	protected String editMethod(HttpServletRequest request, HttpServletResponse response)
	{
		int gameId=StrUtils.parseint(request.getParameter("g"), 0);
		if(gameId<=0)
			return "fail:2";
		
		String passData = request.getParameter("pass");
		if (passData == null || passData.length() <= 32)
			return "fail:1";
		
		String code = passData.substring(0, 32);
		String vcode = passData.substring(32);
		vcode = MD5.getHashString(vcode + UcenterConst.P_KEY);
		if (!code.equals(vcode))
			return "fail:2";
		
		byte mode =(byte)StrUtils.parseint(request.getParameter("mode"),0);
		String ip=request.getParameter("ip");
		if(ip==null || ip.length()<7)
			return "fail:3";
		
		try
		{
			IpFilterCache.editFilterData(gameId, ip, mode);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "fail:3";
		}

		return "ok";
	}

	protected String reloadMethod(HttpServletRequest request, HttpServletResponse response)
	{
		String passData = request.getParameter("pass");
		if (passData == null || passData.length() <= 32)
			return "fail:1";
		
		String code = passData.substring(0, 32);
		String vcode = passData.substring(32);
		vcode = MD5.getHashString(vcode + UcenterConst.P_KEY);
		if (!code.equals(vcode))
			return "fail:2";
		
		IpFilterCache.reloadGameFilter();
		
		return "ok";
	}	
	
	protected String viewMethod(HttpServletRequest request, HttpServletResponse response)
	{
		int gameId=StrUtils.parseint(request.getParameter("g"), 0);
		if(gameId<=0)
			return "{\"status\":-2}";
		
		String passData = request.getParameter("pass");
		if (passData == null || passData.length() <= 32)
			return "{\"status\":-3}";
		
		String code = passData.substring(0, 32);
		String vcode = passData.substring(32);
		vcode = MD5.getHashString(vcode + UcenterConst.P_KEY);
		if (!code.equals(vcode))
			return "{\"status\":-4}";
		
		return IpFilterCache.gameFilterData(gameId);
	}	
}
