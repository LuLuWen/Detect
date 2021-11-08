package demo.CoCoME.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.CoCoME.model.Order;
import demo.CoCoME.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	OrderRepository orderRepository;
	
	public void saveOrder(Order order) {
		orderRepository.save(order);
	}
	
	public List<Order> listAllOrders() {
        return (List<Order>) orderRepository.findAll();
    }
}
