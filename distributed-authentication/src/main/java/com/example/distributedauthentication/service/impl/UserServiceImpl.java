package com.example.distributedauthentication.service.impl;

import com.example.distributedauthentication.entity.User;
import com.example.distributedauthentication.repo.UserRepository;
import com.example.distributedauthentication.service.UserService;
import com.example.distributedauthentication.utils.UserUtils;
import com.example.distributedcommon.utils.Digests;
import com.example.distributedcommon.utils.Encodes;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

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

    @Override
    @Transactional(rollbackOn = RuntimeException.class)
    public User save(User user) {
        Long id = user.getId();
        return Optional.ofNullable(id).filter(i -> i != 0L)
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
