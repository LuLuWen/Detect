package demo.repo2.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import demo.repo2.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {

}
