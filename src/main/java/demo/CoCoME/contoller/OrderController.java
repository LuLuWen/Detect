package demo.CoCoME.contoller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import demo.CoCoME.model.Order;
import demo.CoCoME.service.OrderService;
import demo.CoCoME.service.ProductService;
import demo.CoCoME.service.StockService;
import demo.annotation.CreateAnnotation;

@RestController
public class OrderController {

	@Autowired
	ProductService productService;
	
	@Autowired
	StockService stockService;
	
	@Autowired
	OrderService orderService;
	
	@CreateAnnotation
	@PostMapping("/orderProduct")
	public void orderProduct(@RequestBody Order order) {
		productService.listAllProducts();
		stockService.listAllStocks();
		orderService.saveOrder(order);
	}
	
	@GetMapping("/receiveOrderedProduct")
	public List<Order> receiveOrderedProduct(){
		return orderService.listAllOrders();
	}
}
