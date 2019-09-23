package com.ucenter.channel.channel;

import com.ucenter.channel.channel.type.DefaultChannel;
import com.ucenter.common.ChannelConstants;

public class ChannelFactory {

	public static IChannel getChannel(String channel){
		channel = channel.trim();
		if(channel.equals(ChannelConstants.CHANNLE_TYPE_3366)){
			return new DefaultChannel();
		}else{
			return new DefaultChannel();
		}
		
	}
}
