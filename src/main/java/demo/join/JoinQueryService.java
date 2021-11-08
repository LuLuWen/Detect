package demo.join;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class JoinQueryService {
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	/*@Autowired
	private EmployeeRepository employeeRepository;*/

	//@Scheduled(fixedRate = 5000)
	public List<DeptEmpDto> getDeptEmployeesLeftJoin() {
		List<DeptEmpDto> list = departmentRepository.fetchEmpDeptDataLeftJoin();
		list.forEach(l -> System.out.println(l));
		return list;
	}
	
	/*public List<DeptEmpDto> getDeptEmployeesInnerJoin() {
		List<Department> departments = (List<Department>) departmentRepository.findAll();
		List<Employee> employees = (List<Employee>) employeeRepository.findAll();
		List<DeptEmpDto> deptEmpDtos = new ArrayList<>();
		for(Department dep: departments) {
			for(Employee emp: employees) {
				if(dep.getId() == emp.getDepartment().getId()) {
					System.out.println("name : " + dep.getName());
					DeptEmpDto dto = new DeptEmpDto(dep.getName(), emp.getName(), emp.getEmail(), emp.getAddress());
					deptEmpDtos.add(dto);
				}
				
			}
		}
		//System.out.println("length" + deptEmpDtos.size());
		return deptEmpDtos;
	}*/
	
	public List<Department> getDept() {
		return (List<Department>) departmentRepository.findAll();
		
	}
	
	public List<DeptEmpDto> InnerJoin() {
		List<DeptEmpDto> list = departmentRepository.fetchEmpDeptDataInnerJoin();
		list.forEach(l -> System.out.println(l));
		return list;
	}
}
