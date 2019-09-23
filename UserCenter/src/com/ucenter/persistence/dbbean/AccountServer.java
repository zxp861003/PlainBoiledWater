package com.ucenter.persistence.dbbean;

import java.io.Serializable;

import com.fire.domain.DomainData;
import com.fire.webbase.common.exception.InvalidTypeException;

public class AccountServer extends DomainData implements Serializable
{

	private static final long serialVersionUID = 5877842900500134623L;

	private long id;
	
	private int accountId;

	private int gameId;
	
	private int serverId;

	private long lastLoginTime;

	public AccountServer() { }

	public void copy(AccountServer source)
	{
		this._isNew = source.isNew();
		this.accountId = source.getAccountId();
		this.serverId = source.getServerId();
		this.gameId = source.getGameId();
		this.lastLoginTime = source.getLastLoginTime();
	}

	public void copy(DomainData source)
	{
		if (source instanceof AccountServer)
		{
			copy((AccountServer) source);
		}
		else
		{
			throw new InvalidTypeException("IpFilter", source.getClass().toString());
		}
	}

//	public boolean equals(AccountServer source)
//	{
//		if (this.filterId != source.getFilterId())
//		{
//			return false;
//		}
//		if ((this.ip == null && source.getIp() != null) || (this.ip != null && source.getIp() == null)
//				|| (this.ip != null && source.getIp() != null && !this.ip.equals(source.getIp())))
//		{
//			return false;
//		}
//		if (this.gameId != source.getGameId())
//		{
//			return false;
//		}
//		if (this.status != source.getStatus())
//		{
//			return false;
//		}
//		return true;
//	}

	public boolean equals(DomainData source)
	{
		if ((source instanceof AccountServer) && (equals((AccountServer) source)))
		{
			return true;
		}
		return false;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public long getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(long lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	@Override
	public String getUniqueId() {
		return null;
	}

	@Override
	public void fixNewData() {
		
	}


}