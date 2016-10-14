package io.tuhin.vertx.http;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import io.tuhin.kafka.simpleproducer.KafkaStreamProducer;
import io.tuhin.vertx.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;


/**
 * @author Tuhin Gupta
 * April 2016
 * 
 * Vertx.io server to recieve steam over HTTP request.
 * This is un-chunked version so content-length is received from client. 
 * See that header has content-length value set.
 * Example: content-length : 8766456
 */
public class StreamingServer extends AbstractVerticle {
	

	  public static void main(String[] args) 
	  {
	  
		  Runner.runExample(StreamingServer.class);
	  
	  }

	  
	  static String pattern1 = "{";
	  static String pattern2 = "}";
		
	  static Pattern p = Pattern.compile(Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2));

	  /* (non-Javadoc)
	 * @see io.vertx.core.AbstractVerticle#start()
	 */
	@Override
	  public void start() throws Exception {
		
		System.out.println("Server started at localhost:9977 ...");
		
		
		
	    vertx.createHttpServer().requestHandler(new Handler<HttpServerRequest>() {
	    	
	    	long byteswritten = 0;
	    	
			
			

			@Override
			public void handle(HttpServerRequest request) {

				
				KafkaStreamProducer ksp = new KafkaStreamProducer();
				
				
				request.handler(new Handler<Buffer>(){


					@Override
					public void handle(Buffer buffer) 
					{
						
						Scanner sc = new Scanner(buffer.toString());
						Integer intObj;
						
						while (sc.hasNext()) {
							
							intObj = Integer.parseInt(sc.next());
							
							try{
								ksp.sendMessages("appA", intObj.toString());
							}catch(Exception ex){
								ex.printStackTrace();
							}
							
						}
						
					}
					
				
				});//request.handler
				
				request.endHandler(new Handler<Void>() {

					@Override
					public void handle(Void event) {
						
						System.out.println("...end handler.");
						
						
						
						try {
							
							
							request.response().setStatusCode(202).setStatusMessage("bytes written " + byteswritten);
							request.response().end();
							
							
						
						} catch (Exception e) {
							
							e.printStackTrace();
						}finally{

							
							byteswritten = 0;
						}
						
						
						
					}//handle
				
				});
				
				
			}
  	
	    	
	    }).listen(9977);
	  }

}
