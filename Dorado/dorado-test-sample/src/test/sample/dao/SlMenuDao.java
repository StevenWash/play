package test.sample.dao;

import org.springframework.stereotype.Repository;

import test.sample.entity.SlMenu;

import com.bstek.dorado.hibernate.HibernateDao;

@Repository
public class SlMenuDao extends HibernateDao<SlMenu, Long> {

}
