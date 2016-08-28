package test.sample.dao;

import org.springframework.stereotype.Repository;

import test.sample.entity.SlMessage;

import com.bstek.dorado.hibernate.HibernateDao;

@Repository
public class SlMessageDao extends HibernateDao<SlMessage, Long> {

}
