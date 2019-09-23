package com.ucenter.game.net;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.log4j.Logger;

import com.fire.net.message.AbstractMessage;
import com.fire.utils.StrUtils;
import com.ucenter.domain.GameAreaInfo;
import com.ucenter.service.GameInfoCache;

public class LinkManager {
	private static final Logger logger = Logger.getLogger(LinkManager.class);

	public static Map<String, GameAreaLink> linkMap = new ConcurrentHashMap<String, GameAreaLink>();
	public static List<GameAreaLink> linkList = new CopyOnWriteArrayList<GameAreaLink>();

	public static synchronized void frameUpateLinks() {
		for (int i = 0; i < linkList.size(); i++) {
			checkConnect(linkList.get(i));
		}
	}

	private static synchronized GameAreaLink createLink(String linkKey) {
		if (linkKey == null)
			return null;

		int p = linkKey.indexOf('_');
		if (p < 1)
			return null;

		int gameId = StrUtils.parseint(linkKey.substring(0, p), 0);
		int areaId = StrUtils.parseint(linkKey.substring(p + 1), 0);
		if (gameId <= 0 || areaId <= 0)
			return null;

		GameAreaInfo areaInfo = GameInfoCache.findGameAreaInfo(gameId, areaId);
		if (areaInfo == null)
			return null;

		GameAreaLink tcpClient = new GameAreaLink();
		tcpClient.setServerAddress(areaInfo.getAddress());
		tcpClient.setServerPort(areaInfo.getPort());
		tcpClient.setLinkStatus(areaInfo.getStatus());
		tcpClient.setLinkKey(linkKey);
		linkMap.put(linkKey, tcpClient);
		linkList.add(tcpClient);

		checkConnect(tcpClient);

		return tcpClient;
	}

	public static GameAreaLink findLink(int gameId, int gameAreaId) {
		String linkKey = gameId + "_" + gameAreaId;
		if (linkMap.containsKey(linkKey))
			return linkMap.get(linkKey);
		else
			return createLink(linkKey);
	}

	public static boolean sendRequest(AbstractMessage msg, int gameId, int gameAreaId) {
		try {
			// String linkKey = gameId + "-" + gameAreaId;
			// GameAreaLink serverLink = linkMap.get(linkKey);
			GameAreaLink serverLink = findLink(gameId, gameAreaId);
			if (serverLink != null && serverLink.isConnected()) {
				return serverLink.sendMessage(msg);
			} else {
				logger.error("连接异常:gameId=" + gameId + ",gameAreaId=" + gameAreaId);
			}
		} catch (Exception e) {
			logger.error("发送请求消息异常  " + e);
		}
		return false;
	}

	private static void checkConnect(GameAreaLink tcpClient) {
		if (tcpClient != null && tcpClient.getLinkStatus() == 1 && !tcpClient.isLinkOptionValid() && !tcpClient.isConnected()) {
			try {
				tcpClient.tryConnect();
				if (tcpClient.isConnected()) {
					logger.error(tcpClient.getLinkKey() + ":IP:" + tcpClient.getServerAddress() + "端口"
							+ tcpClient.getServerPort() + "创建链接成功 \n");
				} else {
					logger.error(tcpClient.getLinkKey() + ":IP:" + tcpClient.getServerAddress() + "端口"
							+ tcpClient.getServerPort() + "创建链接失败 \n");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
