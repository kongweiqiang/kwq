package com.kwq.root.elasticsearch.model;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @auther : kongweiqiang
 * @DATE : 2020/9/14
 * @DESC :
 */
@Data
@Document(indexName = "kongweiqiang",shards = 3,replicas = 2,createIndex = true)
public class ElasticEntity extends ESBean {

}
