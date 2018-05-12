package com.sjw.adaptor.util;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class HttpClientUtil {
	
	
	public static final Logger log = Logger.getLogger(HttpClientUtil.class);
	
	static RequestConfig requestConfig;
	
	static CookieStore cookieStore = null;
	
	static {
		requestConfig = RequestConfig.custom().setConnectTimeout(30000).setConnectionRequestTimeout(30000).setSocketTimeout(600000).build();
	}
	
	/**
	 * get方式提交参数
	 * 
	 * @param url
	 *            ：查询请求地址
	 * @param param
	 *            ：查询参数
	 * @param cls
	 *            ：返回转换json对象
	 */
	public static Object getMethod(String url, Map<String, Object> paramMap, Class cls) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		StringBuffer sb = new StringBuffer();
		try {
			if (paramMap != null && !paramMap.isEmpty()) {
				for (String key : paramMap.keySet()) {
					if (paramMap.get(key) instanceof List) {
						List<?> list = (List<?>) paramMap.get(key);
						if (paramMap.get(key) != null && !"".equals(paramMap.get(key))) {
							sb.append(key + "=" + URLEncoder.encode(Arrays.toString(list.toArray()), "utf-8") + "&");
						}
					}
					else {
						if (paramMap.get(key) != null && !"".equals(paramMap.get(key))) {
							sb.append(key + "=" + URLEncoder.encode((String) paramMap.get(key), "utf-8") + "&");
						}
					}
				}
			}
			HttpGet httpGet = new HttpGet(url + (sb.length() > 0 ? "?" + sb.substring(0, sb.length() - 1) : ""));
			httpGet.setConfig(requestConfig);
			CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
			String json = EntityUtils.toString(httpResponse.getEntity());
			httpClient.close();
			
			return json;
			
		}catch (Exception e) {
			log.error("发送请求失败():" + e.getMessage());
		}
		finally {
			try {
				httpClient.close();
			}
			catch (IOException e) {
				log.error("地址" + url + "***关闭连接失败：" + e.getMessage());
			}
		}
		return sb;
	}
	
	public static void main(String[] args) {
		System.out.println("执行调用开始");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("context", "123");
		System.out.println(HttpClientUtil.getMethod("http://localhost:8086/switch/asyncNotify", paramMap, null));
		System.out.println("执行调用结束");
	}
}
