package test.sample.dao;

import org.springframework.stereotype.Repository;

import test.sample.entity.SlEmployee;

import com.bstek.dorado.hibernate.HibernateDao;

@Repository
public class SlEmployeeDao extends HibernateDao<SlEmployee, Long> {

}
