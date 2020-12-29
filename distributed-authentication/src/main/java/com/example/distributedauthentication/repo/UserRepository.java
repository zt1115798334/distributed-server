package com.example.distributedauthentication.repo;

import com.example.distributedauthentication.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang tong
 * date: 2020/12/21 17:17
 * description:
 */
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByAccountAndDeleteState(String account, Integer deleteState);
}
