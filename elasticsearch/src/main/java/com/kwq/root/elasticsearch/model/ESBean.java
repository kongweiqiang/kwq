package com.kwq.root.elasticsearch.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @auther : kongweiqiang
 * @DATE : 2020/9/18
 * @DESC :
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@Document(indexName = "kongweiqiang-01",replicas = 2,shards = 1)
public class ESBean {

    @Id
    private Long id;

    @Field(type = FieldType.Keyword)
    private String title;

    @Field(type = FieldType.Keyword)
    private String author;

    @Field(type = FieldType.Keyword)
    private Byte source;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String data;

    @Field(type = FieldType.Integer)
    private Integer type;

    @Field(type = FieldType.Keyword)
    private String typeName;

}
