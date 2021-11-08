package demo.logToDb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccessDataController {

	@Autowired
	private AccessDataService accessDataService;
	
    @PostMapping("/addAccessData")
    public void add(@RequestBody AccessData accessData) {
    	accessDataService.saveAccessData(accessData);
    }
}
