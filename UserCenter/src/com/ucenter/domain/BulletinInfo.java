package com.ucenter.domain;

import com.fire.utils.json.JsonUtils;

public class BulletinInfo
{
	private int bulletinId;
	private byte bulletinType;
	private int gameId;
	private int areaId;
	private String subject;
	private String subjectRes;
	private String title;
	private String content;
	private byte status;
	private String activeTime;

	public BulletinInfo(int bulletinId)
	{
		this.bulletinId = bulletinId;
	}

	public int getBulletinId()
	{
		return bulletinId;
	}

	public byte getBulletinType()
	{
		return bulletinType;
	}

	public void setBulletinType(byte bulletType)
	{
		this.bulletinType = bulletType;
	}

	public int getGameId()
	{
		return gameId;
	}

	public void setGameId(int gameId)
	{
		this.gameId = gameId;
	}

	public int getAreaId()
	{
		return areaId;
	}

	public void setAreaId(int areaId)
	{
		this.areaId = areaId;
	}

	public String getSubject()
	{
		return subject;
	}

	public void setSubject(String subject)
	{
		this.subject = subject;
	}

	public String getSubjectRes()
	{
		return subjectRes;
	}

	public void setSubjectRes(String subjectRes)
	{
		this.subjectRes = subjectRes;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public byte getStatus()
	{
		return status;
	}

	public void setStatus(byte status)
	{
		this.status = status;
	}

	public String getActiveTime()
	{
		return activeTime;
	}

	public void setActiveTime(String activeTime)
	{
		this.activeTime = activeTime;
	}

	public String toJson()
	{
		return "{\"bulletinId\":" + bulletinId + ",\"type\":" + bulletinType + ",\"gameId\":" + gameId + ",\"areaId\":"
				+ areaId + ",\"subject\":\"" + JsonUtils.safeString(subject) + "\",\"subjectRes\":\""
				+ JsonUtils.safeString(subjectRes) + "\",\"title\":\"" + JsonUtils.safeString(title)
				+ "\",\"content\":\"" + JsonUtils.safeString(content) + "\",\"status\":" + status
				+ ",\"activeTime\":\"" + JsonUtils.safeString(activeTime) + "\"}";
	}

}
