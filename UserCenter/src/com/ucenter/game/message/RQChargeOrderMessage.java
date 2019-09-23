package com.ucenter.game.message;

import io.netty.buffer.ByteBuf;

import com.fire.net.message.AbstractMessage;
import com.fire.net.utils.NetByteUtils;
import com.ucenter.game.UcenterMessages;

public class RQChargeOrderMessage extends AbstractMessage
{
	private static final long serialVersionUID = 8210354300856932783L;

	public RQChargeOrderMessage()
	{
		this.commandId = UcenterMessages.RQMSG_CHARGE_ORDER;
	}

	@Override
	public void release()
	{
		orderId = 0;
		receiptId = null;
		channelId = 0;
		subChannel = 0;
		account = null;
		markMsg = null;
		amount = 0;
		payType = 0;
		currency = null;
	}

	@Override
	public void decode(ByteBuf in)
	{
		for (int i = this.totalLength; i > 0; i--)
		{
			in.readByte();
		}
	}

	@Override
	public void encode(ByteBuf out)
	{
		NetByteUtils.writeInt(out, orderId, UcenterMessages.BIGENDIAN);
		NetByteUtils.writeInt(out, channelId, UcenterMessages.BIGENDIAN);
		NetByteUtils.writeInt(out, subChannel, UcenterMessages.BIGENDIAN);
		NetByteUtils.writeInt(out, amount, UcenterMessages.BIGENDIAN);
		NetByteUtils.writeInt(out, payType, UcenterMessages.BIGENDIAN);
		NetByteUtils.writePstring(out, currency, UcenterMessages.BIGENDIAN);
		NetByteUtils.writePstring(out, receiptId, UcenterMessages.BIGENDIAN);
		NetByteUtils.writePstring(out, account, UcenterMessages.BIGENDIAN);
		NetByteUtils.writePstring(out, markMsg, UcenterMessages.BIGENDIAN);
	}

	// *************************************
	private int orderId;
	private String receiptId;
	private int channelId;
	private int subChannel;
	private String account;
	private String markMsg;
	private int amount;
	private int payType;
	private String currency;

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

	public void setAmount(int amount)
	{
		this.amount = amount;
	}

	public int getPayType()
	{
		return payType;
	}

	public void setPayType(int payType)
	{
		this.payType = payType;
	}

	public String getCurrency()
	{
		return currency;
	}

	public void setCurrency(String currency)
	{
		this.currency = currency;
	}

	public String getMarkMsg()
	{
		return markMsg;
	}

	public void setMarkMsg(String markMsg)
	{
		this.markMsg = markMsg;
	}

}
