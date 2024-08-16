package com.bookstore.service;

import com.bookstore.exception.UserException;
import com.bookstore.model.Users;

public interface UserService {
    public Users registerUser(Users user) throws UserException;
    public Users getUserById(Integer id) throws UserException;
    public Users updateUser( Users user) throws UserException;
    public Users updateAdmin( Users user) throws UserException;
    public void disableUser(Integer id) throws UserException;
}
