package com.kwq.root.elasticsearch;

import com.alibaba.fastjson.JSONObject;
import com.kwq.root.elasticsearch.model.DocumentModel;
import com.kwq.root.elasticsearch.model.ESBean;
import com.kwq.root.elasticsearch.service.ESBeanService;
import com.kwq.root.elasticsearch.service.ESIndexService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

/**
 * @auther : kongweiqiang
 * @DATE : 2020/9/15
 * @DESC :
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ESIndexTest {

    @Autowired
    private ESIndexService esIndexService;

    @Autowired
    private ESBeanService service;

    @Test
    public void createIndex(){
        DocumentModel document = new DocumentModel();
        System.out.println(document);
        System.out.println("success");
        for(int i = 21 ; i< 40; i++) {
            document.setType(Math.floorDiv(i+1,2));
            document.setTypeName("分类"+ Math.floorDiv(i+1,2));
            document.setId(i+1L);
            document.setData("wo 是 "+(i+1)+" 人");
            document.setData(JSONObject.toJSONString(document));
            service.save(document);
       }
    }

    @Test
    public void queryDocumentByType(){
        Page<ESBean> author = service.findByType(1, null);
        System.out.println(author);
    }

    @Test
    public void queryDocumentByContent() throws Exception{
        List<Map<String, Object>> query = service.query("wo","data","1");
        System.out.println(query);
    }

}
