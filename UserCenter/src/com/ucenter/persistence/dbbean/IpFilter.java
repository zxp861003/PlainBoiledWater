package com.ucenter.persistence.dbbean;

import java.io.Serializable;

import com.fire.domain.DomainData;
import com.fire.webbase.common.exception.InvalidTypeException;

public class IpFilter extends DomainData implements Serializable
{
	public final static int WHITE_IP=1;
	
	public final static int BLACK_IP=2;
	

	private static final long serialVersionUID = 5877842900500134623L;

	private boolean isNew;

	private int filterId;

	private int gameId;

	private String ip;

	private byte status;

	public IpFilter()
	{
		init();
	}

	public void copy(IpFilter source)
	{
		this.isNew = source.isNew();
		this.filterId = source.getFilterId();
		this.gameId = source.getGameId();
		this.ip = source.getIp();
		this.status = source.getStatus();
	}

	public void copy(DomainData source)
	{
		if (source instanceof IpFilter)
		{
			copy((IpFilter) source);
		}
		else
		{
			throw new InvalidTypeException("IpFilter", source.getClass().toString());
		}
	}

	public boolean equals(IpFilter source)
	{
		if (this.filterId != source.getFilterId())
		{
			return false;
		}
		if ((this.ip == null && source.getIp() != null) || (this.ip != null && source.getIp() == null)
				|| (this.ip != null && source.getIp() != null && !this.ip.equals(source.getIp())))
		{
			return false;
		}
		if (this.gameId != source.getGameId())
		{
			return false;
		}
		if (this.status != source.getStatus())
		{
			return false;
		}
		return true;
	}

	public boolean equals(DomainData source)
	{
		if ((source instanceof IpFilter) && (equals((IpFilter) source)))
		{
			return true;
		}
		return false;
	}

	public boolean isNew()
	{
		return this.isNew;
	}

	public int getFilterId()
	{
		return filterId;
	}

	public void setFilterId(int filterId)
	{
		this.filterId = filterId;
	}

	public String getIp()
	{
		return ip;
	}

	public void setIp(String ip)
	{
		this.ip = ip;
	}

	public int getGameId()
	{
		return gameId;
	}

	public void setGameId(int gameId)
	{
		this.gameId = gameId;
	}

	public byte getStatus()
	{
		return status;
	}

	public void setStatus(byte status)
	{
		this.status = status;
	}

	public void init()
	{
		this.filterId = 0;
		this.gameId = 0;
		this.ip = "";
		this.status = 0;
		this.isNew = true;
	}

	public void setNew(boolean isNew)
	{
		this.isNew = isNew;
	}

	/** Returns the String representation */
	public String toString()
	{
		return "(Account) " + "new='" + isNew + "', " + "filterId='" + filterId + "', " + "gameId='" + gameId + "', "
				+ "ip='" + ip + "', " + "status='" + status + "'";
	}

	public String toCSVLine()
	{
		return "\"" + filterId + "\",\"" + gameId + "\",\"" + ip + "\",\"" + status + "\"";
	}

	public String toCSVTitles()
	{
		return null;
	}

	public String getUniqueKey()
	{
		return String.valueOf(this.filterId);
	}

	@Override
	public String getUniqueId()
	{
		return String.valueOf(this.filterId);
	}

	@Override
	public void fixNewData()
	{
		
	}

}