package com.ucenter.persistence.dao.mysqlmap;

import com.fire.persistence.dao.entitydao.EntityDaoImpl;
import com.ibatis.dao.client.DaoManager;
import com.ucenter.persistence.dao.IChargeOrderDao;

public class ChargeOrderDaoImpl extends EntityDaoImpl implements IChargeOrderDao
{

	public ChargeOrderDaoImpl(DaoManager daoManager)
	{
		super(daoManager);
	}

	protected void init()
	{
		dataName = "ChargeOrder";
		tableIdName = "order_id";
	}

}