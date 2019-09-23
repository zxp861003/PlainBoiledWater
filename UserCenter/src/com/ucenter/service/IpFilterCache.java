package com.ucenter.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import com.fire.domain.ResultList;
import com.fire.webbase.common.exception.ApplicationException;
import com.ucenter.domain.GameIpFilter;
import com.ucenter.persistence.dbbean.IpFilter;
import com.ucenter.persistence.provider.IpFilterDataProvider;

public class IpFilterCache
{
	private final static byte IP_EDIT_MODE_NONE = 0;
	private final static byte IP_EDIT_MODE_ADD_WHITE = 1;
	private final static byte IP_EDIT_MODE_DEL_WHITE = 2;
	private final static byte IP_EDIT_MODE_ADD_BLACK = 3;
	private final static byte IP_EDIT_MODE_DEL_BLACK = 4;

	// 只增不减，避免线程同步问题
	// 由于游戏数量非常少，因此不用Map
	private static List<GameIpFilter> gameFilterList = new CopyOnWriteArrayList<GameIpFilter>();

	public static GameIpFilter findGameFilter(int gameId)
	{
		GameIpFilter result = null;
		for (int i = gameFilterList.size() - 1; result == null && i >= 0; i--)
		{
			if (gameFilterList.get(i).getGameId() == gameId)
			{
				result = gameFilterList.get(i);
			}
		}
		return result;
	}

	public static synchronized void reloadGameFilter()
	{
		GameIpFilter gameInfo = null;
		long currentVersion = System.currentTimeMillis();
		IpFilterDataProvider provider = IpFilterDataProvider.getInstance();
		ResultList tmpList = provider.findAll(null);
		if (tmpList != null && tmpList.size() > 0)
		{
			IpFilter filterInfo;
			for (int i = 0; i < tmpList.size(); i++)
			{
				filterInfo = (IpFilter) tmpList.get(i);
				if (gameInfo == null || gameInfo.getGameId() != filterInfo.getGameId())
				{
					gameInfo = findGameFilter(filterInfo.getGameId());
					if (gameInfo == null)
					{
						gameInfo = new GameIpFilter(filterInfo.getGameId());
						gameFilterList.add(gameInfo);
					}
				}
				if (gameInfo.getDataVersion() != currentVersion)
				{
					gameInfo.reset();
					gameInfo.setDataVersion(currentVersion);
				}
				gameInfo.regIpFilter(filterInfo.getIp(), filterInfo.getStatus());
			}
		}
	}

	public static boolean isAccessible(int gameId, String ip,boolean defaultAcc)
	{
		GameIpFilter gameInfo = findGameFilter(gameId);
		if(gameInfo == null)
			return defaultAcc;
		
		return gameInfo.isAccessible(ip,defaultAcc);
	}

	private static void addIpFilterToDB(int gameId, String ip, int filterStatus)
	{
		if (gameId <= 0 || ip == null || ip.length() <= 0)
			return;

		if (filterStatus != IpFilter.WHITE_IP && filterStatus != IpFilter.BLACK_IP)
			return;

		IpFilterDataProvider provider = IpFilterDataProvider.getInstance();
		if (provider == null)
			return;

		Map<String, String> searchParams = new HashMap<String, String>();
		searchParams.put("gameId", String.valueOf(gameId));
		searchParams.put("ip", ip);
		ResultList oldList = provider.findAll(searchParams);

		IpFilter ipFilter;
		boolean needSave = false;
		if (oldList != null && oldList.size() >= 1)
		{
			ipFilter = (IpFilter) oldList.get(0);
			if (ipFilter.getGameId() != gameId || ip.equals(ipFilter.getIp()) || filterStatus != ipFilter.getStatus())
			{
				needSave = true;
				ipFilter.setGameId(gameId);
				ipFilter.setIp(ip);
				ipFilter.setStatus((byte) filterStatus);
			}
		}
		else
		{
			needSave = true;
			ipFilter = new IpFilter();
			ipFilter.setGameId(gameId);
			ipFilter.setIp(ip);
			ipFilter.setStatus((byte) filterStatus);
		}

		if (needSave)
		{
			try
			{
				provider.saveIpFilter(ipFilter);
			}
			catch (ApplicationException e)
			{
				e.printStackTrace();
			}
		}
	}

