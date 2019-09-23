package com.ucenter.game;


public class UcenterMessages
{
	public final static boolean BIGENDIAN=false;
	
	public final static int RQMSG_PREHAND = 102;
	public final static int RQMSG_CHARGE_ORDER = 34400;
	public final static int E_GA_CHECK_TOKEN = 196614;
	
	public final static int RSMSG_PREHAND = 102;
	public final static int RSMSG_CHARGE_RECEIPT = 34400;
	public final static int E_AG_CHECK_TOKEN = 196615;
	
	//Message+Action
//	private static List<MessageActionInfo> actionList = new CopyOnWriteArrayList<MessageActionInfo>();
//
//	public static List<MessageActionInfo> getMessageActionList()
//	{
//		return actionList;
//	}
//
//	public static void init()
//	{
//		actionList.add(new MessageActionInfo(RSMSG_PREHAND, RSPreHandMessage.class, PreHandAction.getInstance()));
//		actionList.add(new MessageActionInfo(RSMSG_CHARGE_RECEIPT, RSChargeReceiptMessage.class, ChargeReceiptAction.getInstance()));
//		actionList.add(new MessageActionInfo(E_GA_CHECK_TOKEN, GACheckTokenMessage.class, GACheckTokenAction.getInstance()));
//	}

}
