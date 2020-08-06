package it.regioneveneto.mygov.payment.mypivotsb;

import javax.annotation.Resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import it.regioneveneto.mybox.client.MyBoxClient;

//import it.regioneveneto.mygov.payment.mypivot.service.EnteService;

@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
@ComponentScan(basePackages= {"it.regioneveneto.mygov.payment.mypivotsb","it.regioneveneto.mygov.payment.mypivotsb.service", "it.regioneveneto.mygov.payment.mypivot.service","it.regioneveneto.mygov.payment.mypivot.dao"})
@EnableJpaRepositories(basePackages = {"it.regioneveneto.mygov.payment.mypivot.dao"})
@EntityScan(basePackages = {"it.regioneveneto.mygov.payment.mypivot.domain", "it.regioneveneto.mybox.domain"})
public class MypivotsbApplication extends SpringBootServletInitializer {

	@Resource
	private Environment env;

	public static void main(String[] args) {
		SpringApplication.run(MypivotsbApplication.class, args);
	}

	@Bean
	public MyBoxClient getMyBoxClient() {
		MyBoxClient myBoxClient = new MyBoxClient();
		myBoxClient.setMyBoxPortEndpointURL(env.getProperty("myBox.portEndpointURL"));
		return myBoxClient;
	}
}