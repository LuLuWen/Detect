package demo.join;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.annotation.CreateAnnotation;
import demo.member.MemberAccount;

@RestController
public class JoinQueryController {

	@Autowired
	private JoinQueryService joinQueryService;

	//@Scheduled(fixedRate = 10000)
	@CreateAnnotation("join")
	@GetMapping("/dept/employees/left")
	public ResponseEntity<List<DeptEmpDto>> getDeptEmployeesLeftJoin() {
		return new ResponseEntity<List<DeptEmpDto>>(joinQueryService.getDeptEmployeesLeftJoin(), HttpStatus.OK);
	}
	
	/*@CreateAnnotation("employee")
	@GetMapping("/dept/employees/")
    public List<DeptEmpDto> list() {
        return joinQueryService.getDeptEmployeesInnerJoin();
    }*/
	
	@GetMapping("/dept")
    public List<Department> dept() {
        return joinQueryService.getDept();
    }
	
	@CreateAnnotation("left")
	@GetMapping("/dept/employees/inner")
	public ResponseEntity<List<DeptEmpDto>> InnerJoin() {
		return new ResponseEntity<List<DeptEmpDto>>(joinQueryService.InnerJoin(), HttpStatus.OK);
	}
}
