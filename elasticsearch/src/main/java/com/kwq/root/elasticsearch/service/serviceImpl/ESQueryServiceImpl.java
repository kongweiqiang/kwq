package com.kwq.root.elasticsearch.service.serviceImpl;

import com.kwq.root.elasticsearch.service.ESQueryService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @auther : kongweiqiang
 * @DATE : 2020/9/11
 * @DESC :
 */
@Slf4j
@Service
public class ESQueryServiceImpl implements ESQueryService {

    @Autowired
    private RestHighLevelClient client;

//    @Override
//    public List<Map<String, Object>> query(String index, String type, String[] fields, String classA, String objectType, String content, int page, int rows, String sort, int order) {
//        BoolQueryBuilder build = QueryBuilders.boolQuery();
//        build.should((QueryBuilders.matchQuery("filedA","contentA contentB")
//                .operator(Operator.AND).boost(1))//filedA包含contentA和contentB,权重为1
//                .should(QueryBuilders.matchQuery("filedB","contentA contentB"))
//                .operator(Operator.OR).boost(10));//filedB包含contentA或contentB,权重为10
//        SearchRequestBuilder searchSourceBuilder = new SearchSourceBuilder();
//        searchRequestBuilder.setQuery(build);
//        // 设置高亮字段
//        HighlightBuilder highlightBuilder = new HighlightBuilder();
//        for(String field : fields){
//            highlightBuilder.field(field, 100, 1);
//        }
//        highlightBuilder.preTags("<em style='color:red'>");
//        highlightBuilder.postTags("</em>");
//        searchRequestBuilder.highlighter(highlightBuilder);
//        // 设置分页
//        searchRequestBuilder.setFrom(page).setSize(rows);
//        // 打印的内容 可以在 Elasticsearch head 和 Kibana 上执行查询
//        System.out.println(searchRequestBuilder);
//        SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();
//        List<Map<String, Object>> sourceList = new ArrayList<>();
//        for (SearchHit searchHit : searchResponse.getHits().getHits()) {
//            Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
//            if (null != highlightFields && highlightFields.size() != 0) {
//                for(String field : fields) {
//                    HighlightField highlightField = searchHit.getHighlightFields().get(field);
//                    if (highlightField != null) {
//                        Text[] contentText = highlightField.getFragments();
//                        if (contentText != null && contentText.length != 0) {
//                            StringBuffer stringBuffer = new StringBuffer();
//                            for (Text str : contentText) {
//                                stringBuffer.append(str.string());
//                            }
//                            // 遍历高亮结果集,覆盖正常结果集
//                            searchHit.getSourceAsMap().put(field, stringBuffer.toString());
//                        }
//                    }
//                }
//            }
//            sourceList.add(searchHit.getSourceAsMap());
//        }
//        return sourceList;
//    }

    public void build() throws Exception{
        // 构建搜索
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 查询条件很多,如果做搜索时有些选项可填可不填,可空克不空
        // 创建条件查询
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        // 条件一
        boolBuilder.must(QueryBuilders.matchPhraseQuery("字段1", "xx"));
        // 条件二
        boolBuilder.must(QueryBuilders.matchPhraseQuery("字段2", "xx"));
        // 带范围的
        // searchSourceBuilder.postFilter(QueryBuilders.rangeQuery("某个是数的字段").gte("gte大于等于").lte("lte小于等于"));
        // 排个序
        // searchSourceBuilder.sort("排序字段", SortOrder.DESC);
        // 分个页(from从第几条开始,size每页显示几条)
        // searchSourceBuilder.from(0).size(10);
        // 将条件添加进构造的搜索
        searchSourceBuilder.query(boolBuilder);
        // 查询所有(这条和上面这条只能生效一个,有条件就不能匹配所有)
        // searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        SearchRequest searchRequest = new SearchRequest();
        // 设置索引
        searchRequest.indices("logstash-apigatewaytracelogger-dev-2019.05.10");
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits searchHits = searchResponse.getHits();
        System.out.println("查到数目:" + searchHits.getTotalHits());
        SearchHit[] hits = searchHits.getHits();
        ArrayList<String> list = new ArrayList<String>();
        for (SearchHit hit : hits) {
            String json = hit.getSourceAsString();
            list.add(json);
        }
    }
}
