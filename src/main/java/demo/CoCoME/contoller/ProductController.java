package demo.CoCoME.contoller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import demo.CoCoME.model.Payment;
import demo.CoCoME.model.Product;
import demo.CoCoME.model.Stock;
import demo.CoCoME.service.PaymentService;
import demo.CoCoME.service.ProductService;
import demo.CoCoME.service.StockService;
import demo.annotation.CreateAnnotation;
import demo.member.MemberAccount;

@RestController
@RequestMapping(ProductController.ROOT_MAPPING)
public class ProductController {

	public static final String ROOT_MAPPING = "/api/product";
	
	@Autowired
	ProductService productService;
	
	@Autowired
	PaymentService paymentService;
	
	@Autowired
	StockService stockService;
	
	@CreateAnnotation
	//@GetMapping("/buyProduct/{id}")
	//@RequestMapping(value = "/buyProduct/{id}", method = RequestMethod.GET)
	@RequestMapping(value = "/search/{id}", method = RequestMethod.GET)
	public ResponseEntity<Product> buyProduct(@PathVariable Integer id) {
		try {
			List<Payment> paymentList = paymentService.getPayment();
			Product product = productService.getProduct(id);
            return new ResponseEntity<Product>(product, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }
	}
	
	@PutMapping("/changePrice/{id}")
	public ResponseEntity<Product> changePrice(@RequestBody Product product, @PathVariable Integer id) {
		try {
			Product existProduct = productService.getProduct(id);
			product.setId(id);            
			productService.saveProduct(product);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
	}
	
	@PutMapping("/sellProduct/{id}")
	public ResponseEntity<Product> sellProduct(@RequestBody Stock stock, @PathVariable Integer id) {
		try {
			Product existProduct = productService.getProduct(id);			            
			stockService.saveStockReport(stock);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
	}
}
