package com.sjw.adaptor.tomcat;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.context.embedded.EmbeddedServletContainer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatConfig extends TomcatEmbeddedServletContainerFactory{

	@Override
	public EmbeddedServletContainer getEmbeddedServletContainer(
			ServletContextInitializer... initializers) {
		return super.getEmbeddedServletContainer(initializers);
	}

	@Override
	protected void customizeConnector(Connector connector) {
		super.customizeConnector(connector);
		Http11NioProtocol protocol = (Http11NioProtocol)connector.getProtocolHandler();  
        //设置最大连接数  
        protocol.setMaxConnections(2000);  
        //设置最大线程数  
        protocol.setMaxThreads(2000);  
        protocol.setConnectionTimeout(30000);
        protocol.setMinSpareThreads(20);
        protocol.setPort(8666);
	}
	
}
