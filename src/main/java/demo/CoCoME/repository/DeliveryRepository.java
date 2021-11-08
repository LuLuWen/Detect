package demo.CoCoME.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import demo.CoCoME.model.Delivery;

@Repository
public interface DeliveryRepository extends CrudRepository<Delivery, Integer> {

}
