package core.config;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.orm.hibernate5.support.OpenSessionInViewFilter;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * 此類別為配合XML組態使用的[部署描述類別]
 * 使用時，須..
 * 	將 public class WebAppInitializerXml implements WebApplicationInitializer { 取消註解
 *  將 public class WebAppInitializerXml { 註解
 */
//public class WebAppInitializerXml implements WebApplicationInitializer {
public class WebAppInitializerXml {

//	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		XmlWebApplicationContext applicationContext = new XmlWebApplicationContext();
		applicationContext.setConfigLocations(
			"classpath:applicationContext.xml",
			"classpath:applicationContext-hibernate.xml",
			"classpath:applicationContext-websocket.xml"
		);
		servletContext.addListener(new ContextLoaderListener(applicationContext));
		servletContext.addFilter("characterEncodingFilter", getCharacterEncodingFilter());
		servletContext.addFilter("openSessionInViewFilter", new OpenSessionInViewFilter());
		
		XmlWebApplicationContext mvcApplicationContext = new XmlWebApplicationContext();
		mvcApplicationContext.setConfigLocation("classpath:applicationContext-mvc.xml");
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(mvcApplicationContext));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");
		dispatcher.setMultipartConfig(new MultipartConfigElement(""));
	}

	private Filter getCharacterEncodingFilter() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		return characterEncodingFilter;
	}
}
