package test.sample.dao;

import org.springframework.stereotype.Repository;

import test.sample.entity.SlCompany;

import com.bstek.dorado.hibernate.HibernateDao;

@Repository
public class SlCompanyDao extends HibernateDao<SlCompany, Long> {

}
