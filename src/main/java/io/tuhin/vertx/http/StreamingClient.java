package io.tuhin.vertx.http;


import io.tuhin.vertx.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.file.AsyncFile;
import io.vertx.core.file.FileProps;
import io.vertx.core.file.FileSystem;
import io.vertx.core.file.OpenOptions;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.streams.Pump;

/**
 * @author Tuhin Gupta
 * April 2016
 * 
 * Example of vertx.io HTTP/1.0 non-chunked client. 
 * This requires that content-length be sent with the request, so that server know when the request is complete.  
 */
public class StreamingClient extends AbstractVerticle {
	
	
	  static String FILE_NAME = null;

	  
	  public static void main(String[] args) {
		  
		FILE_NAME = "simple.txt";  
	    Runner.runExample(StreamingClient.class);
	  }

	
	  public void start() {

		  HttpClientRequest request = vertx.createHttpClient(new HttpClientOptions()).put(9977, "localhost", "", resp -> {
		      System.out.println("Response " + resp.statusCode());
		      System.out.println("Response " + resp.statusMessage());
		      System.exit(0);
		    });


		    FileSystem fs = vertx.fileSystem();

		    fs.props(FILE_NAME, ares -> {
		      FileProps props = ares.result();
		      System.out.println("props is " + props);
		      
		      //this is an example of un-chunked stream over HTTP, 
		      //hence set content-length header.

		      long size = props.size();
		      request.headers().set("content-length", String.valueOf(size));
		      
		      fs.open(FILE_NAME, new OpenOptions(), ares2 -> {
		        AsyncFile file = ares2.result();
		        Pump pump = Pump.pump(file, request);
		        file.endHandler(v -> {
		        	request.end();
		        });
		        pump.start();
		      });
		    });

		  
	  }
	 
	}
