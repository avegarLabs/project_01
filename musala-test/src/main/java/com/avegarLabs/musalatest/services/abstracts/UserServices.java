package com.avegarLabs.musalatest.services.abstracts;

import com.avegarLabs.musalatest.dto.UserResponse;
import com.avegarLabs.musalatest.dto.UserModel;

public interface UserServices {

    UserResponse createUser(UserModel model);

    void saveUserFiend(int userId, int friendId);

    void saveFollowedToUser(int userId, int followerId);
}
