package com.sjw.adaptor.entity.high;

import java.io.Serializable;
import java.util.Date;

//@TableName(name="SYST_TRADE_LOG")
public class HighUserInfo implements Serializable {
	
	private String id;
	private java.util.Date createTime;
	private String createUser;
	private String updOpname;
	private Date updateTime;
	private String bussno;
	private Date dealTime;
	private String dealer;
	private String errLog;
	private String orderid;
	private String outputtime;
	private String tradeLog;
	private String transType;
	private String type;
	
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id = id;
	}
	public java.util.Date getCreateTime(){
		return createTime;
	}
	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}
	public String getCreateUser(){
		return createUser;
	}
	public void setCreateUser(String createUser){
		this.createUser = createUser;
	}
	public String getUpdOpname(){
		return updOpname;
	}
	public void setUpdOpname(String updOpname){
		this.updOpname = updOpname;
	}
	public java.util.Date getUpdateTime(){
		return updateTime;
	}
	public void setUpdateTime(java.util.Date updateTime){
		this.updateTime = updateTime;
	}
	public String getBussno(){
		return bussno;
	}
	public void setBussno(String bussno){
		this.bussno = bussno;
	}
	public java.util.Date getDealTime(){
		return dealTime;
	}
	public void setDealTime(java.util.Date dealTime){
		this.dealTime = dealTime;
	}
	public String getDealer(){
		return dealer;
	}
	public void setDealer(String dealer){
		this.dealer = dealer;
	}
	public String getErrLog(){
		return errLog;
	}
	public void setErrLog(String errLog){
		this.errLog = errLog;
	}
	public String getOrderid(){
		return orderid;
	}
	public void setOrderid(String orderid){
		this.orderid = orderid;
	}
	public String getOutputtime(){
		return outputtime;
	}
	public void setOutputtime(String outputtime){
		this.outputtime = outputtime;
	}
	public String getTradeLog(){
		return tradeLog;
	}
	public void setTradeLog(String tradeLog){
		this.tradeLog = tradeLog;
	}
	public String getTransType(){
		return transType;
	}
	public void setTransType(String transType){
		this.transType = transType;
	}
	public String getType(){
		return type;
	}
	public void setType(String type){
		this.type = type;
	}
}
