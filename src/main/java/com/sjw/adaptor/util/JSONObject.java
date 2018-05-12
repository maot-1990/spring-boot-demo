package com.sjw.adaptor.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import org.apache.log4j.Logger;

import java.util.List;

public class JSONObject {
	
	private static final Logger logger = Logger.getLogger(JSONObject.class);
	
	private static final ObjectMapper mapper = new ObjectMapper();
	private JsonNode jsonNode;
	private JSONObject(){};
	public static JSONObject fromObject(String json) throws Exception{
		JSONObject obj = new JSONObject();
		obj.jsonNode = mapper.readTree(json);
		return obj;
	}
	public boolean containsKey(String key){
		return jsonNode.hasNonNull(key);
	}
	public JSONObject getJSONObject(String key){
		JSONObject obj = new JSONObject();
		JsonNode node = jsonNode.get(key);
		obj.jsonNode = node;
		return obj;
	}
	public String getString(String key){
		if(containsKey(key)){
			return jsonNode.get(key).textValue();
		}
		return null;
	}
	
	public JsonNode get(String key){
		if(containsKey(key)){
			return jsonNode.get(key);
		}
		return null;
	}
	
	public <T> List<T> getList(String key,Class<T> clazz) throws Exception{
		if(containsKey(key)){
			JsonParser userAMnFrzePyParamList = jsonNode.get(key).traverse();
	        CollectionType valueType = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
	        return mapper.readValue(userAMnFrzePyParamList, valueType);
		}
		return null;
	} 
	
	public <T> T toBean(String key,Class<T> clazz)throws Exception{
		if(containsKey(key)){
			JsonParser parser = jsonNode.get(key).traverse();
			return mapper.readValue(parser, clazz);
		}
		return null;
	}
	
	public static <T> T toBean(JSONObject obj,Class<T> clazz)throws Exception{
		JsonParser parser = obj.jsonNode.traverse();
		return mapper.readValue(parser, clazz);
	}
	
	public static String toString(Object obj){
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			logger.error("Json to String error", e);
		}
		return null;
	}
	
	
	public static <T> T toObj(String str, Class<T> clazz){
		try {
			return mapper.readValue(str, clazz);
		} catch (Exception e) {
			logger.error("String to Object error", e);
		} 
		return null;
	}
	
	public static String objToStr(Object obj){
		return net.sf.json.JSONObject.fromObject(obj).toString();
	}
	
	public static <T> Object StrToObj(String str, Class<T> cls){
		return net.sf.json.JSONObject.toBean(net.sf.json.JSONObject.fromObject(str), cls);
	}
}
