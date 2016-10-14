package io.tuhin.kafka.simpleconsumer;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

/**
 * @author  Tuhin Gupta
 * @since September 2016
 */

public class KafkaStreamConsumer extends Thread{
	
	
	   final static String TOPIC = "appA";
	   ConsumerConnector consumerConnector;


	    public static void main(String[] argv) throws UnsupportedEncodingException {
	    	KafkaStreamConsumer helloKafkaConsumer = new KafkaStreamConsumer();
	        helloKafkaConsumer.start();
	        
	    }

	    public KafkaStreamConsumer(){
	        Properties properties = new Properties();
	       // properties.put("zookeeper.connect","10.24.40.81:2181");
	        properties.put("zookeeper.connect","127.0.0.1:2181");
	        properties.put("zookeeper.sync.time.ms", "1");
	        properties.put("auto.commit.interval.ms", "1");
	        properties.put("auto.offset.reset", "smallest");
	        properties.put("group.id","test_group");
	        ConsumerConfig consumerConfig = new ConsumerConfig(properties);
	        consumerConnector = Consumer.createJavaConsumerConnector(consumerConfig);
	        
	    }

	    @Override
	    public void run() {
	        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
	        topicCountMap.put(TOPIC, new Integer(1));
	        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumerConnector.createMessageStreams(topicCountMap);
	        KafkaStream<byte[], byte[]> stream =  consumerMap.get(TOPIC).get(0);
	        ConsumerIterator<byte[], byte[]> it = stream.iterator();
	        while(it.hasNext()){
	        	try{
	        	Thread.sleep(100);
	        	}catch(Exception ex){}
	            System.out.println(new String(it.next().message()));
	        }

	    }
}
