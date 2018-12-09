#!/usr/bin/env bash
./ZooKeeper/bin/zkServer.sh
sleep 10
./Kafka/bin/kafka-server-start.sh ./Kafka/config/server.properties
sleep 10
./Kafka/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic event-topic