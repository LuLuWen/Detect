package demo.CoCoME.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.CoCoME.model.Product;
import demo.CoCoME.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	ProductRepository productRepository;
	
	public Product getProduct(Integer id) {
        return productRepository.findById(id).get();
    }
	
	public void saveProduct(Product product) {
		productRepository.save(product);
	}
	
	public List<Product> listAllProducts() {
        return (List<Product>) productRepository.findAll();
    }
}
