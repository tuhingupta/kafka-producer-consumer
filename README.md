# kafka-producer-consumer 

This project contains Java examples to produce/consume messages into and from Kafka topic.

## To run the examples

1. Install **Docker Kafka Image**

I am using __spotify/kafka__ image from Docker Hub. The benefit of using this is that both Kafka and ZooKeeper are available in the same image.

2. Run Kafka on local

```
$ docker run --name kafkaapp -p 2181:2181 -p 9092:9092 --env ADVERTISED_HOST=localhost --env ADVERTISED_PORT=9092 spotify/kafka:latest &
```
3. Build project using Maven

4. Run `io.tuhin.vertx.http.StreamingServer` class. This run Streaming server to accept stream of values from client. This will also submit the input stream to Kafka topic.

5. Run `io.tuhin.vertx.http.StreamingClient` class. This is a `Vertx.io` streaming client that reads a file and sends HTTP stream to `Vertx.io` server. 

6. Run `io.tuhin.kafka.simpleconsumer.KafkaStreamConsumer` from command prompt so that you could start/stop the Kafka Consumer.

```
java -jar vertx-stream-examples-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```

You should see the output from Consumer printing the contents that are consumed from Kafka Topic.


## Notes:
* Look at the code in KafkaStreamConsumer. This sets certain `properties` that help commit back `offset` to ZK so that whenever you kill the consumer and restart later, it picks the offset and starts consuming from the point where it stopped.

* Sometime Code in Java Class `KafkaStreamConsumer` will give error at line 52 and 57 when using JDK 1.8. The error will be 
```
The method iterator() is ambiguous for the type  KafkaStream<byte[],byte[]>
```
This error will go away if using JDK 1.7. Also it will build using maven and run your code without any issues.

You can read more about this at the following [JIRA issue] (https://bugs.openjdk.java.net/browse/JDK-8065185)


