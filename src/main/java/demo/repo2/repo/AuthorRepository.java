package demo.repo2.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import demo.repo2.Author;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Integer> {

}
