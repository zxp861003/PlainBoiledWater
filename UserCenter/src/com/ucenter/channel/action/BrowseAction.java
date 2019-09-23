package com.ucenter.channel.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ucenter.channel.action.base.BaseChargeAction;

public class BrowseAction extends BaseChargeAction
{
	protected String Test4Method(HttpServletRequest request, HttpServletResponse response)
	{
		return "OK2";
	}

	protected void Test5Method(HttpServletRequest request, HttpServletResponse response)
	{
	}

	protected Map<String, String> Test6Method(HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("name", "zzz");
		dataMap.put("age", "18");
		return dataMap;
	}
}
