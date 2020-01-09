package com.woc.am.persistence.jpa.repository;

import com.woc.am.persistence.hibernate.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    List<User> findByUserName(String userName);
    User findByLoginId(String loginId);
    User findByLoginIdAndPassword(String loginId, String password);
    List<User> findByUserNameContains(String userName);
}
