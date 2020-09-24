package com.kwq.root.elasticsearch.dao;

import com.kwq.root.elasticsearch.model.ESBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

/**
 * @auther : kongweiqiang
 * @DATE : 2020/9/18
 * @DESC :
 */
/**
 * 接口关系：
 * ElasticsearchRepository --> ElasticsearchCrudRepository --> PagingAndSortingRepository --> CrudRepository
 */
public interface ESRepository extends ElasticsearchRepository<ESBean, String> {

    Optional<ESBean> findById(String id);

    Page<ESBean> findByAuthor(String author, Pageable pageable);

    Page<ESBean> findByTitle(String title, Pageable pageable);

    Page<ESBean> findByType(Integer type, Pageable pageable);

    Page<ESBean> findByData(String data, Pageable pageable);

}

