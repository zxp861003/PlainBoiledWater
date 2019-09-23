package com.ucenter.channel.channel;

import com.ucenter.common.AuthInfo;


public interface IChannel {
	
	AuthInfo auth(int accountId, String password, String channel);

}
