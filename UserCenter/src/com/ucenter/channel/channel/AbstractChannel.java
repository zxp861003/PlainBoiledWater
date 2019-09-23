package com.ucenter.channel.channel;

import com.ucenter.common.AuthInfo;


public abstract class AbstractChannel implements IChannel{

	public AuthInfo auth(int accountId, String password, String channel){
		
		return onAuth(accountId, password, channel);
	}
	
	abstract protected AuthInfo onAuth(int accountId, String password, String channel);
}
