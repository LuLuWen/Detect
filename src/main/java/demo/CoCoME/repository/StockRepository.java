package demo.CoCoME.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import demo.CoCoME.model.Stock;

@Repository
public interface StockRepository extends CrudRepository<Stock, Integer> {

}
