package com.kwq.root.elasticsearch.controller;

import com.kwq.root.elasticsearch.model.DocumentModel;
import com.kwq.root.elasticsearch.model.ESBean;
import com.kwq.root.elasticsearch.service.ESBeanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @auther : kongweiqiang
 * @DATE : 2020/9/18
 * @DESC :
 */

@RestController
public class ESBeanController {

    @Autowired
    private ESBeanService service;

    @RequestMapping("/bean/{id}")
    @ResponseBody
    public ESBean getById(@PathVariable String id){
        Optional<ESBean> opt =service.findById(id);
        ESBean bean=opt.get();
        System.out.println(bean);
        return bean;
    }

    @RequestMapping("/save")
    @ResponseBody
    public void Save(){
        DocumentModel document = new DocumentModel();
        document.setAuthor("孔维强");
        document.setData("wo 是 1 ge 人");
        System.out.println(document);
        service.save(document);
    }
    
}
