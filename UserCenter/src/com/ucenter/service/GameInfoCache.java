package com.ucenter.service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.fire.utils.json.JsonNode;
import com.fire.utils.json.JsonParser;
import com.ucenter.domain.GameAreaInfo;
import com.ucenter.domain.GameInfo;

public class GameInfoCache {
	// 只增不减，避免线程同步问题
	// 由于游戏数量非常少，因此不用Map
	private static List<GameInfo> gameInfoList = new CopyOnWriteArrayList<GameInfo>();

	public static GameInfo findGameInfo(int gameId) {
		GameInfo result = null;
		for (int i = gameInfoList.size() - 1; result == null && i >= 0; i--) {
			if (gameInfoList.get(i).getGameId() == gameId) {
				result = gameInfoList.get(i);
			}
		}
		return result;
	}

	public static boolean isGameActive(int gameId) {
		if (gameId <= 0)
			return false;

		GameInfo gameInfo = findGameInfo(gameId);
		return (gameInfo != null && gameInfo.getStatus() > 0);
	}

	public static byte GameStatus(int gameId) {
		if (gameId <= 0)
			return 0;

		GameInfo gameInfo = findGameInfo(gameId);
		return (gameInfo != null) ? gameInfo.getStatus() : 0;
	}

	public static GameAreaInfo findGameAreaInfo(int gameId, int areaId) {
		if (gameId <= 0 || areaId <= 0)
			return null;

		GameAreaInfo result = null;
		GameInfo gameInfo = findGameInfo(gameId);
		if (gameInfo != null) {
			result = gameInfo.findAreaInfo(areaId);
		}
		return result;
	}

	public static void addGameInfo(GameInfo gameInfo) {
		if (gameInfo == null || gameInfo.getGameId() <= 0)
			return;

		if (findGameInfo(gameInfo.getGameId()) == null) {
			gameInfoList.add(gameInfo);
		}
	}

	public static String allGameInfo() {
		StringBuffer allInfo = new StringBuffer("{\"games\":[");
		int count = gameInfoList.size();
		for (int i = 0; i < count; i++) {
			allInfo.append(gameInfoList.get(i).toJson());
			if (i < count - 1)
				allInfo.append(",\r\n");
			else
				allInfo.append("\r\n");
		}
		allInfo.append("]}");
		return allInfo.toString();
	}

	public synchronized static void updateGameInfo(String infoText) {
		if (infoText == null || infoText.length() <= 0)
			return;

		try {
			JsonParser slParser = new JsonParser(infoText, false);
			JsonNode gameNode = slParser.Decode();
			if (gameNode != null) {
				int gameId = gameNode.GetAttributeInt("gameId", 0);
				if (gameId >= 0) {
					boolean newGameInfo = false;
					GameInfo gameInfo = findGameInfo(gameId);
					if (gameInfo == null) {
						gameInfo = new GameInfo(gameId);
						newGameInfo = true;
					}

					gameInfo.clear();
					long version = gameNode.GetAttributeLong("version", 0);
					byte status = (byte) gameNode.GetAttributeInt("status", 0);
					String logServerIp = gameNode.GetAttributeString("logServer", "");
					int logServerPort = gameNode.GetAttributeInt("logPort", 0);
					gameInfo.setVersion(version);
					gameInfo.setStatus(status);
					gameInfo.setLogServerIp(logServerIp);
					gameInfo.setLogServerPort(logServerPort);
					JsonNode allAreaNode = gameNode.GetAttribute("servers", false);
					if (allAreaNode != null) {
						int count = allAreaNode.getArraySize();
						JsonNode areaNode;
						GameAreaInfo areaInfo;
						for (int i = 0; i < count; i++) {
							areaNode = allAreaNode.GetArrayItem(i);
							int agameId = areaNode.GetAttributeInt("gameId", 0);
							int areaId = areaNode.GetAttributeInt("areaId", 0);
							if (agameId != gameId || areaId <= 0)
								continue;

							String address = areaNode.GetAttributeString("address", "");
							int port = areaNode.GetAttributeInt("port", 0);
							int gmPort = areaNode.GetAttributeInt("gmPort", 0);
							if (address == null || address.length() < 7 || port <= 0)
								continue;

							String areaName = areaNode.GetAttributeString("name", address);
							int areaStatus = areaNode.GetAttributeInt("status", 0);
							String message = areaNode.GetAttributeString("message", "");

							areaInfo = new GameAreaInfo(gameId, areaId);
							areaInfo.setName(areaName);
							areaInfo.setAddress(address);
							areaInfo.setPort(port);
							areaInfo.setGmPort(gmPort);
							areaInfo.setStatus((byte) areaStatus);
							areaInfo.setMessage(message);

							gameInfo.addAreaInfo(areaInfo);
						}
					}
					gameInfo.changeNotify();

					if (newGameInfo) {
						gameInfoList.add(gameInfo);
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
