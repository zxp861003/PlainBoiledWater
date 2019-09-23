package com.ucenter.job;

import com.fire.task.Task;
import com.ucenter.game.net.LinkManager;

public class LinkScheduleCheckTask implements Task
{

	@Override
	public void run()
	{
		LinkManager.frameUpateLinks();
	}
	
	

}
