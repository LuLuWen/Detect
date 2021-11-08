package demo.logToDb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class LogService {

	//@SuppressWarnings("unused")
	@Autowired
	private LogRepository logRepository;
	
	public void saveLog(LogPattern logPattern) {
		logRepository.save(logPattern);
    }
	
    public List<LogPattern> listAllLogs() {
        return (List<LogPattern>) logRepository.findAll();
    }

}
