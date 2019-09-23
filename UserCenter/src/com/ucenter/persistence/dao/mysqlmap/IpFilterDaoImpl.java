package com.ucenter.persistence.dao.mysqlmap;

import com.fire.persistence.dao.entitydao.EntityDaoImpl;
import com.ibatis.dao.client.DaoManager;
import com.ucenter.persistence.dao.IIpFilterDao;

public class IpFilterDaoImpl extends EntityDaoImpl implements IIpFilterDao
{

	public IpFilterDaoImpl(DaoManager daoManager)
	{
		super(daoManager);
	}

	protected void init()
	{
		dataName = "IpFilter";
		tableIdName = "filter_id";
	}
}