package com.sjw.adaptor.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sjw.adaptor.dao.high.HighUserInfoDao;
import com.sjw.adaptor.dao.low.LowUserInfoDao;
import com.sjw.adaptor.enums.DataSourceEnum;
import com.sjw.adaptor.service.SwitchService;

@Service
public class SwitchServiceImpl implements SwitchService{

	private static Logger log = Logger.getLogger(SwitchServiceImpl.class);
	@Resource
	private JdbcTemplate highJdbcTemplate;
	@Resource
	private JdbcTemplate lowJdbcTemplate;
	@Resource
	private HighUserInfoDao highUserInfoDao;
	@Resource
	private LowUserInfoDao lowUserInfoDao;
	
	/**
	 * 采用jdbcTemplate获取双数据源数据
	 */
	@Override
	public boolean isExist(String fundAcc, String dataSource) {
		String sql = "select count(1) as COUNT from (select a.fund_acc from syst_logon_user a "
				+ "where a.fund_acc = ? "
				+ "union all select b.account_no from clea_plat_sett_acc_info b "
				+ "where b.account_no = ?)";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(DataSourceEnum.highDataSource.name().equals(dataSource)){
			resultMap = highJdbcTemplate.queryForMap(sql, new Object[]{fundAcc, fundAcc});
		}else if(DataSourceEnum.lowDataSource.name().equals(dataSource)){
			resultMap = lowJdbcTemplate.queryForMap(sql, new Object[]{fundAcc, fundAcc});
		}
		return Integer.valueOf(resultMap.get("COUNT").toString()).intValue() > 0 ? true : false;
	}

	/**
	 * 采用mybatis获取双数据源数据
	 */
	@Transactional
	@Override
	public boolean isExistMybatis(String fundAcc, String dataSource) {
		int count = 0;
		if(DataSourceEnum.highDataSource.name().equals(dataSource)){
			count = highUserInfoDao.getUserInfoCount(fundAcc);
		}else if(DataSourceEnum.lowDataSource.name().equals(dataSource)){
			count = lowUserInfoDao.getUserInfoCount(fundAcc);
		}
		return count > 0 ? true : false;
	}
 	
	
	
}
