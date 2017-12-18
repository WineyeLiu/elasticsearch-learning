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
    

    