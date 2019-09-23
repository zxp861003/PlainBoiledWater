package com.ucenter.channel.channel.type;

import com.ucenter.channel.channel.AbstractChannel;
import com.ucenter.common.AuthInfo;

public class DefaultChannel extends AbstractChannel{

	@Override
	protected AuthInfo onAuth(int accountId, String password, String channel) {
		return new AuthInfo(200, "OK");
	}


}
