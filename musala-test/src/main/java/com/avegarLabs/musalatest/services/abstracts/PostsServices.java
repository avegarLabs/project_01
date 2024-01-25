package com.avegarLabs.musalatest.services.abstracts;

import com.avegarLabs.musalatest.dto.PostResponse;
import com.avegarLabs.musalatest.dto.PostModel;
import com.avegarLabs.musalatest.dto.PostsListItems;

import java.util.List;

public interface PostsServices {

    PostResponse createPost(PostModel model);

    List<PostsListItems> getPostsToWall(int userId);

    void addLikeToPost( int postId, int userId);

    void removeLikeToPost(int postId,  int userId);



}
