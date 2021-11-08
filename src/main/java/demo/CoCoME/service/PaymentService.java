package demo.CoCoME.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.CoCoME.model.Payment;
import demo.CoCoME.repository.PaymentRepository;

@Service
public class PaymentService {

	@Autowired
	PaymentRepository paymentRepository;
	
    public List<Payment> getPayment() {
        return (List<Payment>) paymentRepository.findAll();
    }


	
}
