# Kafka POC

## Variables

## Consumer
```shell
BOOSTRAP_SERVERS=localhost:9093,localhost:9094,localhost:9095
KAFKA_TOPIC=table.book

kcat -C -b ${BOOSTRAP_SERVERS} -K"\t" -t ${KAFKA_TOPIC} -o beginning -e 
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

## References 
https://stackoverflow.com/questions/46649748/kafka-producer-timeoutexception-expiring-1-records
