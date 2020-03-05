package webApp;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import survey.Survey;

import java.util.List;

/*
 * A database for surveys
 */
@RepositoryRestResource(collectionResourceRel = "survey", path = "survey")
public interface SurveyRepository extends PagingAndSortingRepository<Survey, Long> {
    List<Survey> findByName(@Param("name") String name);
}
