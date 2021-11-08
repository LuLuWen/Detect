package demo.CoCoME.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import demo.CoCoME.model.Payment;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, String> {

}
