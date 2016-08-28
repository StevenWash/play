package test.sample.contacts;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import test.sample.dao.SlCompanyDao;
import test.sample.entity.SlCompany;

import com.bstek.dorado.annotation.DataProvider;

@Component
public class CompanyService {
	
	@Resource
	private SlCompanyDao companyDao;

	@DataProvider
	public Collection<SlCompany> getCompany() {
		return companyDao.getAll();
	}
}
