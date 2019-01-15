package workgroup.app.dataplatform.jobs;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import workgroup.app.dataplatform.datamodels.CrawlerContent;
import workgroup.app.dataplatform.repo.ValidLinkRepository;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class CrawlBroadsword {
	
	private static final String collectedDesc = "Collected from online scraping www.ajaishukla.blogspot.com";
	private static final String websiteId = "20010";
	private static final Logger LOGGER = LoggerFactory.getLogger(CrawlBroadsword.class);
	
	@Autowired
	ValidLinkRepository validLinksRepository;
	
	@Value("#{new java.util.Date()}")
	Date date;
	
    private static final int MAX_DEPTH = 4;
    private HashSet<String> links;

    public CrawlBroadsword() {
        links = new HashSet<>();
    }
    
    private int breaker = 0;

    private void getPageLinks(String URL, int depth) {
        if ((!links.contains(URL) && (depth < MAX_DEPTH))) {
            //System.out.println(">> Depth: " + depth + " [" + URL + "]");
        	breaker ++ ;

            try {
            	if(URL.matches("[A-Za-z:\\/.]*\\/20[0-9]{2}\\/[0-9]{2}\\/[A-Za-z:\\/\\-.]*") && URL.contains(".html")){
                links.add(URL);
                LOGGER.info(" [ADDED] >> Depth: " + depth + " [" + URL + "]");
                //insertLinks(URL);
                
            	}
                Document document = Jsoup.connect(URL).ignoreContentType(true).get();
                Elements linksOnPage = document.select("a[href]");
                
                //Remove comment for lesser links
                Pattern p = Pattern.compile("20[0-9]{2}\\/[0-9]{2}");
                depth++;
                for (Element page : linksOnPage) {
                	Matcher m = p.matcher(page.attr("abs:href"));
                	if (m.find())
                    getPageLinks(page.attr("abs:href"), depth);
                	else
                		//System.out.println("[REJECTING!] : " + page.attr("abs:href"));
                		continue;
                }
            } catch (Exception e) {
            	LOGGER.error("For '" + URL + "': " + e.getMessage());
            } 
        }
    }

    public void getLinks() {
        getPageLinks("http://www.ajaishukla.blogspot.com/", 1);
        getAllLinkContent(links);
        
    }
    
	private void getAllLinkContent(HashSet<String> links) {
		for(String link:links) {
			insertLinks(link, getURLContent(link)); 
		}
		
	}

	private String getURLContent(String URL){
		Document doc = null;
		try {
			//String url = "http://ajaishukla.blogspot.in/2013/06/lunch-with-business-standard-dr-avinash.html";
		

		doc = Jsoup.connect(URL)
                .userAgent("Mozilla/5.0")
                .timeout(30000)
                .get();
		doc.getElementById("comments").remove();
		
		doc.getElementById("sidebar-right-3").remove();
		doc.getElementById("Label1").remove();
		doc.getElementsByClass("post-footer-line post-footer-line-2").remove();
		doc.getElementsByClass("post-footer").remove();
		doc.getElementsByClass("post-share-buttons goog-inline-bloc").remove();
		doc.getElementsByClass("widget Text").remove();
		doc.getElementsByClass("widget HTML").remove();
		doc.getElementsByClass("blog-pager").remove();
		doc.getElementsByClass("widget-content").remove();
		doc.getElementsByClass("post-feeds").remove();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc.text();	
	}
    
   public void insertLinks(String link,String content){    	
    	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    	LOGGER.info("[INSERTING]  " + link);
		validLinksRepository.save(new CrawlerContent( websiteId, link, dateFormat.format(date),content,collectedDesc));

    	
/*    	for(String link:links){
    		LOGGER.info("[INSERTING!]  " + link);
    		validLinksRepository.save(new ValidLinks( "20001", link, dateFormat.format(date)));

    	}*/
    }
    
    
   
}