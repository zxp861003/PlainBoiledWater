package com.ucenter.channel.action;

import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fire.utils.HttpUtil;
import com.fire.utils.MD5;
import com.fire.utils.StrUtils;
import com.ucenter.channel.action.base.BaseChargeAction;
import com.ucenter.common.UcenterConst;
import com.ucenter.domain.GameInfo;
import com.ucenter.persistence.dbbean.AccountServer;
import com.ucenter.service.AccountService;
import com.ucenter.service.GameInfoCache;

public class ServerListAction extends BaseChargeAction {
	protected String defaultMethod(HttpServletRequest request, HttpServletResponse response) {
		Properties postParams = null;
		try {
			postParams = HttpUtil.parsePosParams(request, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"respCode\":400,\"message\":\"throw exception\"}";
		}
		String accountId = postParams.getProperty("accountId");
		String gameId = postParams.getProperty("gameId");

		int gid = StrUtils.parseint(gameId, 0);
		if (gid <= 0)
			return "{\"gameId\":0,\"status\":0,\"message\":\"invalid gameId\"}";

		GameInfo gameInfo = GameInfoCache.findGameInfo(gid);
		if (gameInfo == null)
			return "{\"gameId\":" + gameId + ",\"status\":0,\"message\":\"invalid gameId\"}";

		String servers = gameInfo.toJson();
		String result = servers.substring(0, servers.length() - 1);
		result = result + ",\"myservers\":[";
		List<AccountServer> list = AccountService.getAccountServerList(StrUtils.parseint(accountId, 0));
		if (list != null && list.size() > 0) {
			for (AccountServer as : list) {
				result += "{\"areaId\":" + as.getServerId() + "},";
			}
			result = result.substring(0, result.length() - 1);
		}
		result += "]}";
		return result;
	}

	protected String deployMethod(HttpServletRequest request, HttpServletResponse response) {
		String passData = request.getParameter("pass");
		if (passData == null || passData.length() <= 32)
			return "fail:1";

		String code = passData.substring(0, 32);
		String vcode = passData.substring(32);
		vcode = MD5.getHashString(vcode + UcenterConst.P_KEY);
		if (!code.equals(vcode))
			return "fail:2";

		try {
			String deployText = HttpUtil.readPosDataAsString(request, "utf-8");
			GameInfoCache.updateGameInfo(deployText);
		} catch (Exception e) {
			e.printStackTrace();
			return "fail:3";
		}

		return "ok";
	}

	protected String vsMethod(HttpServletRequest request, HttpServletResponse response) {
		int gameId = StrUtils.parseint(request.getParameter("g"), 0);
		if (gameId > 0) {
			GameInfo gameInfo = GameInfoCache.findGameInfo(gameId);
			if (gameInfo != null)
				return String.valueOf(gameInfo.getVersion());
			else
				return "-2";
		}
		return "-1";
	}

	protected String viewMethod(HttpServletRequest request, HttpServletResponse response) {
		return GameInfoCache.allGameInfo();
	}
}
