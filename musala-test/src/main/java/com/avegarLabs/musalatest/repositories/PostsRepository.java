package com.avegarLabs.musalatest.repositories;

import com.avegarLabs.musalatest.models.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostsRepository extends JpaRepository<Posts, Integer> {

    List<Posts> getPostsByUserId(int userId);

    @Query("SELECT COUNT(l) FROM Posts p LEFT JOIN p.likes l WHERE p.id = :postId")
    int countLikesByPostId(@Param("postId") Integer postId);

    @Modifying
    @Query(value = "DELETE FROM likes WHERE post_id = :postId AND user_id = :userId", nativeQuery = true)
    void removeLikeNative(@Param("postId") int postId, @Param("userId") int userId);
}
