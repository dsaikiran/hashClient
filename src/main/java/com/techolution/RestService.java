package com.techolution;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import argo.jdom.JdomParser;
import argo.jdom.JsonNode;
import argo.jdom.JsonRootNode;
import argo.saj.InvalidSyntaxException;


/**
 * This class uses map broker service by sending rest api calls to add data 
 * @author saikiran
 *
 */
@Service
public class RestService {
	String uri = "https://hashbroker-spicier-verrucoseness.cfapps.io/TechoHash/{bindingId}/{key}";
	String username = "dinesh";
	String password = "dinesh";
	String bindingId = "techohashclinet";
	RestTemplate restTemplate = new RestTemplate();
	HttpHeaders headers;

 	public void init(String vcap_services) {
		 ParameterizedTypeReference<String> ptr= new ParameterizedTypeReference<String>() {};
		    RestTemplate restTemplate= new RestTemplate();
		    if (vcap_services != null && vcap_services.length() > 0) {
		        JsonRootNode root=null;;
				try {
					root = new JdomParser().parse(vcap_services);
					JsonNode techohashmapNode = root.getNode("techohashmap");
			        JsonNode credentials = techohashmapNode.getNode(0).getNode("credentials");
			        username=credentials.getStringValue("username");
			        password=credentials.getStringValue("password");
			        bindingId=credentials.getStringValue("bindingId");
			        uri=credentials.getStringValue("uri");
				} catch (InvalidSyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
 	}
	
	
	public void put(String key,String value) {
		headers = Application.createHeaders(username, password);
		HttpEntity<String> entity = new HttpEntity<String>(value,headers);
		Map<String, String> params = new HashMap<String, String>();
		params.put("bindingId", bindingId);
	    params.put("key", key);
		//restTemplate.put(uri, entity,params);
		restTemplate.exchange(uri, HttpMethod.POST, entity,String.class, params);

	}
	
	public String get(String key) {
		 headers = Application.createHeaders(username, password);
		 ParameterizedTypeReference<String> ptr= new ParameterizedTypeReference<String>() {};
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		Map<String, String> params = new HashMap<String, String>();
		params.put("bindingId", bindingId);
	    params.put("key", key);
		return restTemplate.exchange(uri ,
			    HttpMethod.GET, entity, ptr,params).getBody(); 
	}
	
	public String delete(String key) {
		headers = Application.createHeaders(username, password);
		ParameterizedTypeReference<String> ptr= new ParameterizedTypeReference<String>() {};
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		Map<String, String> params = new HashMap<String, String>();
		params.put("bindingId", bindingId);
	    params.put("key", key);
	    return restTemplate.exchange(uri ,
			    HttpMethod.GET, entity, ptr,params).getBody();  
	}

	public static HttpHeaders createHeaders(String username, String password) {
		return new HttpHeaders() {
			{
				String auth = username + ":" + password;
				byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encodedAuth);
				set("Authorization", authHeader);
			}
		};
	}

}
