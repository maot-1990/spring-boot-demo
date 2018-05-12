package com.sjw.adaptor.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Configuration
@ConfigurationProperties(prefix="pre")
@PropertySource(value={"classpath:properties/config.properties"})
@Component
public class ConfigBean {
	
	private String lowUrl;
	private String bestUrl;
	private String decryptionAddress;
	
	public String getLowUrl() {
		return lowUrl;
	}
	public void setLowUrl(String lowUrl) {
		this.lowUrl = lowUrl;
	}
	public String getBestUrl() {
		return bestUrl;
	}
	public void setBestUrl(String bestUrl) {
		this.bestUrl = bestUrl;
	}
	public String getDecryptionAddress() {
		return decryptionAddress;
	}
	public void setDecryptionAddress(String decryptionAddress) {
		this.decryptionAddress = decryptionAddress;
	}
	@Override
	public String toString() {
		return "ConfigBean [lowUrl=" + lowUrl + ", bestUrl=" + bestUrl
				+ ", decryptionAddress=" + decryptionAddress + "]";
	}
	
}
