package demo.CoCoME.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import demo.CoCoME.model.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {

}
