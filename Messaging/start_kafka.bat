start .\ZooKeeper\bin\zkServer.cmd
timeout 5
start .\Kafka\bin\windows\kafka-server-start.bat .\Kafka\config\server.properties
timeout 5
start .\Kafka\bin\windows\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic event-topic