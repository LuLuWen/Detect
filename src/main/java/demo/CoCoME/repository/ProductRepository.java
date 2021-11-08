package demo.CoCoME.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import demo.CoCoME.model.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

}
