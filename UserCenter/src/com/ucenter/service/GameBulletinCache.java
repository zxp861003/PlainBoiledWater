package com.ucenter.service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.fire.utils.json.JsonNode;
import com.fire.utils.json.JsonParser;
import com.ucenter.domain.BulletinInfo;
import com.ucenter.domain.GameBulletinData;

public class GameBulletinCache
{
	// 只增不减，避免线程同步问题
	// 由于游戏数量非常少，因此不用Map
	private static List<GameBulletinData> gameBulletinData = new CopyOnWriteArrayList<GameBulletinData>();

	public static GameBulletinData findGameBulletin(int gameId)
	{
		GameBulletinData result = null;
		for (int i = gameBulletinData.size() - 1; result == null && i >= 0; i--)
		{
			if (gameBulletinData.get(i).getGameId() == gameId)
			{
				result = gameBulletinData.get(i);
			}
		}
		return result;
	}

	public static BulletinInfo findBulletin(int gameId, int bulletinId)
	{
		if (gameId <= 0 || bulletinId <= 0)
			return null;

		BulletinInfo result = null;
		GameBulletinData gameInfo = findGameBulletin(gameId);
		if (gameInfo != null)
		{
			result = gameInfo.findBulletin(bulletinId);
		}
		return result;
	}

	public static void addGameBulletin(GameBulletinData gameInfo)
	{
		if (gameInfo == null || gameInfo.getGameId() <= 0)
			return;

		if (findGameBulletin(gameInfo.getGameId()) == null)
		{
			gameBulletinData.add(gameInfo);
		}
	}

	public static String allGameBulletin()
	{
		StringBuffer allInfo = new StringBuffer("{\"bulletins\":[");
		int count = gameBulletinData.size();
		for (int i = 0; i < count; i++)
		{
			allInfo.append(gameBulletinData.get(i).toJson());
			if (i < count - 1)
				allInfo.append(",\r\n");
			else
				allInfo.append("\r\n");
		}
		allInfo.append("]}");
		return allInfo.toString();
	}

	public synchronized static void updateGameBulletins(String infoText)
	{
		if (infoText == null || infoText.length() <= 0)
			return;

		try
		{
			JsonParser slParser = new JsonParser(infoText, false);
			JsonNode gameNode = slParser.Decode();
			if (gameNode != null)
			{
				int gameId = gameNode.GetAttributeInt("gameId", 0);
				if (gameId >= 0)
				{
					boolean newGameInfo = false;
					GameBulletinData gameBulletins = findGameBulletin(gameId);
					if (gameBulletins == null)
					{
						gameBulletins = new GameBulletinData(gameId);
						gameBulletinData.add(gameBulletins);
						newGameInfo = true;
					}

					gameBulletins.clear();
					long version = gameNode.GetAttributeLong("version", 0);
					gameBulletins.setVersion(version);
					JsonNode allAreaNode = gameNode.GetAttribute("bulletins", false);
					if (allAreaNode != null)
					{
						int count = allAreaNode.getArraySize();
						JsonNode areaNode;
						BulletinInfo bulletinInfo;
						for (int i = 0; i < count; i++)
						{
							areaNode = allAreaNode.GetArrayItem(i);
							int agameId = areaNode.GetAttributeInt("gameId", 0);
							int areaId = areaNode.GetAttributeInt("areaId", 0);
							if (agameId != gameId)
								continue;

							int bulletinId = areaNode.GetAttributeInt("bulletinId", 0);
							if (bulletinId <= 0)
								continue;

							int bulletinType = areaNode.GetAttributeInt("type", 0);
							String subject = areaNode.GetAttributeString("subject", "");
							String subjectRes = areaNode.GetAttributeString("subjectRes", "");
							String title = areaNode.GetAttributeString("title", "");
							String content = areaNode.GetAttributeString("content", "");
							int status = areaNode.GetAttributeInt("status", 0);
							String activeTime = areaNode.GetAttributeString("activeTime", "");
							if (title == null || title.length() <= 0 || content == null || content.length() <= 0)
								continue;

							bulletinInfo = new BulletinInfo(bulletinId);
							bulletinInfo.setBulletinType((byte) bulletinType);
							bulletinInfo.setGameId(gameId);
							bulletinInfo.setAreaId(areaId);
							bulletinInfo.setSubject(subject);
							bulletinInfo.setSubjectRes(subjectRes);
							bulletinInfo.setTitle(title);
							bulletinInfo.setContent(content);
							bulletinInfo.setStatus((byte) status);
							bulletinInfo.setActiveTime(activeTime);

							gameBulletins.addBulletin(bulletinInfo);
						}
					}
					gameBulletins.changeNotify();

					if (newGameInfo)
					{

					}
				}

			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
