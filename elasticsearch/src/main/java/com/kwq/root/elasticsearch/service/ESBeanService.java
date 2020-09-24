package com.kwq.root.elasticsearch.service;

import com.kwq.root.elasticsearch.model.ESBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @auther : kongweiqiang
 * @DATE : 2020/9/18
 * @DESC :
 */
public interface ESBeanService {

    Optional<ESBean> findById(String id);

    ESBean save(ESBean blog);

    void delete(ESBean blog);

    Optional<ESBean> findOne(String id);

    List<ESBean> findAll();

    Page<ESBean> findByAuthor(String author, PageRequest pageRequest);

    Page<ESBean> findByTitle(String title, PageRequest pageRequest);

    List<Map<String, Object>> query(String ... key) throws Exception;

    Page<ESBean> findByType(Integer title, PageRequest pageRequest);

}
