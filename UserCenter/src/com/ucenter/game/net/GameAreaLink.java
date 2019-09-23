package com.ucenter.game.net;

import com.fire.net.message.AbstractMessage;
import com.fire.net.server.base.gm.GMSocketClient;
import com.ucenter.game.message.RQPreHandMessage;

public class GameAreaLink
{
	private String linkKey;
	private String linkName;
	private String serverAddress;
	private int serverPort;
	private byte linkStatus;
	private boolean linkOptionValid;

	private GMSocketClient client;
	private int retryTimes;
	private long nextCheckStamp;

	public GameAreaLink()
	{
		client = null;
		linkOptionValid=false;
	}

	public String getLinkKey()
	{
		return linkKey;
	}

	public void setLinkKey(String linkKey)
	{
		this.linkKey = linkKey;
	}

	public String getLinkName()
	{
		return linkName;
	}

	public void setLinkName(String linkName)
	{
		this.linkName = linkName;
	}

	public byte getLinkStatus()
	{
		return linkStatus;
	}

	public void setLinkStatus(byte linkStatus)
	{
		this.linkStatus = linkStatus;
	}

	public String getServerAddress()
	{
		return serverAddress;
	}

	public void setServerAddress(String serverAddress)
	{
		this.serverAddress = serverAddress;
	}

	public int getServerPort()
	{
		return serverPort;
	}

	public void setServerPort(int serverPort)
	{
		this.serverPort = serverPort;
	}

	public boolean isLinkOptionValid()
	{
		return linkOptionValid;
	}

	public int getRetryTimes()
	{
		return retryTimes;
	}

	public long getNextCheckStamp()
	{
		return nextCheckStamp;
	}

	public void incRetryTimes()
	{
		retryTimes++;
		if (retryTimes <= 5)
			nextCheckStamp = System.currentTimeMillis() + retryTimes * 3600000l;
		else
			nextCheckStamp = System.currentTimeMillis() + retryTimes * 18000000l;
	}

	public void tryConnect()
	{
		linkOptionValid=true;
		client = new GMSocketClient();
		client.setServerAddress(this.getServerAddress());
		client.setServerPort(this.getServerPort());
		client.initialize();

//		List<MessageActionInfo> actionList = UcenterMessages.getMessageActionList();
//		if (actionList != null)
//		{
//			MessageActionInfo actionInfo;
//			for (int i = 0; i < actionList.size(); i++)
//			{
//				actionInfo = actionList.get(i);
//				client.addHandler(actionInfo.getCommandId(), actionInfo.getMessageClass(), actionInfo.getAction());
//			}
//		}
		
		client.tryConnect();
		if (client.isConnected())
		{
			retryTimes = 0;
			nextCheckStamp = 0;
			RQPreHandMessage msg = new RQPreHandMessage();
			client.sendMessage(msg);
		}
		else
			incRetryTimes();
	}

	public boolean isConnected()
	{
		return (client != null && client.isConnected());
	}

	public boolean sendMessage(AbstractMessage msg)
	{
		if (client != null && client.isConnected())
		{
			client.sendMessage(msg);
			return true;
		}
		return false;
	}

}
