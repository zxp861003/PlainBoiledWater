package com.ucenter.job;

import com.fire.task.Task;
import com.ucenter.service.ChargeOrderService;

public class OrderScheduleCheckTask implements Task
{

	@Override
	public void run()
	{
		ChargeOrderService.frameUpdateOrder();
	}
}
