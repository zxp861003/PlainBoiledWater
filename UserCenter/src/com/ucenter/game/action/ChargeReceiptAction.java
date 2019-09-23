package com.ucenter.game.action;

import kilim.Pausable;

import com.fire.net.channelobj.RemoteNode;
import com.fire.net.message.handler.AbstractSessionAction;
import com.ucenter.game.message.RSChargeReceiptMessage;

public class ChargeReceiptAction extends AbstractSessionAction<RSChargeReceiptMessage>
{

	@Override
	public void processMessage(RSChargeReceiptMessage message, RemoteNode p) throws Pausable
	{
		System.out.println("Process message " + message);

	}

	private ChargeReceiptAction()
	{

	}

	// *************************************
	private final static ChargeReceiptAction _instance = new ChargeReceiptAction();

	public final static ChargeReceiptAction getInstance()
	{
		return _instance;
	}

}
