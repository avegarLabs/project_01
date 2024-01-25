package com.avegarLabs.musalatest.services;

import com.avegarLabs.musalatest.dto.UserResponse;
import com.avegarLabs.musalatest.dto.UserModel;
import com.avegarLabs.musalatest.models.Users;
import com.avegarLabs.musalatest.repositories.UsersRepository;
import com.avegarLabs.musalatest.services.abstracts.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServicesImpementation implements UserServices {

    @Autowired
    UsersRepository repository;

    @Override
    public UserResponse createUser(UserModel model) {
        Users users = mapUserModelToUser(model);
        repository.save(users);
        return mapUserToUserListItem(users);
    }

    @Transactional
    @Override
    public void saveUserFiend(int userId, int friendId) {
        Users user = repository.getReferenceById(userId);
        Users friend = repository.getReferenceById(friendId);

        user.getFriends().add(friend);
        friend.getFriends().add(user);
        repository.save(user);
        repository.save(friend);
    }

    @Transactional
    @Override
    public void saveFollowedToUser(int userId, int followerId) {
        Users user = repository.getReferenceById(userId);
        Users follower = repository.getReferenceById(followerId);

        follower.getFollowed().add(user);
        repository.save(user);
    }


    public UserResponse mapUserToUserListItem(Users users){
        return UserResponse.builder()
                .useId(users.getId())
                .build();
    }

    private Users mapUserModelToUser(UserModel model){
        return Users.builder()
                .fullName(model.getFullName())
                .build();
    }

}
