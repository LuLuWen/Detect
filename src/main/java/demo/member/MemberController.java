package demo.member;

import java.util.List;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import demo.annotation.CreateAnnotation;
import demo.member.MemberAccount;
import demo.repo2.BookAuthorService;


@RestController
public class MemberController {

	@Autowired
	MemberService memberService;
	@Autowired
	BookAuthorService bookAuthorService;
	@CreateAnnotation("normal")
    @GetMapping("/listAllMembers")
    public List<MemberAccount> list() {
        return memberService.listAllMembers();
    }
    //@CreateAnnotation("normal")
    @GetMapping("/listMember/{id}")
    public ResponseEntity<MemberAccount> get(@PathVariable Integer id) {
        try {
        	MemberAccount member = memberService.getMember(id);
            return new ResponseEntity<MemberAccount>(member, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<MemberAccount>(HttpStatus.NOT_FOUND);
        }
    }
    
    @CreateAnnotation("normal")
    @PostMapping("/addMember")
    public void add(@RequestBody MemberAccount member) {
        memberService.saveMember(member);
        bookAuthorService.listAll();
    }
    @CreateAnnotation("normal")
    @PutMapping("/{id}")
    public ResponseEntity<MemberAccount> update(@RequestBody MemberAccount member, @PathVariable Integer id) {
        try {
        	MemberAccount existMember = memberService.getMember(id);
            member.setId(id);            
            memberService.saveMember(member);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/deleteMember/{id}")
    public void delete(@PathVariable Integer id) {
        memberService.deleteMember(id);
    }
}
