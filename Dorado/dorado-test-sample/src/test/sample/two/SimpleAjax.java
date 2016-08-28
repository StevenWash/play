package test.sample.two;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

import test.sample.entity.User;


import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.Expose;
 
@Component
public class SimpleAjax {
    @Expose
    public String toUpperCase(String str){
        return "input:\n"+str+"\n\n"+"output:\n"+str.toUpperCase();
    }
    
    @DataProvider
    public Collection<User> getUserList(String str) {
    	List<User> list = new ArrayList<User>();
    	list.add(new User("1","张三","男"));
    	list.add(new User("2","李斯","女"));
        return list;
    }
}

