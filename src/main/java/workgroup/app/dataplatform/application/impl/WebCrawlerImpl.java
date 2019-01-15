package workgroup.app.dataplatform.application.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import workgroup.app.dataplatform.jobs.CrawlBroadsword;

@Component
public class WebCrawlerImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebCrawlerImpl.class);
	
	@Autowired
	private CrawlBroadsword crawlBroadsword;
	
	@RequestMapping
	public void startCrawling() {
		LOGGER.debug("-------------------------------------------------------------");
		LOGGER.debug("-----------Starting Link Validation for Broadsword-----------");
		LOGGER.debug("-------------------------------------------------------------");
		crawlBroadsword.getLinks();
		System.exit(0);
		//LOGGER.debug("-------------------------------------------------------------");
		//LOGGER.debug("------------Starting Link Insert for Broadsword--------------");
		//LOGGER.debug("-------------------------------------------------------------");
		//crawlBroadsword.insertLinks();
	}
}
