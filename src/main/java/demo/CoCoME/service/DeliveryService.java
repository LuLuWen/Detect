package demo.CoCoME.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.CoCoME.model.Delivery;
import demo.CoCoME.repository.DeliveryRepository;



@Service
public class DeliveryService {

	@Autowired
	DeliveryRepository deliveryRepository;
	
	public void saveDeliveryReport(Delivery delivery) {
		deliveryRepository.save(delivery);
	}
	
	public Delivery getDeliveryReport(Integer delivery) {
        return deliveryRepository.findById(delivery).get();
    }
}
