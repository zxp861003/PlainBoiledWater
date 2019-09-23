package com.ucenter.persistence.provider.base;

import com.fire.persistence.dao.SimpleDaoFactory;
import com.fire.persistence.dao.SimpleDataProvider;
import com.ucenter.common.UcenterConst;

public abstract class ChargeBaseProvider extends SimpleDataProvider
{
	public static final String RESOURCE = "com/ucenter/persistence/dao/mysqlmap/Dao.xml";
	
	public ChargeBaseProvider()
	{
		super(UcenterConst.SUBSYSTEM);
	}

	@Override
	public void initFactory()
	{
		if (!SimpleDaoFactory.isExistDaoManager(getSystemKey()))
		{
			SimpleDaoFactory.addDaoManager(getSystemKey(), RESOURCE);
		}
	}

}
