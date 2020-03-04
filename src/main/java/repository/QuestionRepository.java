package repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import survey.Question;

@RepositoryRestResource(collectionResourceRel = "survey", path = "survey")
public interface QuestionRepository extends PagingAndSortingRepository<Question, Long> {
}
