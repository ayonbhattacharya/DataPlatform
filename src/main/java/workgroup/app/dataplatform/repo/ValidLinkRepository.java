package workgroup.app.dataplatform.repo;

import org.springframework.stereotype.Repository;
import workgroup.app.dataplatform.datamodels.CrawlerContent;
import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface ValidLinkRepository extends MongoRepository<CrawlerContent, String>{

}
