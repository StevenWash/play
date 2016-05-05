package com.test.repository;

import org.springframework.data.repository.CrudRepository;

import com.test.bean.CommodityBean;

public interface CommodityRepository extends CrudRepository<CommodityBean, Integer> {

}
