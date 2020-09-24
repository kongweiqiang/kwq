package com.kwq.root.elasticsearch.service.serviceImpl;

import com.google.common.collect.Lists;
import com.kwq.root.elasticsearch.dao.ESRepository;
import com.kwq.root.elasticsearch.model.ESBean;
import com.kwq.root.elasticsearch.service.ESBeanService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @auther : kongweiqiang
 * @DATE : 2020/9/18
 * @DESC :
 */
@Service
public class ESBeanServiceImpl implements ESBeanService {

    @Autowired
    private ESRepository repository;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private ElasticsearchRestTemplate restTemplate;

    @Override
    public Optional<ESBean> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public ESBean save(ESBean blog) {
        return repository.save(blog);
    }

    @Override
    public void delete(ESBean blog) {
        repository.delete(blog);
    }

    @Override
    public Optional<ESBean> findOne(String id) {
        return repository.findById(id);
    }

    @Override
    public List<ESBean> findAll() {
        return (List<ESBean>) repository.findAll();
    }

    @Override
    public Page<ESBean> findByAuthor(String author, PageRequest pageRequest) {
        return repository.findByAuthor(author,pageRequest);
    }

    @Override
    public Page<ESBean> findByTitle(String title, PageRequest pageRequest) {
        return repository.findByTitle(title,pageRequest);
    }

    @Override
    public List<Map<String, Object>> query(String ... key) throws Exception{
        String preTag = "<font color='#dd4b39'>";//google的色值//"<span style='color:red'>"
        String postTag = "</font>";//"</span>"
        SearchRequest searchRequest = new SearchRequest("kongweiqiang-01");
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("data","wo"));
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//        QueryStringQueryBuilder matchQueryBuilder = QueryBuilders.queryStringQuery("wo").defaultField("data");
//        boolBuilder.must(matchQueryBuilder);
        sourceBuilder.query(boolBuilder);
        sourceBuilder.from(0);
        sourceBuilder.size(100);
        //定义query查询
        //QueryBuilder queryBuilder = QueryBuilders.matchQuery("type",1 );
        //定义高亮查询
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        for(String field : key){
            highlightBuilder.field(new HighlightBuilder.Field(field));
        }
        highlightBuilder.postTags(postTag).preTags(preTag);
        sourceBuilder.query(boolBuilder);
        sourceBuilder.highlighter(highlightBuilder);
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        List<Map<String, Object>> list = Lists.newArrayList();
        //遍历高亮结果
        for (SearchHit searchHit : searchResponse.getHits().getHits()) {
            Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
            if (null != highlightFields && highlightFields.size() != 0) {
                for(String field : key) {
                    HighlightField highlightField = searchHit.getHighlightFields().get(field);
                    if (highlightField != null) {
                        Text[] contentText = highlightField.getFragments();
                        if (contentText != null && contentText.length != 0) {
                            StringBuffer stringBuffer = new StringBuffer();
                            for (Text str : contentText) {
                                stringBuffer.append(str.string());
                            }
                            // 遍历高亮结果集,覆盖正常结果集
                            searchHit.getSourceAsMap().put(field, stringBuffer.toString());
                        }
                    }
                }
            }
            list.add(searchHit.getSourceAsMap());
        }
        return list;
        /*for(String field : key){
            highlightBuilder.field(field, 100, 1);
        }
        highlightBuilder.preTags(preTag);
        highlightBuilder.postTags(postTag);
        sourceBuilder.highlighter(highlightBuilder);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        SearchRequest searchRequest = new SearchRequest("kongweiqiang-0918","kongweiqiang-01");
        searchRequest.source();
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();  //SearchHits提供有关所有匹配的全局信息，例如总命中数或最高分数：
        SearchHit[] searchHits = hits.getHits();
        List<Map<String, Object>> sourceList = new ArrayList<>();
        for (SearchHit searchHit : searchHits) {
            Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
            if (null != highlightFields && highlightFields.size() != 0) {
                for(String field : key) {
                    HighlightField highlightField = searchHit.getHighlightFields().get(field);
                    if (highlightField != null) {
                        Text[] contentText = highlightField.getFragments();
                        if (contentText != null && contentText.length != 0) {
                            StringBuffer stringBuffer = new StringBuffer();
                            for (Text str : contentText) {
                                stringBuffer.append(str.string());
                            }
                            // 遍历高亮结果集,覆盖正常结果集
                            searchHit.getSourceAsMap().put(field, stringBuffer.toString());
                        }
                    }
                }
            }
            sourceList.add(searchHit.getSourceAsMap());
        }*/
        /*for (SearchHit hit : searchHits) {
            System.out.println(hit.getIndex());
            Map<String, HighlightField> map=hit.getHighlightFields();
            System.out.println(hit.getSourceAsString());
        }*/
    }

    @Override
    public Page<ESBean> findByType(Integer type, PageRequest pageRequest) {
        return repository.findByType(type,pageRequest);
    }
}
