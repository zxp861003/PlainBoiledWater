package com.ucenter.game.message;

import io.netty.buffer.ByteBuf;

import com.fire.net.message.AbstractMessage;
import com.ucenter.game.UcenterMessages;

public class RSPreHandMessage extends AbstractMessage
{
	public static final int MSG_LENGTH = 0;
	private static final long serialVersionUID = -6798199458569924761L;

	public RSPreHandMessage()
	{
		this.commandId = UcenterMessages.RSMSG_PREHAND;
	}

	@Override
	public void release()
	{

	}

	@Override
	public void decode(ByteBuf in)
	{
		System.out.println("rece msg Lengh=" + this.totalLength);
		for (int i = this.totalLength; i > 0; i--)
		{
			in.readByte();
		}
	}

	@Override
	public void encode(ByteBuf out)
	{
		byte[] mData = new byte[MSG_LENGTH];
		for (int i = 0; i < mData.length; i++)
		{
			mData[i] = (byte) i;
		}
		if (mData.length > 0)
		{
			out.writeBytes(mData);
		}
		this.totalLength = mData.length;
	}

}
