package com.flumetest;
 
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
 
public class JsonInterceptor implements Interceptor {
 
  private static final Logger LOG = Logger.getLogger(JsonInterceptor.class);
 
  // private means that only Builder can build me.
  private JsonInterceptor() {}
 
  @Override
  public void initialize() {}
 
  @Override
  public Event intercept(Event event) {
 
    Map<String, String> headers = event.getHeaders();
 
    String body = new String(event.getBody());
    JSONParser jsonParser = new JSONParser();
    JSONObject jsonObject = null;
	try {
		jsonObject = (JSONObject) jsonParser.parse(body);
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  ContainerFactory containerFactory = new ContainerFactory(){
	    public List creatArrayContainer() {
	      return new LinkedList();
	    }

	    public Map createObjectContainer() {
	      return new LinkedHashMap();
	    }
	                        
	  };
	                
	  try{
	    Map json = (Map)jsonParser.parse(body, containerFactory);
	    Iterator iter = json.entrySet().iterator();
	    while(iter.hasNext()){
	      Map.Entry entry = (Map.Entry)iter.next();
	      String KeyName=entry.getKey().toString();
	      String KeyValue=entry.getValue().toString();
	      
	      headers.put(KeyName, KeyValue);
	    }
	                        
	    
	  }
	  catch(ParseException pe){
	    System.out.println(pe);
	  }
  
    return event;
  }
 
  @Override
  public List<Event> intercept(List<Event> events) {
    for (Event event:events) {
      intercept(event);
    }
    return events;
  }
 
  @Override
  public void close() {}
 
  public static class Builder implements Interceptor.Builder {
 
    @Override
    public Interceptor build() {
      return new JsonInterceptor();
    }
 
    @Override
    public void configure(Context context) {}
  }
}
