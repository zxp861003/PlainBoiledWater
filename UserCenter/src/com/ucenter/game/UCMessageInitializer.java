package com.ucenter.game;

import com.fire.net.server.msgInitializer.AbstractMessageInitializer;

public class UCMessageInitializer extends AbstractMessageInitializer
{
	public UCMessageInitializer()
	{
	}

	@Override
	protected void initMessages()
	{
		register();
	}

	private void register()
	{
		// 注册服务器信息消息
//		addHandler(UcenterMessages.RSMSG_PREHAND,RSPreHandMessage.class, PreHandAction.getInstance());
//		addHandler(UcenterMessages.RSMSG_CHARGE_RECEIPT,RSChargeReceiptMessage.class, ChargeReceiptAction.getInstance());
//		addHandler(UcenterMessages.E_GA_CHECK_TOKEN,GACheckTokenMessage.class, GACheckTokenAction.getInstance());

	}

}
