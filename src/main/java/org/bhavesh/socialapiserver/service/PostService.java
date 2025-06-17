package org.bhavesh.socialapiserver.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bhavesh.socialapiserver.dto.PostRequest;
import org.bhavesh.socialapiserver.exception.UserException;
import org.bhavesh.socialapiserver.model.Post;
import org.bhavesh.socialapiserver.model.User;
import org.bhavesh.socialapiserver.storage.PostStorage;
import org.bhavesh.socialapiserver.storage.UserStorage;
import org.springframework.security.core.userdetails.memory.UserAttribute;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
@AllArgsConstructor
public class PostService {




    public Post createPost(PostRequest request, Principal principal) {
        String username = principal.getName();
        User user = UserStorage.users.get(username);

        if (user == null) {
            throw new UserException("User not found");
        }

        String postId = UUID.randomUUID().toString();
        Post post = new Post(postId, request.getContent(), username, LocalDateTime.now(), new HashSet<>());

        PostStorage.posts.put(postId, post);
        log.info("User '{}' created a post with ID {}", username, postId);
        return post;
    }

    public void deletePost(String postId, Principal principal) {
        String username = principal.getName();
        Post post = PostStorage.posts.get(postId);

        if (post == null) {
            throw new UserException("Post not found");
        }

        if (!post.getAuthor().equals(username)) {
            throw new UserException("You can only delete your own posts");
        }

        PostStorage.posts.remove(postId);
        log.info("User '{}' deleted post with ID {}", username, postId);
    }

    public void likePost(String postId, Principal principal) {
        String username = principal.getName();
        Post post = PostStorage.posts.get(postId);

        if (post == null) {
            throw new UserException("Post not found");
        }

        if (post.getAuthor().equals(username)) {
            throw new UserException("You cannot like your own post");
        }


        log.info("User '{}' liked post with ID {}", username, postId);
    }

    public List<Post> listPosts() {
        return new ArrayList<>(PostStorage.posts.values());
    }
}
