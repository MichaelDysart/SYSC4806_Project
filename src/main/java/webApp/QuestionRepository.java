package webApp;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import survey.OpenEndedQuestion;

@RepositoryRestResource(collectionResourceRel = "survey", path = "survey")
public interface QuestionRepository extends PagingAndSortingRepository<OpenEndedQuestion, Long> {
}
