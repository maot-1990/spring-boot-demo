package com.sjw.adaptor.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sjw.adaptor.config.ConfigBean;
import com.sjw.adaptor.enums.DataSourceEnum;
import com.sjw.adaptor.service.SwitchService;
import com.sjw.adaptor.util.CXFClientUtil;
import com.sjw.adaptor.util.HttpClientUtil;
import com.sjw.adaptor.util.RetData;

@RestController
@RequestMapping("/custCustomer")
public class SwitchController {
	
	private static Logger log = Logger.getLogger(SwitchController.class);
	@Autowired
	private ConfigBean configBean;
	@Autowired
	private SwitchService switchService;
	
	@RequestMapping(value="/notifyCust", produces={"application/json"})
	@ResponseBody
	public Object asyncNotify(HttpServletRequest request, HttpServletResponse respone){
		String context = request.getParameter("context");
		boolean isExist = false;
		Object result = "";
		try {
			log.info("银行密文：" + context);
			RetData retData = CXFClientUtil.callRemoteMethod(configBean.getDecryptionAddress(), context);
			log.info("银行明文：" + retData.toString());
			JSONObject josnObj = JSONObject.fromObject(retData.getRetBody());
			JSONObject transOutput = JSONObject.fromObject(josnObj.get("transOutput"));
			String fundAcc = transOutput.getString("fundAcc");
			String orderId = transOutput.getString("orderId"); 
			log.info("通知,存管号：" + fundAcc + ",订单：" + orderId);
			 
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("context", context);
			isExist = switchService.isExistMybatis(fundAcc, DataSourceEnum.lowDataSource.name());
			if(isExist){
				log.info("通知lowUrl:" + configBean.getLowUrl());
				result = HttpClientUtil.getMethod(configBean.getLowUrl(), paramMap, null);
				log.info("通知lowUrl成功:" + configBean.getLowUrl() + ",返回信息：" + result);
			}
			isExist = switchService.isExistMybatis(fundAcc, DataSourceEnum.highDataSource.name());
			if(isExist){
				log.info("通知highUrl:" + configBean.getBestUrl());
				result = HttpClientUtil.getMethod(configBean.getBestUrl(), paramMap, null);
				log.info("通知highUrl成功:" + configBean.getBestUrl() + ",返回信息：" + result);
			}
		} catch (Exception e) {
			log.error("通知异常：" + e.getMessage());
			result = "error";
			e.printStackTrace();
		}
		
		return result;
	}
	
}
