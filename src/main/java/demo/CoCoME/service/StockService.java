package demo.CoCoME.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.CoCoME.model.Stock;
import demo.CoCoME.repository.StockRepository;


@Service
public class StockService {

	@Autowired
	StockRepository stockRepository;
	
	public void saveStockReport(Stock stock) {
		stockRepository.save(stock);
	}
	
	public Stock getStock(Integer id) {
        return stockRepository.findById(id).get();
    }

	public List<Stock> listAllStocks() {
        return (List<Stock>) stockRepository.findAll();
    }

}
