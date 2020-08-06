package it.regioneveneto.mygov.payment.mypivot.web.config;

import it.regioneveneto.mybox.client.MyBoxClient;
import it.regioneveneto.mygov.payment.mypivot.client.PagamentiTelematiciDovutiPagatiClient;
import it.regioneveneto.mygov.payment.mypivot.client.PagamentiTelematiciDovutiPagatiClientImpl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;
import org.springframework.web.servlet.view.tiles3.SimpleSpringPreparerFactory;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

@Configuration
@ComponentScan("it.regioneveneto.mygov.payment.mypivot")
@PropertySource("classpath:mypivot.properties")
@EnableTransactionManagement
@EnableWebMvc
@EnableJpaRepositories("it.regioneveneto.mygov.payment.mypivot.dao")
public class ApplicationContextConfig extends WebMvcConfigurerAdapter {

	@Resource
	private Environment env;

	@Bean
	public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
		ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
		List<ViewResolver> resolvers = new ArrayList<ViewResolver>();

		TilesViewResolver tilesViewResolver = new TilesViewResolver();
		resolvers.add(tilesViewResolver);

		BeanNameViewResolver beanNameViewResolver = new BeanNameViewResolver();
		resolvers.add(beanNameViewResolver);

		InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
		internalResourceViewResolver.setPrefix("/WEB-INF/views/");
		internalResourceViewResolver.setSuffix(".jsp");
		internalResourceViewResolver.setViewClass(JstlView.class);
		resolvers.add(internalResourceViewResolver);

		JsonViewResolver jsonViewResolver = new JsonViewResolver();
		resolvers.add(jsonViewResolver);

		resolver.setViewResolvers(resolvers);
		resolver.setContentNegotiationManager(manager);

		return resolver;

	}

	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

		Map<String, MediaType> mediaTypes = new LinkedHashMap<>();
		mediaTypes.put("json", MediaType.APPLICATION_JSON);
		mediaTypes.put("html", MediaType.TEXT_HTML);
		configurer.mediaTypes(mediaTypes);
		configurer.defaultContentType(MediaType.TEXT_HTML);

		configurer.favorPathExtension(true);
		configurer.useJaf(false);
		configurer.ignoreAcceptHeader(true);
	}
	
    @Bean(name = "dataSource", destroyMethod = "")
    public DataSource getDataSource() {
        final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
        dsLookup.setResourceRef(true);
        DataSource dataSource = dsLookup.getDataSource(env.getProperty("dataSource.jndiName"));
        return dataSource;
    }
	
	private Properties getHibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.show_sql", env.getProperty("hibernate.showSql"));
		properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
		properties.put("hibernate.format_sql", true);
		return properties;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(getDataSource());
		entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		entityManagerFactoryBean.setPackagesToScan("it.regioneveneto.mygov.payment.mypivot.domain.po");

		entityManagerFactoryBean.setJpaProperties(getHibernateProperties());

		return entityManagerFactoryBean;
	}

	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return transactionManager;
	}

	@Bean(name = "tilesConfigurer")
	public TilesConfigurer getTilesConfigurer() {
		TilesConfigurer tilesConfigurer = new TilesConfigurer();
		tilesConfigurer.setDefinitions("/WEB-INF/views/views.xml");
		tilesConfigurer.setPreparerFactoryClass(SimpleSpringPreparerFactory.class);
		return tilesConfigurer;
	}

	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
		source.setBasename(env.getRequiredProperty("message.source.basename"));
		source.setUseCodeAsDefaultMessage(true);
		source.setFallbackToSystemLocale(false);
		source.setDefaultEncoding("UTF-8");
		return source;
	}

	@Bean(name = "localeResolver")
	SessionLocaleResolver sessionLocaleResolver() {
		SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
		return sessionLocaleResolver;
	}

	@Bean
	LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		return localeChangeInterceptor;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**").addResourceLocations("/css/");
		registry.addResourceHandler("/js/**").addResourceLocations("/js/");
		registry.addResourceHandler("/images/**").addResourceLocations("/images/");
		registry.addResourceHandler("/styles/**").addResourceLocations("/styles/");
		registry.addResourceHandler("/fonts/**").addResourceLocations("/fonts/");
		registry.addResourceHandler("/ext/**").addResourceLocations("/ext/");
	}

	@Bean
	public MyBoxClient getMyBoxClient() {
		MyBoxClient myBoxClient = new MyBoxClient();
		myBoxClient.setMyBoxPortEndpointURL(env.getProperty("myBox.portEndpointURL"));
		return myBoxClient;
	}

	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver createMultipartResolver() {
	    CommonsMultipartResolver resolver=new CommonsMultipartResolver();
	    resolver.setMaxUploadSize(10485760);
	    resolver.setDefaultEncoding("utf-8");
	    return resolver;
	}
	
	
	/**
	 * View resolver for returning JSON in a view-based system. Always returns a
	 * {@link MappingJacksonJsonView}.
	 */
	public class JsonViewResolver implements ViewResolver {
		public View resolveViewName(String viewName, Locale locale) throws Exception {

			MappingJackson2JsonView view = new MappingJackson2JsonView();
			view.setPrettyPrint(true);

			// MappingJacksonJsonView view = new MappingJacksonJsonView();
			// view.setPrettyPrint(true);
			return view;
		}
	}

}
