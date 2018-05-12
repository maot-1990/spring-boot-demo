package com.sjw.adaptor.util;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;

/**
 * webservice restful 调用工具类
 * Created by cnpaya on 2015/3/6.
 */
public class CXFClientUtil {

    /**
     * 以json格式提交 vof
     * @param remoteURL
     * @param vo
     * @return
     */
    public static RetData callRemoteMethod(String remoteURL, Object vo) {
        RetData retData = new RetData();
        WebClient client = WebClient.create(remoteURL);
        client.encoding("application/json;charset=UTF-8");
        String result = client.accept(MediaType.APPLICATION_JSON).post(vo, String.class);
        try {
            retData = JSONObject.toObj(result, RetData.class);
        } catch (Exception e) {
        }
        return retData;
    }
    /**
     *  重载callRemoteMethod方法 加入请求次数参数
     *
     * @author cjh 
     * @param remoteURL
     * @param vo
     * @param maxRetryCount
     * @return
     */
    public static RetData callRemoteMethod(String remoteURL, Object vo, int maxRetryCount) { 
        if (maxRetryCount == 0) return callRemoteMethod(remoteURL,vo); 
        boolean hasTimeoutException = true; 
        RetData retData = null; 
        while(hasTimeoutException && maxRetryCount > 0) { 
           try { 
              retData = callRemoteMethod(remoteURL,vo); 
              if (retData != null) { 
                 hasTimeoutException = false; 
              } 
           } catch (Exception e) { 
              System.out.println("*********输出异常，重复次数*************," + maxRetryCount); 
              --maxRetryCount; 
           } 
        } 
        return retData; 
     }
}
