package com.ucenter.job;

import com.fire.task.Task;
import com.ucenter.service.IpFilterCache;

public class ServiceDelayInitializeTask implements Task
{

	@Override
	public void run()
	{
		System.out.println("ServiceDelayInitializeTask");
		IpFilterCache.reloadGameFilter();
	}

}
