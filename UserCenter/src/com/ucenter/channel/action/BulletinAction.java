package com.ucenter.channel.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fire.utils.HttpUtil;
import com.fire.utils.MD5;
import com.fire.utils.StrUtils;
import com.ucenter.channel.action.base.BaseChargeAction;
import com.ucenter.common.UcenterConst;
import com.ucenter.domain.GameBulletinData;
import com.ucenter.service.GameBulletinCache;

public class BulletinAction extends BaseChargeAction
{
	protected String defaultMethod(HttpServletRequest request, HttpServletResponse response)
	{
		int gameId = StrUtils.parseint(request.getParameter("g"), 0);
		if (gameId <= 0)
			return "{\"gameId\":0,\"status\":0,\"message\":\"need gameId\"}";

		GameBulletinData bulletinData = GameBulletinCache.findGameBulletin(gameId);
		if (bulletinData == null)
			return "{\"gameId\":" + gameId + ",\"status\":0,\"message\":\"invalid gameId\"}";

		return bulletinData.toJson();
	}

	protected String deployMethod(HttpServletRequest request, HttpServletResponse response)
	{
		String passData = request.getParameter("pass");
		if (passData == null || passData.length() <= 32)
			return "fail:1";

		String code = passData.substring(0, 32);
		String vcode = passData.substring(32);
		vcode = MD5.getHashString(vcode + UcenterConst.P_KEY);
		if (!code.equals(vcode))
			return "fail:2";

		try
		{
			String deployText = HttpUtil.readPosDataAsString(request, "utf-8");
			GameBulletinCache.updateGameBulletins(deployText);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "fail:3";
		}

		return "ok";
	}

	protected String vsMethod(HttpServletRequest request, HttpServletResponse response)
	{
		int gameId = StrUtils.parseint(request.getParameter("g"), 0);
		if (gameId > 0)
		{
			GameBulletinData gameInfo = GameBulletinCache.findGameBulletin(gameId);
			if (gameInfo != null)
				return String.valueOf(gameInfo.getVersion());
			else
				return "-2";
		}
		return "-1";
	}

	protected String viewMethod(HttpServletRequest request, HttpServletResponse response)
	{
		return GameBulletinCache.allGameBulletin();
	}
}
