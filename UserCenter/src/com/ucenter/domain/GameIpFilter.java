package com.ucenter.domain;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ucenter.persistence.dbbean.IpFilter;

public class GameIpFilter
{
	private int gameId;

	private Map<String, Integer> ipMap;
	private Map<String, Integer> ipPatternMap;

	private long dataVersion;

	public GameIpFilter(int gameid)
	{
		gameId = gameid;
		ipMap = new ConcurrentHashMap<String, Integer>();
		ipPatternMap = new ConcurrentHashMap<String, Integer>();
		dataVersion = 0;
	}

	public synchronized void reset()
	{
		ipMap.clear();
		ipPatternMap.clear();
		dataVersion = 0;
	}

	public long getDataVersion()
	{
		return dataVersion;
	}

	public void setDataVersion(long dataVersion)
	{
		this.dataVersion = dataVersion;
	}

	public int getGameId()
	{
		return gameId;
	}

	public boolean delIpFilter(String ip)
	{
		boolean result = false;
		if (ip != null && ip.length() >= 7)
		{
			boolean isPattern = ip.endsWith(".*");
			if (isPattern)
				ip = ip.substring(0, ip.length() - 2);

			synchronized (this)
			{
				if (ipMap.containsKey(ip))
				{
					ipMap.remove(ip);
				}
				if (isPattern)
					ipPatternMap.remove(ip);
			}
		}
		return result;
	}

	public boolean regIpFilter(String ip, int filterStatus)
	{
		boolean result = false;
		if (ip != null && ip.length() >= 7)
		{
			boolean isPattern = ip.endsWith(".*");
			if (isPattern)
				ip = ip.substring(0, ip.length() - 2);

			synchronized (this)
			{
				Integer v = ipMap.get(ip);
				if (v == null || v.intValue() != filterStatus)
				{
					ipMap.put(ip, filterStatus);
					if (isPattern)
						ipPatternMap.put(ip, filterStatus);
				}
			}
			result = true;
		}
		return result;
	}

	public boolean isAccessible(String ip, boolean defaultAcc)
	{
		if (ip == null || ip.length() < 7 || ipMap.isEmpty())
			return defaultAcc;

		boolean result = defaultAcc;
		synchronized (this)
		{
			Integer v = ipMap.get(ip);
			if (v == null)
			{
				int pos = ip.lastIndexOf('.');
				if (pos > 5)
				{
					ip = ip.substring(0, pos);
					v = ipPatternMap.get(ip);
				}
			}
			if (v != null)
			{
				if (v.intValue() == IpFilter.WHITE_IP)
					result = true;
				else if (v.intValue() == IpFilter.BLACK_IP)
					result = false;
			}
		}
		return result;
	}

	public String toJson()
	{
		StringBuilder jsonBuffer = new StringBuilder("{\"gameId\":" + gameId + ",\"ipFilters\":[\r\n");
		synchronized (this)
		{
			boolean isFirst = true;
			Iterator<String> keys = ipMap.keySet().iterator();
			while (keys != null && keys.hasNext())
			{
				String key = (String) keys.next();
				Integer v = (Integer) ipMap.get(key);
				if (!isFirst)
					jsonBuffer.append(",\r\n");
				jsonBuffer.append("{\"ip\":\"").append(key).append("\",\"filter\":").append(String.valueOf(v))
						.append("}");
				isFirst = false;
			}
		}
		jsonBuffer.append("\r\n]}");
		return jsonBuffer.toString();
	}

}
