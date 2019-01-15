package workgroup.app.dataplatform.application;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import workgroup.app.dataplatform.application.impl.WebCrawlerImpl;

@SpringBootApplication
@ComponentScan(basePackages="workgroup.app.dataplatform.*")
@EnableMongoRepositories(basePackages="workgroup.app.dataplatform.repo")
public class DataplatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataplatformApplication.class, args);
	}
	
	
	@Autowired
	private WebCrawlerImpl webcrawlerImpl;

	@PostConstruct
	public void initApp(){
		webcrawlerImpl.startCrawling();
	}

}

