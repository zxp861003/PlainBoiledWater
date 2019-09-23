package com.ucenter.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class GameBulletinData
{
	private int gameId;
	private List<BulletinInfo> bulletinList;

	private AtomicBoolean dataDirty;
	private String jsonText;

	private AtomicLong version;

	public GameBulletinData(int gameId)
	{
		this.gameId = gameId;
		this.bulletinList = new ArrayList<BulletinInfo>();
		this.jsonText = "{\"gameId\":" + gameId + ",\"version\":1,\"bulletins\":[]}";
		this.dataDirty = new AtomicBoolean(false);
		this.version = new AtomicLong(1l);
	}

	public synchronized int getGameId()
	{
		return gameId;
	}

	public synchronized int bulletinCount()
	{
		return this.bulletinList.size();
	}

	public synchronized BulletinInfo getBulletinAt(int idx)
	{
		if (idx >= 0 && idx < bulletinList.size())
			return bulletinList.get(idx);
		else
			return null;
	}

	public synchronized BulletinInfo findBulletin(int bulletId)
	{
		if (bulletId <= 0)
			return null;

		BulletinInfo result = null;
		for (int i = bulletinList.size() - 1; result == null && i >= 0; i--)
		{
			if (bulletinList.get(i).getBulletinId() == bulletId)
			{
				result = bulletinList.get(i);
			}
		}
		return result;
	}

	public synchronized boolean addBulletin(BulletinInfo bulletinInfo)
	{
		if (bulletinInfo == null || bulletinInfo.getBulletinId() <= 0)
			return false;

		BulletinInfo result = findBulletin(bulletinInfo.getBulletinId());
		if (result != null)
			return true;

		bulletinList.add(bulletinInfo);
		return true;
	}

	public synchronized void deleteAreaInfo(int bulletId)
	{
		boolean need = false;
		for (int i = bulletinList.size() - 1; need && i >= 0; i--)
		{
			if (bulletinList.get(i).getBulletinId() == bulletId)
			{
				bulletinList.remove(i);
				need = false;
			}
		}
	}

	public synchronized void clear()
	{
		bulletinList.clear();
	}

	public synchronized void changeNotify()
	{
		dataDirty.set(true);
	}

	public long getVersion()
	{
		return version.longValue();
	}

	public void setVersion(long vs)
	{
		version.set(vs);
	}

	public String toJson()
	{
		if (dataDirty.compareAndSet(true, false))
		{
			StringBuilder jsonStr = new StringBuilder("{\"gameId\":");
			jsonStr.append(gameId);
			jsonStr.append(",\"status\":1"); //表示返回信息的状态
			jsonStr.append(",\"version\":");
			jsonStr.append(version.longValue());
			jsonStr.append(",\"bulletins\":[\r\n");
			int count = bulletinList.size();
			for (int i = 0; i < count; i++)
			{
				jsonStr.append(bulletinList.get(i).toJson());
				if (i < count - 1)
					jsonStr.append(",\r\n");
			}
			jsonStr.append("]}");
			jsonText = jsonStr.toString();
			jsonStr = null;
			return jsonText;
		}
		else
			return jsonText;
	}
}
