package com.ucenter.persistence.dbbean;

import java.io.Serializable;

import com.fire.domain.DomainData;
import com.fire.webbase.common.exception.InvalidTypeException;

public class ChargeOrder extends DomainData implements Serializable
{
	private static final long serialVersionUID = 5261256126781185487L;

	private boolean isNew;
	private int orderId;
	private String receiptId;
	private int gameId;
	private int gameArea;
	private int channelId;
	private int subChannel;
	private String account;
	private int amount;
	private String currency;
	private int payType;
	private String orderSource;
	private byte status;
	private long createTime;
	private long closeTime;
	private String markMsg;

	// 临时
	private int dealTimes;

	public ChargeOrder()
	{
		init();
	}

	public void incDealTimes()
	{
		dealTimes++;
	}

	public int getDealTimes()
	{
		return dealTimes;
	}

	public int getOrderId()
	{
		return orderId;
	}

	public void setOrderId(int orderId)
	{
		this.orderId = orderId;
	}

	public String getReceiptId()
	{
		return receiptId;
	}

	public void setReceiptId(String receiptId)
	{
		this.receiptId = receiptId;
	}

	public int getGameId()
	{
		return gameId;
	}

	public void setGameId(int gameId)
	{
		this.gameId = gameId;
	}

	public int getGameArea()
	{
		return gameArea;
	}

	public void setGameArea(int gameArea)
	{
		this.gameArea = gameArea;
	}

	public int getChannelId()
	{
		return channelId;
	}

	public void setChannelId(int channelId)
	{
		this.channelId = channelId;
	}

	public int getSubChannel()
	{
		return subChannel;
	}

	public void setSubChannel(int subChannel)
	{
		this.subChannel = subChannel;
	}

	public String getAccount()
	{
		return account;
	}

	public void setAccount(String account)
	{
		this.account = account;
	}

	public int getAmount()
	{
		return amount;
	}

	public void setAmount(int fee)
	{
		this.amount = fee;
	}

	public void setCurrency(String currency)
	{
		this.currency = currency;
	}

	public int getPayType()
	{
		return payType;
	}

	public void setPayType(int payType)
	{
		this.payType = payType;
	}

	public String getOrderSource()
	{
		return orderSource;
	}

	public String getCurrency()
	{
		return currency;
	}

	public void setOrderSource(String orderSource)
	{
		this.orderSource = orderSource;
	}

	public byte getStatus()
	{
		return status;
	}

	public void setStatus(byte status)
	{
		this.status = status;
	}

	public long getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(long createTime)
	{
		this.createTime = createTime;
	}

	public long getCloseTime()
	{
		return closeTime;
	}

	public void setCloseTime(long closeTime)
	{
		this.closeTime = closeTime;
	}
	
	public String getMarkMsg()
	{
		return markMsg;
	}
	
	public void setMarkMsg(String markMsg)
	{
		this.markMsg = markMsg;
	}

	// ********************************

	@Override
	public void copy(DomainData source)
	{
		if (source instanceof ChargeOrder)
		{
			copy((ChargeOrder) source);
		}
		else
		{
			throw new InvalidTypeException("ChargeOrder", source.getClass().toString());
		}

	}

	public boolean equals(ChargeOrder source)
	{
		if (this.orderId != source.getOrderId())
		{
			return false;
		}
		if ((this.receiptId == null && source.getReceiptId() != null)
				|| (this.receiptId != null && source.getReceiptId() == null)
				|| (this.receiptId != null && source.getReceiptId() != null && !this.receiptId.equals(source
						.getReceiptId())))
		{
			return false;
		}
		if (this.gameId != source.getGameId())
		{
			return false;
		}
		if (this.gameArea != source.getGameArea())
		{
			return false;
		}
		if (this.channelId != source.getChannelId())
		{
			return false;
		}
		if (this.subChannel != source.getSubChannel())
		{
			return false;
		}
		if ((this.account == null && source.getAccount() != null)
				|| (this.account != null && source.getAccount() == null)
				|| (this.account != null && source.getAccount() != null && !this.account.equals(source.getAccount())))
		{
			return false;
		}
		if (this.amount != source.getAmount())
		{
			return false;
		}
		if (this.currency != source.getCurrency())
		{
			return false;
		}
		if (this.payType != source.getPayType())
		{
			return false;
		}
		if ((this.orderSource == null && source.getOrderSource() != null)
				|| (this.orderSource != null && source.getOrderSource() == null)
				|| (this.orderSource != null && source.getOrderSource() != null && !this.orderSource.equals(source
						.getOrderSource())))
		{
			return false;
		}
		if (this.status != source.getStatus())
		{
			return false;
		}
		if (this.createTime != source.getCreateTime())
		{
			return false;
		}
		if (this.closeTime != source.getCloseTime())
		{
			return false;
		}
		return true;
	}

	public boolean equals(DomainData source)
	{
		if ((source instanceof ChargeOrder) && (equals((ChargeOrder) source)))
		{
			return true;
		}
		return false;
	}

	@Override
	public String getUniqueId()
	{
		return String.valueOf(orderId);
	}

	public void init()
	{
		this.isNew = true;
		this.orderId = 0;
		this.receiptId = "";
		this.gameId = 0;
		this.gameArea = 0;
		this.channelId = 0;
		this.subChannel = 0;
		this.account = "";
		this.amount = 0;
		this.currency = "";
		this.payType = 0;
		this.orderSource = "";
		this.status = 0;
		this.createTime = 0;
		this.closeTime = 0;
		this.dealTimes = 0;
	}

	@Override
	public boolean isNew()
	{
		return isNew;
	}

	@Override
	public void setNew(boolean isnew)
	{
		this.isNew = isnew;
	}

	@Override
	public String toString()
	{
		return "(ChargeOrder) " + "new='" + isNew + "', " + "orderId='" + orderId + "', " + "receiptId='" + receiptId
				+ "', " + "gameId='" + gameId + "', " + "gameArea='" + gameArea + "', " + "channelId='" + channelId
				+ "', " + "subChannel='" + subChannel + "', " + "account='" + account + "', " + "fee='" + amount + "', "
				+ "currency='" + currency + "', " + "payType='" + payType + "', " + "orderSource='" + orderSource
				+ "', " + "status='" + status + "', " + "createTime='" + createTime + "', " + "closeTime='" + closeTime
				+ "'";
	}

	@Override
	public void fixNewData()
	{
		
	}

}
