package com.example.distributedauthentication.service.impl;

import com.example.distributedauthentication.entity.User;
import com.example.distributedauthentication.repo.UserRepository;
import com.example.distributedauthentication.service.UserService;
import com.example.distributedauthentication.utils.UserUtils;
import com.example.distributedcommon.utils.Digests;
import com.example.distributedcommon.utils.Encodes;
import com.google.common.collect.Queues;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

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

    private final ConcurrentLinkedQueue<Long> concurrentLinkedQueue = Queues.newConcurrentLinkedQueue();

    @Override
    @Transactional(rollbackFor = RuntimeException.class, isolation = Isolation.READ_COMMITTED)
    public User save(User user) {
        Long id = user.getId();
        User user1 = Optional.ofNullable(id).filter(i -> i != 0L)
                .map(i -> {
                    Optional<User> userOfDb = userRepository.findById(i);
                    User userOfNeed = userOfDb.map(u -> {
                        u.setUserName(user.getUserName());
                        return u;
                    }).orElseGet(() -> {
                        user.setId(generateKey());
                        user.setCreatedTime(currentDateTime);
                        user.setDeleteState(UN_DELETED);
                        return user;
                    });
                    return userRepository.save(userOfNeed);
                })
                .orElseGet(() -> {
                    user.setId(generateKey());
                    user.setCreatedTime(currentDateTime);
                    user.setDeleteState(UN_DELETED);
                    return userRepository.save(user);
                });
        concurrentLinkedQueue.add(user1.getId());
        System.out.println("concurrentLinkedQueue = " + concurrentLinkedQueue.size());
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
}
