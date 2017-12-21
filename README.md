# ElasticSearch 学习文档

    ElasticSearch (以下简称ES) 是 一个distributed, RESTful search and analytics engine

## 基础

### ES安装

    解压 elasticsearch-5.5.0.zip 即可

    运行方法：执行bin目录下的elasticsearch即可（使用nohup可作为守护进程启动）

#### 运行环境

    linux 上不能以root用户安装,创建一个新用户es

    需要JAVA环境。检查Java环境，最好使用官方推荐的Java版本

    报错解决方法：

        a) ERROR: bootstrap checks failed   max file descriptors [4096] for elasticsearch process likely too low, increase to at least [65536]

            vim /etc/security/limits.conf

            添加如下内容:

            es  soft    nofile  65536

            es  hard    nofile  131072

            es  soft    nproc   2048

            es  hard    nproc   4096

            保存后重新登陆生效

        b) max virtual memory areas vm.max_map_count [65530] likely too low, increase to at least [262144]

            vim /etc/sysctl.conf

            添加如下内容：

            vm.max_map_count=655360

        c) max number of threads [1024] for user [es] likely too low, increase to at least [2048]

            cd /etc/security/limits.d

            找到形如xx-nproc.conf的文件，用vim编辑，找到如下内容：

            *   soft    nproc   1024

            将1024改为2048

#### ES配置

##### elasticsearch.yml配置

    ES的常见配置都在conf目录下的elasticsearch.yml配置文件中。

    ES默认只允许本机访问。如果需要通过别的机器访问ES的API，需要将 network.host 配置项配置为需要访问ES的机器的IP。

    开发环境下可设置为 network.host: 0.0.0.0 （所有机器都能访问）

##### 配置中文分词：elasticsearch-analysis-ik

```bash
    ./bin/elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v5.5.0/elasticsearch-analysis-ik-5.5.0.zip
```

    插件的版本必须与ES版本一致

### 基本ES操作

#### 基本概念

    * index --> db
    * type --> table
    * document --> record
    * field --> column


#### 基本CRUD操作

* create   

    ```bash 
    curl -XPUT localhost:9200/myindex/mytype?pretty -H 'Content-Type: application/json' -d '{"account":"liuwenai","age":25}'
    ```   
    或   
    ```bash  
    curl -XPOST localhost:9200/myindex/mytype?pretty -H 'Content-Type: application/json' -d '{"account":"liuwenai","age":25}'
    ```


    第一种指定id，使用PUT方法  
    第二种不指定id(ES自动生成)，使用POST方法
    es6 需要指定Content-Type

* retrieve  

    ```bash  
    curl localhost:9200/myindex/mytype/1?pretty=true
    ```  
    或  
    ```bash  
    curl localhost:9200/myindex/mytype/_search?pretty
    ```  
    或带条件查询  
    ```bash  
    curl localhost:9200/myindex/mytype/_search?pretty -H 'Content-Type: application/json' -d '{"query": {"match": {"account":"飞"}}}'
    ```  
    带条件查询结果：  
    ```json  
        {
            "took" : 121,
            "timed_out" : false,
            "_shards" : {
                "total" : 5,
                "successful" : 5,
                "skipped" : 0,
                "failed" : 0
            },
            "hits" : {
                "total" : 2,
                "max_score" : 0.6099695,
                "hits" : [
                {
                    "_index" : "myindex",
                    "_type" : "mytype",
                    "_id" : "3",
                    "_score" : 0.6099695,
                    "_source" : {
                    "account" : "王飞",
                    "age" : 22
                    }
                },
                {
                    "_index" : "myindex",
                    "_type" : "mytype",
                    "_id" : "2",
                    "_score" : 0.2876821,
                    "_source" : {
                    "account" : "黄飞鸿",
                    "age" : 22
                    }
                }
                ]
            }
        }

    ```

* update  
    更新是使用PUT请求，重新发送一次数据：
    ```bash  
    curl -XPUT localhost:9200/myindex/mytype/3?pretty -H 'Content-Type: application/json' -d '{"account":"王飞", "age": 26}'
    ```


* delete 
    ```bash  
    curl -X DELETE localhost:9200/myindex/mytype/1
    ```

### ES Java-api

    参考：[ES Java-Api](https://www.elastic.co/guide/en/elasticsearch/client/java-api/5.6/index.html)

    参考：es-learning

```java
    Settings settings = Settings.builder().put("client.transport.ignore_cluster_name", true).put("client.transport.ping_timeout", "30s").put("client.transport.sniff", true).build();

	TransportClient client = new PreBuiltTransportClient(settings).addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.146.128"), 9300));

	log.info("init completed!");

	List<DiscoveryNode> nodes = client.connectedNodes();

	log.info("connected nodes ("+nodes.size()+") :"+nodes);
```

### ES与 spring-data集成

    参考：es-spring
