package com.ucenter.game.message;

import io.netty.buffer.ByteBuf;

import com.fire.net.message.AbstractMessage;
import com.fire.net.utils.NetByteUtils;
import com.ucenter.game.UcenterMessages;
import com.ucenter.persistence.dbbean.AccountBean;

public class AGCheckTokenMessage extends AbstractMessage
{
	private static final long serialVersionUID = -6798199458569924761L;
	private AccountBean account;
	private long userSessionId;
	
	public AGCheckTokenMessage()
	{
		this.commandId = UcenterMessages.E_AG_CHECK_TOKEN;
	}


	@Override
	public void release()
	{
	}

	@Override
	public void decode(ByteBuf in)
	{

	}

	@Override
	public void encode(ByteBuf out)
	{
		if(account != null){
			
			NetByteUtils.writeLong(out, userSessionId, false);
			NetByteUtils.writeLong(out, account.getId(), false);
			NetByteUtils.writePstring(out, account.getEmail(), false);
			NetByteUtils.writeInt(out, account.getLastAreaId(), false);
		}
	}

	public void setAccount(AccountBean account) {
		this.account = account;
	}


	public void setUserSessionId(long userSessionId) {
		this.userSessionId = userSessionId;
	}

}
