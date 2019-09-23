package com.ucenter.service;

import java.util.concurrent.LinkedBlockingQueue;

import com.ucenter.game.message.RQChargeOrderMessage;
import com.ucenter.game.net.LinkManager;
import com.ucenter.persistence.dbbean.ChargeOrder;
import com.ucenter.persistence.provider.ChargeOrderDataProvider;

public class ChargeOrderService
{
	private static LinkedBlockingQueue<ChargeOrder> orderQueue= new LinkedBlockingQueue<ChargeOrder>();

	public ChargeOrderService()
	{
	}

	public static ChargeOrder appendOrder(ChargeOrder order)
	{
		if (order == null)
			return order;

		ChargeOrderDataProvider provider = ChargeOrderDataProvider.getInstance();
		try
		{
			orderQueue.put(order);
			return provider.saveOrder(order);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return order;
	}

	public static ChargeOrder popOrder()
	{
		return orderQueue.isEmpty() ? null : orderQueue.poll();
	}
	
	public static void closeOrder(ChargeOrder order)
	{
		if (order == null)
			return;

		order.setStatus((byte)-1);
		ChargeOrderDataProvider provider = ChargeOrderDataProvider.getInstance();
		try
		{
			provider.saveOrder(order);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void frameUpdateOrder()
	{
		int count = 0;
		ChargeOrder order;
		do
		{
			count++;
			order = ChargeOrderService.popOrder();
			if (order != null)
			{
				RQChargeOrderMessage msg = new RQChargeOrderMessage();
				msg.setOrderId(order.getOrderId());
				msg.setChannelId(order.getChannelId());
				msg.setSubChannel(order.getSubChannel());
				msg.setAmount(order.getAmount());
				msg.setPayType(order.getPayType());
				msg.setCurrency(order.getCurrency());
				msg.setAccount(order.getAccount());
				msg.setReceiptId(order.getReceiptId());
				msg.setMarkMsg(order.getMarkMsg());
				boolean suc = LinkManager.sendRequest(msg, order.getGameId(), order.getGameArea());
				if (suc)
					ChargeOrderService.closeOrder(order);
				
				try
				{
					Thread.sleep(50);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		while (order != null && count < 100);
	}

}
