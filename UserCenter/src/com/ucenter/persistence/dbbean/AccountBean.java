/**
 * AccountBean.java
 *
 *
 * $LastChangedBy:  $
 * $LastChangedDate:  $
 * $Revision:  $
 */
package com.ucenter.persistence.dbbean;

import java.io.Serializable;
import java.util.Date;

import com.fire.domain.DomainData;

/**
 * Domain object class for AccountBean
 *
 * @author CodeMaster v1.0
 */
public class AccountBean extends DomainData implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = -7577852826093154999L;

	/**
	 *  */
	private int id;
/**
 *  */
	private int accountId;
	/**
	 *  */
	private int gameId;

/**
 *  */
	private String email;

/**
 *  */
	private String password;

/**
 *  */
	private String osType;

/**
 *  */
	private String osVersion;

/**
 *  */
	private int deviceType;

/**
 *  */
	private String deviceName;

/**
 *  */
	private String udid;

/**
 *  */
	private String mobile;

/**
 *  */
	private Date createTime;

/**
 *  */
	private Date bindTime;

/**
 *  */
	private int lastAreaId;

/**
 *  */
	private int channelType;

/**
 *  */
	private int channelId;
	
	private Date lastLoginTime;

	public AccountBean()
	{
		init();
	}

	/** Gets  */
	public int getAccountId()
	{
		return this.accountId;
	}

	/** Gets  */
	public String getEmail()
	{
		return this.email;
	}

	/** Gets  */
	public String getPassword()
	{
		return this.password;
	}

	/** Gets  */
	public String getOsType()
	{
		return this.osType;
	}

	/** Gets  */
	public String getOsVersion()
	{
		return this.osVersion;
	}

	/** Gets  */
	public int getDeviceType()
	{
		return this.deviceType;
	}

	/** Gets  */
	public String getDeviceName()
	{
		return this.deviceName;
	}

	/** Gets  */
	public String getUdid()
	{
		return this.udid;
	}

	/** Gets  */
	public String getMobile()
	{
		return this.mobile;
	}

	/** Gets  */
	public Date getCreateTime()
	{
		return this.createTime;
	}

	/** Gets  */
	public Date getBindTime()
	{
		return this.bindTime;
	}

	/** Gets  */
	public int getLastAreaId()
	{
		return this.lastAreaId;
	}

	/** Gets  */
	public int getChannelType()
	{
		return this.channelType;
	}

	/** Gets  */
	public int getChannelId()
	{
		return this.channelId;
	}

	/** Initializes the values */
	public void init()
	{
		this.gameId = 0;
		this.email = "";
		this.password = "";
		this.osType = "";
		this.osVersion = "";
		this.deviceType = 0;
		this.deviceName = "";
		this.udid = "";
		this.mobile = "";
		this.createTime = null;
		this.bindTime = null;
		this.lastAreaId = 0;
		this.channelType = 0;
		this.channelId = 0;
	}

	/** Sets  */
	public void setAccountId(int accountId)
	{
		this.accountId = accountId;
	}

	/** Sets  */
	public void setEmail(String email)
	{
		this.email = email;
	}

	/** Sets  */
	public void setPassword(String password)
	{
		this.password = password;
	}

	/** Sets  */
	public void setOsType(String osType)
	{
		this.osType = osType;
	}

	/** Sets  */
	public void setOsVersion(String osVersion)
	{
		this.osVersion = osVersion;
	}

	/** Sets  */
	public void setDeviceType(int deviceType)
	{
		this.deviceType = deviceType;
	}

	/** Sets  */
	public void setDeviceName(String deviceName)
	{
		this.deviceName = deviceName;
	}

	/** Sets  */
	public void setUdid(String udid)
	{
		this.udid = udid;
	}

	/** Sets  */
	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	/** Sets  */
	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	/** Sets  */
	public void setBindTime(Date bindTime)
	{
		this.bindTime = bindTime;
	}

	/** Sets  */
	public void setLastAreaId(int lastAreaId)
	{
		this.lastAreaId = lastAreaId;
	}

	/** Sets  */
	public void setChannelType(int channelType)
	{
		this.channelType = channelType;
	}

	/** Sets  */
	public void setChannelId(int channelId)
	{
		this.channelId = channelId;
	}

	public Date getLastLoginTime()
	{
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime)
	{
		this.lastLoginTime = lastLoginTime;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/** Returns the String representation */
	public String toString()
	{
		return "(AccountBean) " 
			+ "accountId='" + accountId + "', "
			+ "email='" + email + "', "
			+ "password='" + password + "', "
			+ "osType='" + osType + "', "
			+ "osVersion='" + osVersion + "', "
			+ "deviceType='" + deviceType + "', "
			+ "deviceName='" + deviceName + "', "
			+ "udid='" + udid + "', "
			+ "mobile='" + mobile + "', "
			+ "createTime='" + createTime + "', "
			+ "bindTime='" + bindTime + "', "
			+ "lastAreaId='" + lastAreaId + "', "
			+ "channelType='" + channelType + "', "
			+ "channelId='" + channelId + "'";
	}

	@Override
	public String getUniqueId() {
		return null;
	}

	@Override
	public void fixNewData() {
		
	}

	@Override
	public void copy(DomainData data) {
		
	}

	public String toJson() {
		return null;
	}

}