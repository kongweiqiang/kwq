package com.kwq.root.elasticsearch.service;

import com.alibaba.fastjson.JSONObject;
import com.kwq.root.elasticsearch.model.ElasticEntity;
import org.apache.poi.ss.formula.functions.T;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.Collection;
import java.util.List;

/**
 * @auther : kongweiqiang
 * @DATE : 2020/9/11
 * @DESC :
 */
public interface ESIndexService {

    /**
     *
     * @param idxName 索引名称
     * @param idxSQL 索引描述
     */
    void createIndex(String idxName, JSONObject idxSQL);

    boolean indexExist(String idxName) throws Exception;

    boolean isExistsIndex(String idxName) throws Exception;

    void insertOrUpdateOne(String idxName, ElasticEntity entity);

    void deleteOne(String idxName, ElasticEntity entity);

    void insertBatch(String idxName, List<ElasticEntity> list);

    void insertBatchTrueObj(String idxName, List<ElasticEntity> list);

    void deleteBatch(String idxName, Collection<T> idList);

    List<T> search(String idxName, SearchSourceBuilder builder, Class<T> c);

    void deleteIndex(String idxName);

    void deleteByQuery(String idxName, QueryBuilder builder);

    void buildSetting(CreateIndexRequest request);

}
