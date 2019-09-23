package com.ucenter.channel.channel.type;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.fire.utils.StrUtils;
import com.fire.utils.UrlCatcher;
import com.fire.utils.json.JsonNode;
import com.fire.utils.json.JsonParser;
import com.ucenter.channel.channel.AbstractChannel;
import com.ucenter.common.AuthInfo;
import com.ucenter.common.MD5Utils;
import com.ucenter.persistence.dbbean.AccountBean;
import com.ucenter.persistence.dbbean.ChargeOrder;
import com.ucenter.service.AccountService;
import com.ucenter.service.ChargeOrderService;

public class UCChannel extends AbstractChannel
{
	protected static final Logger loger = Logger.getLogger(UCChannel.class);

	private final static String DFT_CHARSET = "utf-8";
	private final static String UC_CHANNEL_URL = "http://127.0.0.1:8080/channel";
	private final static String CHECKLOGIN_ACTION = "/checkLogin.action";

	public static String doLogin(Properties request)
	{
		if (request == null)
			return "{\"respCode\":400,\"message\":\"params is null\"}";

		int gid = StrUtils.parseint(request.getProperty("g"), 2);
		if (gid <= 0)
			return "{\"respCode\":400,\"message\":\"gameId is invalid\"}";
		
		String sid = request.getProperty("sid");
		
//		String sign = "sid="+sid + UcenterConst.UC_CHANNEL_KEY;
		
		String loginUrl = UC_CHANNEL_URL + CHECKLOGIN_ACTION;
//		long id = System.currentTimeMillis();
//		String tmpParams = "{\"id\":\""+ id + "\",\"data\":{\"sid\":\"" + sid + "\"},\"game\":{\"gameId\":" + gid
//				+ "},\"sign\":\"" + sign + "\"}" ;
		String tmpParams =  "sid=" + sid  + "&gameId=" + gid ;

		tmpParams = MD5Utils.convertMD5(tmpParams);
		byte[] paramsData = StrUtils.stringToBytes(tmpParams, DFT_CHARSET);

		String urlResponseText = UrlCatcher.urlPost(loginUrl, paramsData, "text/html", DFT_CHARSET);
		loger.error("平台返回信息内容:" + urlResponseText);

		JsonParser tmpParser = new JsonParser(urlResponseText, false);
		JsonNode resNode = null;
		try {
			resNode = tmpParser.Decode();
			int code = resNode.GetAttributeInt("respCode", 0);
			if (code == 200) {
				loger.error("uc login suc");
				JsonNode data = resNode.GetAttribute("data",false);
				String accountId = data.GetAttributeString("accountId", "-1");
				String nickName = data.GetAttributeString("nickName", "zhangsan");
				
				int acId = StrUtils.parseint(accountId, 0);
				AccountBean bean = AccountService.updateAccountInfo(acId,nickName);
				urlResponseText = "{\"respCode\":200,\"accountId\":\"" + bean.getId()+ "\",\"nickName\":\""+nickName+"\"}"; 
			} else {
				loger.error("uc login fail");
//				String respMsg = resNode.GetAttributeString("message", "");
//				urlResponseText = "{\"userId\":\"" + userId + "\",\"respCode\":\"" + respCode + "\",\"message\":\""
//						+ respMsg + "\"}";
				urlResponseText = "{\"respCode\":400,\"message\":\"gameId is invalid\"}";
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			urlResponseText = "{\"respCode\":400,\"message\":\"gameId is invalid\"}";
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
