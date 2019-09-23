package com.ucenter.game.message;

import io.netty.buffer.ByteBuf;

import com.fire.net.message.AbstractMessage;
import com.fire.net.utils.NetByteUtils;
import com.ucenter.game.UcenterMessages;

public class GACheckTokenMessage extends AbstractMessage
{
	private static final long serialVersionUID = 8210354300856932783L;
	private int userId;
	private String sessionKey;
	private int gameId;
	private int areaId;
	private String account;
	private String password;
	private String channel;
	private int vType;
	private String deviceId;
	private String deviceType;
	private String deviceVersion;
	private long userSessionId;

	public GACheckTokenMessage()
	{
		this.commandId = UcenterMessages.E_GA_CHECK_TOKEN;
	}

	@Override
	public void release()
	{
		sessionKey = null;
		userId = -1;
		gameId = 0;
		areaId = 0;
		sessionKey = null;
		account = null;
		vType = 0;
		channel = null;
		password = null;
		deviceId = null;
		deviceType = null;
		deviceVersion = null;
	}

	@Override
	public void decode(ByteBuf in)
	{
		userId = NetByteUtils.readInt(in, false);
		sessionKey = NetByteUtils.readPstring(in, false);
		gameId = NetByteUtils.readInt(in, false);
		areaId = NetByteUtils.readInt(in, false);
		account = NetByteUtils.readPstring(in, false);
		password = NetByteUtils.readPstring(in, false);
		channel = NetByteUtils.readPstring(in, false);
		userSessionId = NetByteUtils.readLong(in, false);
	}

	@Override
	public void encode(ByteBuf out)
	{
	}

	public long getSessionId() {
		return sessionId;
	}

	public int getAccountId() {
		return userId;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public int getAreaId() {
		return areaId;
	}

	public String getAccount() {
		return account;
	}

	public String getPassword() {
		return password;
	}

	public String getChannel() {
		return channel;
	}

	public int getvType() {
		return vType;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public String getDeviceVersion() {
		return deviceVersion;
	}

	public long getUserSessionId() {
		return userSessionId;
	}

	public int getGameId() {
		return gameId;
	}

	
}
