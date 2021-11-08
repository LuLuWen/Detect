package demo.join;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, Integer> {

	@Query("SELECT new demo.join.DeptEmpDto(d.name, e.name, e.email, e.address)"
			+ " FROM Department d LEFT JOIN d.employees e")
	List<DeptEmpDto> fetchEmpDeptDataLeftJoin();
	
	@Query("SELECT d.name, e.name, e.email, e.address "
		      + "FROM Department d INNER JOIN d.employees e")
	List<DeptEmpDto> fetchEmpDeptDataInnerJoin();
}
