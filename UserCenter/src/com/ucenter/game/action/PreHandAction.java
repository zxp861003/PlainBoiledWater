package com.ucenter.game.action;

import kilim.Pausable;

import com.fire.net.channelobj.RemoteNode;
import com.fire.net.message.handler.AbstractSessionAction;
import com.ucenter.game.message.RSPreHandMessage;

public class PreHandAction extends AbstractSessionAction<RSPreHandMessage>
{

	@Override
	public void processMessage(RSPreHandMessage message, RemoteNode p) throws Pausable
	{
		System.out.println("Process message " + message);

	}

	private PreHandAction()
	{

	}

	// *************************************
	private final static PreHandAction _instance = new PreHandAction();

	public final static PreHandAction getInstance()
	{
		return _instance;
	}

}
