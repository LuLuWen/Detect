package demo.logToDb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccessDataService {

	@Autowired
	private AccessDataRepository accessDataRepository;
	
	public void saveAccessData(AccessData accessData) {
		accessDataRepository.save(accessData);
    }
    
	public List<AccessData> listAllAccessDatas() {
        return (List<AccessData>) accessDataRepository.findAll();
    }
}
