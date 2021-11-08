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

import demo.CoCoME.model.Stock;
import demo.CoCoME.service.StockService;
import demo.member.MemberAccount;

@RestController
public class StockController {

	@Autowired
	StockService stockService;

	@PostMapping("/createStockReport")
	public void createStockReport(@RequestBody Stock stock) {
		stockService.saveStockReport(stock);
	}
	
	@PutMapping("/updateStockReport/{id}")
	public ResponseEntity<Stock> updateStockReport(@RequestBody Stock stock, @PathVariable Integer id) {
		try {
			Stock existStock = stockService.getStock(id);
			stock.setId(id);            
			stockService.saveStockReport(stock);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
	}
}
