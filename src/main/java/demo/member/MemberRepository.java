package demo.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;
//import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

//@RestResource
//@RepositoryRestResource(path = "member")
@Repository
public interface MemberRepository extends CrudRepository<MemberAccount, Integer> {

}
