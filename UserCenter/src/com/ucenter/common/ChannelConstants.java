package com.ucenter.common;

/**
 * 渠道常量 
 * @author hxbing
 */
public class ChannelConstants {

	public static final String CHANNLE_TYPE_3366 = "3366";
	
	
	/**
	 * 获得渠道标识码
	 * @param channel
	 * @return
	 */
	public static int getChannelCode(String channel){
		if(channel == null)
			return 0;
		
		channel = channel.trim();
		
		if(channel.equals(CHANNLE_TYPE_3366)){
			return 1;
		}
		
		
		return 0;
	}
}
