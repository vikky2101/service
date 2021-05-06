=====================================================================
Kafka - https://www.baeldung.com/spring-kafka

Start Steps 
 1. zookeeper-server-start /usr/local/etc/kafka/zookeeper.properties 
 2. kafka-server-start /usr/local/etc/kafka/server.properties
 
 Install Path : /usr/local/Cellar/kafka/2.3.1/bin
 Log Path =  /usr/local/var/lib/kafka-logs
 Run curl -X POST -F 'message=test' http://localhost:8090/kafka/publish
 

https://medium.com/@Ankitthakur/apache-kafka-installation-on-mac-using-homebrew-a367cdefd273
 kafka-topics --zookeeper localhost:2181 --list
 http://kafka.apache.org/08/documentation/#configuration
 ======================================================================
 Cassandra - https://www.baeldung.com/spring-data-cassandra-tutorial
 
 Install : brew install cassandra
 Run : brew services start cassandra 
 Cql : cqlsh localhost
 describe keyspaces;
 create keyspace film_service  with replication={'class':'SimpleStrategy', 'replication_factor':1};
 CREATE TABLE datamodel(
    id int PRIMARY KEY,
    name text
 );
 describe datamodel;
 drop table datamodel;
 select * from datamodel;
  create keyspace film_service  with replication={'class':'SimpleStrategy', 'replication_factor':1};
  CREATE TABLE filmDatamodel(
     id int PRIMARY KEY,
     name text,
     directed_by text,
     initial_release_date date,
     genre list<text>
  );
  
    CREATE TABLE productDatamodel(
       id bigint PRIMARY KEY,
       name text,
       author text,
       series_t text,
       cat list<text>,
       sequence_i int,
       genre_s text,
       inStock boolean,
       price double,
       pages_i int
    );
    
INSERT INTO datamodel (directed_by,genre,id,initial_release_date,name) VALUES ('Gary Lennon',['Black comedy','Thriller','Psychological thriller','Indie film','Action Film','Crime Thriller','Crime Fiction','Drama'],2006,null,'gary');
 ===============================================================================================
 Solr - https://www.baeldung.com/spring-data-solr
 zookeeper-3.4.12/bin/zkServer.sh start
 Start 
 solr_path=~/Setup/solr-7.6.0
 port=8983
 node_path=~/solrData7/
 bootstrap=false
 cd $solr_path/bin
 ./solr start -c -m 1g -z localhost:2181 -a "-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=1044" -t $node_path -s $node_path
 Data Modelling - https://www.baeldung.com/cassandra-data-modeling
 https://tech.ebayinc.com/engineering/cassandra-data-modeling-best-practices-part-1/
 Modify Schema - https://lucene.apache.org/solr/guide/6_6/schema-api.html#SchemaAPI-ModifytheSchema
 ALIAS - 
 http://localhost:8983/solr/admin/collections?action=CREATEALIAS&name=film_service_live&collections=film_service
 http://localhost:8983/solr/admin/collections?action=LISTALIASES&wt=json
 ================================================================================================
 Payload : 
 product_service
 {
  "topicName":"product_service",
  "payload" :{
  	"params":{
  	 "id":12348,	
      "name":"mouse"
    }
  }
 }
 File Service:
 {
  "topicName":"film_service",
  "payload" :{
  	"params":{
     "id": 2006,
     "directed_by": "Gary Lennon",
     "initial_release_date": "2006-11-30",
     "genre": [
       "Black comedy",
       "Thriller",
       "Psychological thriller",
       "Indie film",
       "Action Film",
       "Crime Thriller",
       "Crime Fiction",
       "Drama"
     ],
     "name": "Karoke"
   }
  }
 }
 https://www.baeldung.com/cassandra-data-modeling
 
 ========TypeAhead Service====
 collection create -> ./solr create -c typeahead_service -d typeahead_config
 Index data        ->  bin/post -c typeahead_service example/exampledocs/
 
 
                       I
