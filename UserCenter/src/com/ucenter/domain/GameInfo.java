package com.ucenter.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class GameInfo
{
	private int gameId;
	private String logServerIp;
	private int logServerPort;
	private byte status;
	private List<GameAreaInfo> areaList;

	private AtomicBoolean dataDirty;
	private String jsonText;

	private AtomicLong version;

	public GameInfo(int gameId)
	{
		this.gameId = gameId;
		this.areaList = new ArrayList<GameAreaInfo>();
		this.jsonText = "{\"gameId\":" + gameId + ",\"version\":1,\"servers\":[]}";
		this.dataDirty = new AtomicBoolean(false);
		this.version = new AtomicLong(1l);
	}

	public synchronized int getGameId()
	{
		return gameId;
	}

	public byte getStatus()
	{
		return status;
	}

	public void setStatus(byte status)
	{
		this.status = status;
	}

	public String getLogServerIp()
	{
		return logServerIp;
	}

	public void setLogServerIp(String logServerIp)
	{
		this.logServerIp = logServerIp;
	}

	public int getLogServerPort()
	{
		return logServerPort;
	}

	public void setLogServerPort(int logServerPort)
	{
		this.logServerPort = logServerPort;
	}

	public synchronized int areaCount()
	{
		return this.areaList.size();
	}

	public synchronized GameAreaInfo getAreaInfoAt(int idx)
	{
		if (idx >= 0 && idx < areaList.size())
			return areaList.get(idx);
		else
			return null;
	}

	public synchronized GameAreaInfo findAreaInfo(int areaId)
	{
		if (areaId <= 0)
			return null;

		GameAreaInfo result = null;
		for (int i = areaList.size() - 1; result == null && i >= 0; i--)
		{
			if (areaList.get(i).getAreaId() == areaId)
			{
				result = areaList.get(i);
			}
		}
		return result;
	}

	public synchronized boolean addAreaInfo(GameAreaInfo areaInfo)
	{
		if (areaInfo == null || areaInfo.getGameId() != gameId || areaInfo.getAreaId() <= 0)
			return false;

		GameAreaInfo result = findAreaInfo(areaInfo.getAreaId());
		if (result != null)
			return true;

		areaList.add(areaInfo);
		return true;
	}

	public synchronized void deleteAreaInfo(int areaId)
	{
		boolean need = false;
		for (int i = areaList.size() - 1; need && i >= 0; i--)
		{
			if (areaList.get(i).getAreaId() == areaId)
			{
				areaList.remove(i);
				need = false;
			}
		}
	}

	public synchronized void clear()
	{
		areaList.clear();
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
			jsonStr.append(",\"status\":");//表示返回信息的状态
			jsonStr.append(status);
			jsonStr.append(",\"version\":");
			jsonStr.append(version.longValue());
			jsonStr.append(",\"logServer\":\"");
			jsonStr.append(logServerIp);
			jsonStr.append("\",\"logPort\":");
			jsonStr.append(logServerPort);
			jsonStr.append(",\"servers\":[\r\n");
			int count = areaList.size();
			for (int i = 0; i < count; i++)
			{
				jsonStr.append(areaList.get(i).toJson());
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