	private static void delIpFilterFromDB(int gameId, String ip)
	{
		if (gameId <= 0 || ip == null || ip.length() <= 0)
			return;

		IpFilterDataProvider provider = IpFilterDataProvider.getInstance();
		if (provider == null)
			return;

		IpFilter ipFilter = new IpFilter();
		ipFilter.setNew(false);
		ipFilter.setGameId(gameId);
		ipFilter.setIp(ip);
		try
		{
			provider.delete(ipFilter);
		}
		catch (ApplicationException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public synchronized static boolean editFilterData(int gameId, String ip, byte mode)
	{
		if (gameId <= 0 || ip == null || ip.length() <= 0 || mode < IP_EDIT_MODE_NONE || mode > IP_EDIT_MODE_DEL_BLACK)
			return false;

		IpFilterDataProvider provider = IpFilterDataProvider.getInstance();
		if (provider == null)
			return false;

		GameIpFilter gameInfo;
		boolean result = false;
		switch (mode)
		{
		case IP_EDIT_MODE_ADD_WHITE:
		case IP_EDIT_MODE_ADD_BLACK:
			gameInfo = findGameFilter(gameId);
			if (gameInfo == null)
			{
				gameInfo = new GameIpFilter(gameId);
				gameFilterList.add(gameInfo);
			}
			gameInfo.regIpFilter(ip, (mode == IP_EDIT_MODE_ADD_WHITE) ? IpFilter.WHITE_IP : IpFilter.BLACK_IP);
			addIpFilterToDB(gameId, ip, (mode == IP_EDIT_MODE_ADD_WHITE) ? IpFilter.WHITE_IP : IpFilter.BLACK_IP);
			break;
		case IP_EDIT_MODE_DEL_WHITE:
		case IP_EDIT_MODE_DEL_BLACK:
			gameInfo = findGameFilter(gameId);
			if (gameInfo != null)
			{
				gameInfo.delIpFilter(ip);
			}
			delIpFilterFromDB(gameId, ip);
			break;
		}

		return result;
	}

	public static void uploadIpFilterData(int gameId, int filterStatus, String ipData)
	{
		int len = (ipData != null) ? ipData.length() : 0;
		if (len <= 0)
			return;

		if (filterStatus != IpFilter.WHITE_IP && filterStatus != IpFilter.BLACK_IP)
			return;

		GameIpFilter gameInfo = findGameFilter(gameId);
		if (gameInfo == null)
		{
			gameInfo = new GameIpFilter(gameId);
			gameFilterList.add(gameInfo);
		}

		int scan = 0;
		int pnstart = 0;
		String ip;
		char ch;
		while (scan < len)
		{
			ch = ipData.charAt(scan);
			switch (ch)
			{
			case '\r':
			case '\n':
				if (scan > pnstart)
				{
					ip = ipData.substring(pnstart, scan);
					if (gameInfo.regIpFilter(ip, filterStatus))
					{
						addIpFilterToDB(gameId, ip, filterStatus);
					}
				}
				pnstart = scan + 1;
				break;
			}
			scan++;
		}

		if (scan > pnstart)
		{
			ip = ipData.substring(pnstart, scan);
			if (gameInfo.regIpFilter(ip, filterStatus))
			{
				addIpFilterToDB(gameId, ip, filterStatus);
			}
		}
	}

	public static String gameFilterData(int gameId)
	{
		GameIpFilter gameInfo = findGameFilter(gameId);
		if (gameInfo != null)
			return gameInfo.toJson();
		else
			return "{}";
	}
}
