package com.cjam.springboot.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class NetClient {

	private static Logger logger = LoggerFactory.getLogger(NetClient.class);
	private static int TIMEOUT = 10000;

	public static JSONObject httpJSONResponse(String post_url,
			Map<String, String> parameters, String requestMethod) {
		long t1 = System.currentTimeMillis();
		String result = "";
		HttpURLConnection connection = null;
		BufferedReader reader = null;
		try {
			URL url = new URL(getFormattedUrl(post_url, parameters));// 创建连接
			connection = (HttpURLConnection) setURLConnectionProperties(url,requestMethod);
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			String lines;
			StringBuffer sb = new StringBuffer("");
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes(), "UTF-8");
				sb.append(lines);
			}
			result = sb.toString();
			long t2 = System.currentTimeMillis();
			return JSONObject.parseObject(result);
		} catch (IOException e) {
			logger.error("获取数据失败  \nurl = \"{}\" \nmethod = \"GET\" e={}",post_url, e);
			return null;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.error("BufferedReader close error, reason:{}",e);
				}
			}
		}
	}

	/**
	 * 通过get menthod进行调用
	 * @param url 请求地址
	 * @return JSONObject
	 */
	public static JSONObject getJSONResponse(String url) {
		return httpJSONResponse(url, null, "GET");
	}
	/**
	 * 通过get menthod进行调用 可参数传入。
	 * @param url 请求地址
	 * @param parameters 参数map
	 * @return JSONObject
	 */
	public static JSONObject getJSONResponse(String url,
			Map<String, String> parameters) {
		return httpJSONResponse(url, parameters, "GET");
	}
	/**
	 * 通过post menthod进行调用
	 * @param url 请求地址
	 * @return JSONObject
	 */
	public static JSONObject postJSONResponse(String url) {
		return httpJSONResponse(url, null, "POST");

	}
	
	/**
	 * 通过post menthod进行调用 可传入参数
	 * @param url 请求地址
	 * @param parameters 参数map
	 * @return JSONObject
	 */
	public static JSONObject postJSONResponse(String url,Map<String, String> parameters) {
		return httpJSONResponse(url, parameters, "POST");

	}

	/**
	 * 想指定url发送post请求 
	 * @param post_url
	 * @param parameters 参数列表, 现支持String类型 和Map<String,String> 集合类型 
	 * @return String
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	
	public static String postResponse(String post_url,
			Object parameters)throws Exception {
		long t1 = System.currentTimeMillis();
		String result = "";
		String jsonText="";
		HttpURLConnection connection = null;
		BufferedReader reader = null;
		try {
			URL url = new URL(post_url);// 创建连接
			connection = (HttpURLConnection) setURLConnectionProperties(url,"POST");
			OutputStreamWriter out=new OutputStreamWriter(connection.getOutputStream(),"UTF-8");
			if(parameters instanceof String) {
				jsonText=(String)parameters;
			} else if (parameters instanceof JSONObject) {
                jsonText=JSON.toJSONString(parameters);
            } else if(parameters instanceof Map){
				Map<String,String> paraMap=(Map<String,String>)parameters;
				JSONObject jsonObject=new JSONObject();
				jsonObject.putAll(paraMap);
				jsonText=getFormatted(paraMap);
			}
			out.write(jsonText);
			out.flush();
			out.close();
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			String lines;
			StringBuffer sb = new StringBuffer("");
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes(), "UTF-8");
				sb.append(lines);
			}
			result = sb.toString();
			long t2 = System.currentTimeMillis();
			return result;
		} catch (IOException e) {
			logger.error("获取数据失败  \nurl = \"{}\" \nmethod = \"GET\" e={}",post_url, e);
			return null;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.error("BufferedReader close error, reason:{}",e);

				}
			}
		}
	}
	private static String getFormattedUrl(String baseUrl,Map<String, String> parameters) {
		String returnValue = baseUrl;
		if (parameters != null && parameters.size() > 0) {
			returnValue = returnValue + "&";
			for (String key : parameters.keySet()) {
				returnValue = String.format("%s%s=%s&", returnValue, key,parameters.get(key));
			}
		}
		return StringUtils.removeEnd(returnValue, "&");
	}
	
	private static String getFormatted(Map<String, String> parameters) {
		String returnValue ="";
		if (parameters != null && parameters.size() > 0) {
			for (String key : parameters.keySet()) {
				returnValue = String.format("%s%s=%s&", returnValue, key,parameters.get(key));
			}
		}
		return StringUtils.removeEnd(returnValue, "&");
	}

	/**
	 * 设置HttpURLConnection参数<br>
	 * <功能详细描述><br>
	 * 
	 * @param url
	 *            请求处理的地址
	 * @return 后台与服务器之间的通信连接
	 * @throws IOException
	 * @throws ProtocolException
	 * @see [类、类#方法、类#成员]
	 */
	private static HttpURLConnection setURLConnectionProperties(URL url,String requestMethod)throws IOException, ProtocolException {
		HttpURLConnection httpUrlConnection;
		URLConnection rulConnection = url.openConnection();// 此处的urlConnection对象实际上是根据URL的
		httpUrlConnection = (HttpURLConnection) rulConnection;
		// 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在
		// http正文内，因此需要设为true, 默认情况下是false;
		httpUrlConnection.setDoOutput(true);
		// 设置是否从httpUrlConnection读入，默认情况下是true;
		httpUrlConnection.setDoInput(true);
		// Post 请求不能使用缓存
		httpUrlConnection.setUseCaches(false);
		// 设定传送的内容类型是可序列化的java对象
		// (如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
		// httpUrlConnection.setRequestProperty("Content-type",
		// "application/x-java-serialized-object");
		//
		//httpUrlConnection.setRequestProperty("Content-type", "application/json");
		// 设定请求的方法为"POST"，默认是GET
		httpUrlConnection.setRequestMethod(requestMethod);
		// connection.setInstanceFollowRedirects(true);
		httpUrlConnection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
		try {
			// 连接，从上述至此的配置必须要在connect之前完成，
			httpUrlConnection.connect();
			httpUrlConnection.setConnectTimeout(TIMEOUT);
			httpUrlConnection.setReadTimeout(TIMEOUT);
		} catch (ConnectException e) {
			if (e.getMessage().equals("Connection refused: connect")) {
				logger.error("连接超时  \nurl = \"{}\" \nmethod = \"GET\" e={}",url, e);
				throw e;
			}
		}
		return httpUrlConnection;
	}

}
