package demo.logToDb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogController {

	@Autowired
	private LogService logService;
	
    @PostMapping("/addLog")
    public void add(@RequestBody LogPattern logPattern) {
    	logService.saveLog(logPattern);
    }
}
