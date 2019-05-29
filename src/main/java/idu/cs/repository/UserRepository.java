package idu.cs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import idu.cs.domain.User;

@Repository
public interface UserRepository 
	extends JpaRepository<User, Long> {
	List<User> findByName(String name);
	List<User> findByCompany(String company);
	User findByUserId(String userId);
}
