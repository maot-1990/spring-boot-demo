package com.sjw.adaptor.util;

import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

/**
 * 
 *  类            名:     返回数据  （RetData）
 *  修 改 记 录:     // 修改历史记录，包括修改日期、修改者及修改内容
 *  版 权 所 有:     版权所有(C)2010-2014
 *  公             司:     深圳华夏通宝信息技术有限公司
 *  @version    V1.0
 *  @date       2015年2月8日
 *  @author     qinlinhai
 *
 */
@SuppressWarnings("restriction")
@XmlRootElement(name = "RetData")
public class RetData implements Serializable{
	
	private static final long serialVersionUID = 1195465963903865240L;
	
	/**状态码*/
    private String retCode;
    /**错误信息*/
    private String errMsg;
    /**交易类型*/
    private String transType;
    /**交易日期*/
    private String transDate;
    /**交易时间*/
    private String transTime;
    /**交易流水号*/
    private String transFlowNo;
    /**数据体*/
    private Object retBody;
    
    public RetData() {
    }

    public RetData(String retCode, String errMsg) {
        this.retCode = retCode;
        this.errMsg = errMsg;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public String getTransTime() {
        return transTime;
    }

    public void setTransTime(String transTime) {
        this.transTime = transTime;
    }

    public String getTransFlowNo() {
        return transFlowNo;
    }

    public void setTransFlowNo(String transFlowNo) {
        this.transFlowNo = transFlowNo;
    }

    public Object getRetBody() {
        return retBody;
    }

    public void setRetBody(Object retBody) {
        this.retBody = retBody;
    }

	@Override
	public String toString() {
		return "RetData [retCode=" + retCode + ", errMsg=" + errMsg
				+ ", transType=" + transType + ", transDate=" + transDate
				+ ", transTime=" + transTime + ", transFlowNo=" + transFlowNo
				+ ", retBody=" + retBody + "]";
	}
    
}
