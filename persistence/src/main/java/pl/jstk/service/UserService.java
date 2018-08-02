package pl.jstk.service;

import pl.jstk.to.UserTo;

import java.util.List;

public interface UserService {

    List<UserTo> findUserByName(String name);
}
