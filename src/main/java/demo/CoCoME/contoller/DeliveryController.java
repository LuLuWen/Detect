package demo.CoCoME.contoller;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import demo.CoCoME.model.Delivery;
import demo.CoCoME.model.Stock;
import demo.CoCoME.service.DeliveryService;
import demo.CoCoME.service.StockService;
import demo.annotation.CreateAnnotation;

@RestController
public class DeliveryController {

	@Autowired
	DeliveryService deliveryService;

	@PostMapping("/createDeliveryReport")
	public void createDeliveryReport(@RequestBody Delivery delivery) {
		deliveryService.saveDeliveryReport(delivery);
	}
	
	@CreateAnnotation
	@PutMapping("/updateDeliveryReport/{id}")
	public ResponseEntity<Delivery> updateDeliveryReport(@RequestBody Delivery delivery, @PathVariable int id) {
		try {
			Delivery existDelivery = deliveryService.getDeliveryReport(id);
			delivery.setId(id);         
			deliveryService.saveDeliveryReport(delivery);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
	}
}
