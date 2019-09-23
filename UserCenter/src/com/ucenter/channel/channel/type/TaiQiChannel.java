package com.ucenter.channel.channel.type;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.fire.utils.StrUtils;
import com.fire.utils.UrlCatcher;
import com.fire.utils.json.JsonNode;
import com.fire.utils.json.JsonParser;
import com.ucenter.channel.channel.AbstractChannel;
import com.ucenter.common.AuthInfo;
import com.ucenter.domain.GameInfo;
import com.ucenter.persistence.dbbean.ChargeOrder;
import com.ucenter.service.ChargeOrderService;
import com.ucenter.service.GameInfoCache;
import com.ucenter.service.IpFilterCache;

public class TaiQiChannel extends AbstractChannel
{
	protected static final Logger loger = Logger.getLogger(TaiQiChannel.class);

	private final static String DFT_CHARSET = "utf-8";
	private final static String TAIQI_INLAND_URL = "http://usdk.taiqigame.com/channel/";
	private final static String TAIQI_OUTLAND_URL = "http://usdk.uqsoft.com/channel/";
	private final static String TAIQI_ACTION = "/checkLogin";

	public static String doLogin(Properties request)
	{
		if (request == null)
			return "{\"userId\":\"\",\"respCode\":\"400\",\"message\":\"params is null\"}";

		int gid = StrUtils.parseint(request.getProperty("g"), 2);
		String userId = request.getProperty("userId");
		String userKey = request.getProperty("userKey");
		String appId = request.getProperty("appId");
		String channelAction = request.getProperty("channelAction");
		String cpId = request.getProperty("cpId");
		String channelId = request.getProperty("channelId");
		String deviceToken = request.getProperty("deviceToken");
		String extInfo = request.getProperty("extInfo");

		String remoteIp = request.getProperty("remoteIp");
		String xfIp = request.getProperty("xfIp");

		if (userId == null || userId.length() <= 0)
		{
			return "{\"userId\":\"\",\"respCode\":\"400\",\"message\":\"userId is null\"}";
		}

		if (channelAction == null || channelAction.length() <= 0)
		{
			return "{\"userId\":\"" + userId + "\",\"respCode\":\"400\",\"message\":\"channelAction is null\"}";
		}

		if (gid <= 0)
		{
			return "{\"userId\":\"" + userId + "\",\"respCode\":\"400\",\"message\":\"gameId is invalid\"}";
		}

		loger.error("taiqi identity request,IP=" + remoteIp + ",xfIP=" + xfIp);
		if (remoteIp == null || remoteIp.length() <= 0)
			remoteIp = xfIp;

		if (!IpFilterCache.isAccessible(gid, remoteIp, GameInfoCache.isGameActive(gid)))
			return "{\"userId\":\"" + userId + "\",\"respCode\":\"400\",\"message\":\"game is out of service now\"}";

		// 生成泰奇认证URL
		String loginUrl;
		if(GameInfoCache.GameStatus(gid)==2)
			loginUrl = TAIQI_OUTLAND_URL + channelAction + TAIQI_ACTION;
		else
			loginUrl = TAIQI_INLAND_URL + channelAction + TAIQI_ACTION;
		String tmpParams = "userId=" + userId + "&token=" + userKey + "&cpId=" + cpId + "&appId=" + appId
				+ "&channelId=" + channelId + "&channelAction=" + channelAction;
		if (extInfo != null && extInfo.length() > 0)
			tmpParams += "&extInfo=" + extInfo;

		loger.error("taiqi identity url:" + loginUrl);
		loger.error("taiqi identity data:" + tmpParams);
		byte[] paramsData = StrUtils.stringToBytes(tmpParams, DFT_CHARSET);

		// 泰奇的Post不能加Content-type，否则对方无法读取
		String urlResponseText = UrlCatcher.urlPost(loginUrl, paramsData, null, DFT_CHARSET);
		loger.error("taiqi identity response:" + urlResponseText);

		// 解析泰奇返回
		JsonParser tmpParser = new JsonParser(urlResponseText, false);
		JsonNode resNode = null;
		try
		{
			resNode = tmpParser.Decode();
			String respCode = resNode.GetAttributeString("respCode", "");
			if ("200".equals(respCode))
			{
				loger.error("taiqi login suc");
				GameInfo gameInfo = GameInfoCache.findGameInfo(gid);
				String logServerIp = (gameInfo != null) ? gameInfo.getLogServerIp() : "";
				int logServerPort = (gameInfo != null) ? gameInfo.getLogServerPort() : 0;

				userId = resNode.GetAttributeString("userId", userId);
//				AccountBean account = AccountService.searchAccount(userId);
//				AccountBean account = AccountService.findAccount(userId, channelAction, deviceToken, true);
//				if (account != null)
//				{
//
//					urlResponseText = "{\"userId\":\"" + userId + "\",\"userUUID\":\"" + account.getAccountId()
//							+ "\",\"channelAction\":\"" + channelAction + "\",\"logServer\":\"" + logServerIp
//							+ "\",\"logPort\":" + logServerPort + ",\"deviceToken \":\"" + deviceToken
//							+ "\",\"channelId\":\"" + channelId + "\",\"extInfo\":\"" + extInfo
//							+ "\",\"respCode\":\"200\"}";
//				}
			}
			else
			{
				loger.error("taiqi login fail");
				String respMsg = resNode.GetAttributeString("message", "");
				urlResponseText = "{\"userId\":\"" + userId + "\",\"respCode\":\"" + respCode + "\",\"message\":\""
						+ respMsg + "\"}";
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			urlResponseText = "{\"userId\":\"" + userId + "\",\"message\":\"throw exception\",\"respCode\":\"400\"}";
		}
		return urlResponseText;
	}

	public static String receiveOrder(Properties request)
	{
		if (request == null)
			return "success";
		
		System.out.println("++++++++++++++receiveOrder+++++++++");
		
		String requestUrl = "host=" + request.getProperty("_remoteHost") + ",port="
				+ request.getProperty("_remotePort") + ",params=" + request.getProperty("_requestUrl");
		
		String transId = request.getProperty("transId");
		String channelId = request.getProperty("channelId");
		String amount = request.getProperty("amount");
		String userId = request.getProperty("userId");
		String markMsg = request.getProperty("markMsg");
		String payType = request.getProperty("payType");
		String currency = request.getProperty("currency");
		String signature = request.getProperty("signature");
		String respCode = request.getProperty("respCode");
		
		// 先校验signature
		
		int gameId = StrUtils.parseint(request.getProperty("gameId"), 0);
		int gameAreaId = StrUtils.parseint(request.getProperty("gameArea"), 0);
		
		ChargeOrder order = new ChargeOrder();
		order.setReceiptId(transId);
		order.setGameId(gameId);
		order.setGameArea(gameAreaId);
		order.setChannelId(StrUtils.parseint(channelId, 0));
		order.setSubChannel(0);
		order.setAccount(String.valueOf(userId));
		order.setAmount((int) (StrUtils.parseFloat(amount, 0) * 100));
		order.setCurrency(currency);
		order.setPayType(StrUtils.parseint(payType, 0));
		order.setOrderSource(requestUrl);
		order.setStatus((byte) 1);
		order.setCreateTime(System.currentTimeMillis());
		order.setCloseTime(-1);
		order.setMarkMsg(markMsg);
		
		ChargeOrderService.appendOrder(order);
		System.out.println(order);
		
		return "success";
	}

	@Override
	protected AuthInfo onAuth(int accountId, String password, String channel) {
		
		return null;
	}

}
