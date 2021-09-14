package com.example.testtask.services;

import com.example.testtask.dao.UserDao;
import com.example.testtask.dto.ResponseBase;
import com.example.testtask.dto.requests.user.RegisterUserRequest;
import com.example.testtask.dto.responses.UserResponse;
import com.example.testtask.exception.ProjectErrorCode;
import com.example.testtask.exception.ProjectException;
import com.example.testtask.mapper.DtoMapStruct;
import com.example.testtask.models.Session;
import com.example.testtask.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = ProjectException.class)
public class UserService extends ValidationService{
    @Autowired
    private DtoMapStruct dtoMapStruct;
    @Autowired
    private UserDao userDao;

    public UserResponse register(final String cookie, final RegisterUserRequest request) throws ProjectException {
        cookieNotNullAndNotEmpty(cookie);
        requestNotNull(request);
        final User user = dtoMapStruct.toUser(request);
        userNotNull(user);
        final Session session = new Session(user, cookie);
        user.setSession(session);
        userDao.save(user);
        return dtoMapStruct.toUserResponse(user);
    }


}
