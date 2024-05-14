# Kafka POC

## Variables

## Consumer
```shell
# Creates book with Kafka publishing
BOOSTRAP_SERVERS=localhost:9093,localhost:9094,localhost:9095
KAFKA_TOPIC=table.book

kcat -C -b ${BOOSTRAP_SERVERS} -K"\t" -t ${KAFKA_TOPIC} -o beginning -e 


# Creates book with async Kafka publishing 
BOOSTRAP_SERVERS=localhost:9093,localhost:9094,localhost:9095
KAFKA_TOPIC=table.book-async

kcat -C -b ${BOOSTRAP_SERVERS} -K"\t" -t ${KAFKA_TOPIC} -o beginning 
```

## Kafka UI
```shell
docker run -p 8080:8080 \
        -e KAFKA_CLUSTERS_0_NAME=local \
        -e KAFKA_CLUSTERS_0_BOOTSTRAPSERVERS=localhost:9093,localhost:9094,localhost:9095 \
        -d provectuslabs/kafka-ui:latest        
```
## Kafka configuration

To change topic configuration you could go inside one kafka container
```shell
docker exec -it kafka1 /bin/bash

BOOSTRAP_SERVERS=kafka1:9092,kafka2:9092,kafka3:9092 
KAFKA_TOPIC=table.book
kafka-topics.sh --bootstrap-server ${BOOSTRAP_SERVERS} --describe --topic ${KAFKA_TOPIC}

# set min.insync.replicas to 1
kafka-configs.sh --bootstrap-server ${BOOSTRAP_SERVERS} --alter --topic ${KAFKA_TOPIC} --add-config min.insync.replicas=1

# Change partitions
kafka-topics.sh --bootstrap-server ${BOOSTRAP_SERVERS} --alter --topic ${KAFKA_TOPIC} --partitions 20

kafka-configs.sh --bootstrap-server ${BOOSTRAP_SERVERS} --alter --topic ${KAFKA_TOPIC} --add-config max.compaction.lag.ms=10000 
kafka-configs.sh --bootstrap-server ${BOOSTRAP_SERVERS} --alter --topic ${KAFKA_TOPIC} --add-config min.cleanable.dirty.ratio=0.0 
kafka-configs.sh --bootstrap-server ${BOOSTRAP_SERVERS} --alter --topic ${KAFKA_TOPIC} --add-config segment.ms=10000
kafka-configs.sh --bootstrap-server ${BOOSTRAP_SERVERS} --alter --topic ${KAFKA_TOPIC} --add-config delete.retention.ms=10000

--config min.cleanable.dirty.ratio=0.0 --config segment.ms=10000 --config delete.retention.ms=10000
```


## Endpoints 

###  POST /api/books
It will create a book, after saving and before publishing to Kafka there is a delay, 
so the request response will be delayed as well.

```sh
curl --location --request POST 'localhost:8007/api/books' | jq
```
###  POST /api/async/books
It will create a book, after saving and before publishing to Kafka topic there is a delay,
but because it is async, publishing is done in a different thread, so the request response will be not delayed

```sh
curl --location --request POST 'localhost:8007/api/async/books' | jq
```

### POST /api/books/republish?limit=10

It will find all books ordered by createdAt, modify the title and publish them to Kafka topic. 

```sh
curl --location --request POST 'localhost:8007/api/books/republish?limit=10' | jq
```

### POST /api/async/books/republish?limit=10

It will find all books ordered by createdAt, modify the title and publish them to Kafka topic.

```sh
curl --location --request POST 'localhost:8007/api/async/books/republish?limit=10' | jq
```

## References 
https://stackoverflow.com/questions/46649748/kafka-producer-timeoutexception-expiring-1-records
