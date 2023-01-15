package app;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import web.member.dao.MemberDao;
import web.member.entity.Member;

public class TestApp {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		MemberDao memberDao = applicationContext.getBean(MemberDao.class);
		Member member = memberDao.selectById(1);
		System.out.println(member.getNickname());
		((ConfigurableApplicationContext) applicationContext).close();
	}
}
