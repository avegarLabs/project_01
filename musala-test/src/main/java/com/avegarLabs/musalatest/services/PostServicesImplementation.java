package com.avegarLabs.musalatest.services;

import com.avegarLabs.musalatest.dto.PostModel;
import com.avegarLabs.musalatest.dto.PostResponse;
import com.avegarLabs.musalatest.dto.PostsListItems;
import com.avegarLabs.musalatest.models.Posts;
import com.avegarLabs.musalatest.models.Users;
import com.avegarLabs.musalatest.repositories.PostsRepository;
import com.avegarLabs.musalatest.repositories.UsersRepository;
import com.avegarLabs.musalatest.services.abstracts.PostsServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class PostServicesImplementation implements PostsServices {

    @Autowired
    PostsRepository repository;

    @Autowired
    UsersRepository usersRepository;

    @Override
    public PostResponse createPost(PostModel model) {
       Posts posts = mapPostModelToPost(model);
       repository.save(posts);
       return mapPostToPostResponse(posts);
    }

    @Override
    public List<PostsListItems> getPostsToWall(int userId) {
        Users user = usersRepository.getReferenceById(userId);
        List<Posts> allPosts = new ArrayList<>();
        List<Posts> userPosts = repository.getPostsByUserId(user.getId());
        allPosts.addAll(userPosts);
        List<Posts> friendsPosts = user.getFriends().stream()
                .flatMap(friend -> repository.getPostsByUserId(friend.getId()).stream().filter(posts -> posts.getVisibility().equals("public")))
                .toList();

        List<Posts> followersPosts = user.getFollowed().stream()
                .flatMap(follower -> repository.getPostsByUserId(follower.getId()).stream().filter(posts -> posts.getVisibility().equals("public")))
                .toList();

        allPosts.addAll(friendsPosts);
        allPosts.addAll(followersPosts);

        List<Posts> sortedPosts = allPosts.stream()
                .sorted(Comparator.comparing(Posts::getPostedOn).reversed())
                .toList();

        return sortedPosts.stream().map(this::mapPostToPostListItem).toList();
    }

    @Transactional
    @Override
    public void addLikeToPost(int postId, int userId) {
        Users user = usersRepository.getReferenceById(userId);
        Posts post = repository.getReferenceById(postId);

        post.getLikes().add(user);
        repository.save(post);
    }

    @Transactional
    @Override
    public void removeLikeToPost(int postId, int userId) {
        repository.removeLikeNative(postId, userId);
        Users user = usersRepository.getReferenceById(userId);
        Posts post = repository.getReferenceById(postId);

        post.getLikes().remove(user);
        repository.save(post);
    }

    private PostResponse mapPostToPostResponse(Posts posts){
        return PostResponse.builder()
                .postId(posts.getId())
                .build();
    }

    private PostsListItems mapPostToPostListItem(Posts posts){
        int likes = repository.countLikesByPostId(posts.getId());
        return PostsListItems.builder()
                .text(posts.getText())
                .postedOn(posts.getPostedOn())
                .userFullName(posts.getUser().getFullName())
                .countLikes(likes)
                .build();
    }

    private Posts mapPostModelToPost(PostModel model){
        Users user = usersRepository.getReferenceById(model.getUserId());
        return Posts.builder()
                .user(user)
                .text(model.getText())
                .postedOn(LocalDateTime.now())
                .visibility(model.getVisibility())
                .build();
    }
}
