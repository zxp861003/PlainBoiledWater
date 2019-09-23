package com.ucenter.domain;

import com.fire.utils.json.JsonUtils;

public class GameAreaInfo
{
	private int gameId;
	private int areaId;
	private String name;
	private String address;
	private int port;
	private int gmPort;
	private byte status;
	private String message;

	public GameAreaInfo(int gameId, int areaId)
	{
		this.gameId = gameId;
		this.areaId = areaId;
		this.name = gameId + "_" + areaId;
		this.address = "0.0.0.0";
		this.port = 0;
		this.gmPort = 0;
		this.status = 0;
		this.message = "";
	}

	public int getGameId()
	{
		return gameId;
	}

	public int getAreaId()
	{
		return areaId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public int getPort()
	{
		return port;
	}

	public void setPort(int port)
	{
		this.port = port;
	}

	public byte getStatus()
	{
		return status;
	}

	public void setStatus(byte status)
	{
		this.status = status;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public int getGmPort() {
		return gmPort;
	}

	public void setGmPort(int gmPort) {
		this.gmPort = gmPort;
	}

	public String toJson()
	{
		return "{\"gameId\":" + gameId + ",\"areaId\":" + areaId + ",\"name\":\"" + JsonUtils.safeString(name)
				+ "\",\"address\":\"" + JsonUtils.safeString(address) + "\",\"port\":" + port+ ",\"gmPort\":" + gmPort + 
				",\"status\":" + status + ",\"message\":\"" + JsonUtils.safeString(message) + "\"}";
	}

}
