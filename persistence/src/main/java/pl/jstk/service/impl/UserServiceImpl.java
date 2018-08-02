package pl.jstk.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jstk.entity.UserEntity;
import pl.jstk.mapper.UserMapper;
import pl.jstk.repository.UserRepository;
import pl.jstk.service.UserService;
import pl.jstk.to.UserTo;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserTo> findUserByName(String name) {
        List<UserEntity> entity = userRepository.findUsersByName(name);
        return UserMapper.map2To(entity);
    }

}
