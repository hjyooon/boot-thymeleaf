package idu.cs.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import idu.cs.domain.User;
import idu.cs.exception.ResourceNotFoundException;
import idu.cs.repository.UserRepository;

@Controller //@Component, @Service, @Repository
//Spring Framework에게 이 클래스로부터 작성된 객체는 controller역할을 함을 알려줌
//Spring이 이클래스로부터 Bean객체를 생성해서 등록할 수 있음.
public class UserController {
	@Autowired UserRepository userRepo; // Dependency Injection
	
	@GetMapping("/")
	public String home(Model model) {
		return "index";
	}
	
	@GetMapping("/user-login")
	public String getLoginFrom(Model model) {
		return "login";
	}
	
	@PostMapping("/login")
	public String loginUser(@Valid User user, HttpSession session) {
		System.out.println("login process : " + user.getUserId());
		User sessionUser = userRepo.findByUserId(user.getUserId());
		if(sessionUser == null) {
			System.out.println("id error");
			return "redirect:/user-login";
		}
		if(!sessionUser.getUserPw().equals(user.getUserPw())) {
			System.out.println("pw error");
			return "redirect:/user-login";
		}
		//userRepo.save(user);
		session.setAttribute("user", sessionUser);
		return "redirect:/";
	}
	@GetMapping("/logout")
	public String logoutUser(HttpSession session) {
		session.removeAttribute("user");
		//session.invalidate();
	return "redirect:/";
	}
	
	@GetMapping("/user-regist-form")
	public String getRegForm(Model model) {
		return "register";
	}
	
	
	@GetMapping("/users")
	public String getAllUser(Model model) {
		model.addAttribute("users", userRepo.findAll());
		return "userlist";
	}
	
	@PostMapping("/users")
	public String createUser(@Valid User user, Model model) {
		if(userRepo.save(user) != null)
			System.out.println("Database 등록");
		else
			System.out.println("Database등록 실패");
		model.addAttribute("users", userRepo.findAll());
		return "redirect:/users";
	}	
	@GetMapping("/users/{id}")
	public String getUserById(@PathVariable(value = "id") Long userId, Model model)
			throws ResourceNotFoundException {
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
		model.addAttribute("user", user);
		
		return "user";
		//return ResponseEntity.ok().body(user);
	}
	
	@GetMapping("/users/fn")
	public String getUserByName(@Param(value = "name") String name, Model model)
			throws ResourceNotFoundException {
		List<User> users = userRepo.findByName(name);
		model.addAttribute("users", users);
		return "userlist";
	}
	
	@PutMapping("/users/{id}")
	public String updateUser(@PathVariable(value = "id") Long userId, @Valid User userDetails, Model model) {
		User user = userRepo.findById(userId).get(); // user는 DB로 부터 읽어온 객체
		user.setName(userDetails.getName()); // userDetails는 전송한 객체
		user.setCompany(userDetails.getCompany());
		userRepo.save(user);
		model.addAttribute("users", userRepo.findAll());
		return "redirect:/users";
	}
	
	@DeleteMapping("/users/{id}")
	public String deleteUser(@PathVariable(value = "id") Long userId, Model model) {
		User user = userRepo.findById(userId).get();
		userRepo.delete(user);
		model.addAttribute("name", user.getName());
		return "user-deleted";
	}
}
