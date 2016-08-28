package test.sample.contacts;

import java.util.Collection;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Component;

import test.sample.dao.SlEmployeeDao;
import test.sample.entity.SlEmployee;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.bstek.dorado.hibernate.HibernateUtils;

@Component
public class EmployeeService {

	@Resource
	private SlEmployeeDao employeeDao; 
	
	@DataProvider
	public Collection<SlEmployee> getAllEmployee() {
		return employeeDao.getAll();
	}
	
	@DataProvider
	public void getAllForPage(Page<SlEmployee> page) {
		employeeDao.getAll(page);
	}
	
	@DataProvider
	public void getAllForFilter(Page<SlEmployee> page, Criteria criteria) throws Exception {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SlEmployee.class);
		if (criteria != null) {
			employeeDao.find(page, HibernateUtils.createFilter(detachedCriteria, criteria));
		} else {
			employeeDao.getAll(page);
		}
	}
}
