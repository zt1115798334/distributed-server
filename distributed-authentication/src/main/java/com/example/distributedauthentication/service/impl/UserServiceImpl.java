package com.example.distributedauthentication.service.impl;

import com.example.distributedauthentication.entity.User;
import com.example.distributedauthentication.repo.UserRepository;
import com.example.distributedauthentication.service.UserService;
import com.example.distributedauthentication.utils.UserUtils;
import com.example.distributedcommon.utils.Digests;
import com.example.distributedcommon.utils.Encodes;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang tong
 * date: 2020/12/21 17:17
 * description:
 */
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    private final ConcurrentHashMap<Long, Long> concurrentHashMap = new ConcurrentHashMap<>();

    @Override
    @Transactional(rollbackFor = RuntimeException.class, isolation = Isolation.READ_COMMITTED)
    public User save(User user) {
        Long id = user.getId();
        long generateKey = generateKey();
        User user1 = Optional.ofNullable(id).filter(i -> i != 0L)
                .map(i -> {
                    Optional<User> userOfDb = userRepository.findById(i);
                    User userOfNeed = userOfDb.map(u -> {
                        u.setUserName(user.getUserName());
                        return u;
                    }).orElseGet(() -> {
                        user.setId(generateKey);
                        user.setCreatedTime(currentDateTime);
                        user.setDeleteState(UN_DELETED);
                        return user;
                    });
                    return userRepository.save(userOfNeed);
                })
                .orElseGet(() -> {
                    user.setId(generateKey);
                    user.setCreatedTime(currentDateTime);
                    user.setDeleteState(UN_DELETED);
                    return userRepository.save(user);
                });
        concurrentHashMap.put(generateKey, generateKey);
        System.out.println("concurrentLinkedQueue = " + concurrentHashMap.size());
        return user1;
    }

    @Override
    public void createdUser(String account, String password, String userName, String phone, String email) {
        String salt = Encodes.encodeHex(Digests.generateSalt(Digests.SALT_SIZE));
        password = UserUtils.getEncryptPassword(account, password, salt);
        final User buildUser = User.builder()
                .account(account).password(password).salt(salt).userName(userName).phone(phone).email(email)
                .build();
        this.save(buildUser);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<User> findAllUser() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public Optional<User> findOptUserByAccount(String account) {
        return userRepository.findByAccountAndDeleteState(account, UN_DELETED);
    }

    @Override
    public User findUserByAccount(String account) {
        return this.findOptUserByAccount(account).orElseThrow(() -> new UsernameNotFoundException("查无此人"));
    }
}
