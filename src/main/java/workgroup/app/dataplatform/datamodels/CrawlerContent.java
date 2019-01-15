package workgroup.app.dataplatform.datamodels;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;



import org.springframework.data.annotation.Id;

@Document(collection = "crawlerContent")
public class CrawlerContent {

	@Id
	private String docId;
	@Indexed
	private String websiteId;
	private String link;
	private String linkScrapeTime;
	private String linkContent;
	private String collectedDescription;
	
	
	
	
	public CrawlerContent(String websiteId, String link, String linkScrapeTime, String linkContent,
			String collectedDescription) {
		super();
		this.websiteId = websiteId;
		this.link = link;
		this.linkScrapeTime = linkScrapeTime;
		this.linkContent = linkContent;
		this.collectedDescription = collectedDescription;
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public String getWebsiteId() {
		return websiteId;
	}
	public void setWebsiteId(String websiteId) {
		this.websiteId = websiteId;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getLinkScrapeTime() {
		return linkScrapeTime;
	}
	public void setLinkScrapeTime(String linkScrapeTime) {
		this.linkScrapeTime = linkScrapeTime;
	}
	public String getLinkContent() {
		return linkContent;
	}
	public void setLinkContent(String linkContent) {
		this.linkContent = linkContent;
	}
	public String getCollectedDescription() {
		return collectedDescription;
	}
	public void setCollectedDescription(String collectedDescription) {
		this.collectedDescription = collectedDescription;
	}
	

}


