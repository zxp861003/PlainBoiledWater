package com.ucenter.game.message;

import io.netty.buffer.ByteBuf;

import com.fire.net.message.AbstractMessage;
import com.ucenter.game.UcenterMessages;

public class RSChargeReceiptMessage extends AbstractMessage
{
	public static final int MSG_LENGTH = 0;
	private static final long serialVersionUID = -6798199458569924761L;

	public RSChargeReceiptMessage()
	{
		this.commandId = UcenterMessages.RSMSG_CHARGE_RECEIPT;
	}

	@Override
	public void release()
	{

	}

	@Override
	public void decode(ByteBuf in)
	{
//		int vint = 0;
//		String vStr = null;
//		vint = NetByteUtils.readInt(in, UcenterMessages.BIGENDIAN);
//		vint = NetByteUtils.readInt(in, UcenterMessages.BIGENDIAN);
//		vint = NetByteUtils.readInt(in, UcenterMessages.BIGENDIAN);
//		vint = NetByteUtils.readInt(in, UcenterMessages.BIGENDIAN);
//		vint = NetByteUtils.readInt(in, UcenterMessages.BIGENDIAN);
//		in.readByte();
//		vStr = NetByteUtils.readPstring(in, UcenterMessages.BIGENDIAN);
//		vStr = NetByteUtils.readPstring(in, UcenterMessages.BIGENDIAN);
	}

	@Override
	public void encode(ByteBuf out)
	{

	}

}
