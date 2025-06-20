package org.bhavesh.socialapiserver.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bhavesh.socialapiserver.dto.PostRequest;
import org.bhavesh.socialapiserver.exception.UserException;
import org.bhavesh.socialapiserver.model.Post;
import org.bhavesh.socialapiserver.model.User;
import org.bhavesh.socialapiserver.storage.PostStorage;
import org.bhavesh.socialapiserver.storage.UserStorage;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class PostService {



    public Post createPost(PostRequest request, Principal principal) {
        if (principal == null || principal.getName() == null) {
            throw new UserException("User principal is missing or invalid");
        }

        String username = principal.getName();
        log.info("Principal username: {}", username);

        User user = UserStorage.users.get(username);
        //user null check
        if (user == null) {
            throw new UserException("User not found for username: " + username);
        }

        String postId = UUID.randomUUID().toString();
        Post post = new Post(postId, request.getContent(), username, LocalDateTime.now(), new HashSet<>());

        PostStorage.posts.put(postId, post);
        log.info("User '{}' created a post with ID {}", username, postId);
        return post;
    }


    public void deletePost(String postId, Principal principal) {
        // Check if the principal is null or if the name is null
        if (principal == null || principal.getName() == null) {
            throw new UserException("User principal is missing or invalid");
        }
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

    public Post likePost(String postId, Principal principal) {
        if (principal == null || principal.getName() == null) {
            throw new UserException("User principal is missing or invalid");
        }
        String username = principal.getName();

        User user = UserStorage.users.get(username);
        if (user == null) {
            throw new UserException("User not found for username: " + username);
        }

        Post post = PostStorage.posts.get(postId);
        if (post == null) {
            throw new UserException("Post not found for ID: " + postId);
        }
        // Check if the user has already liked the post maintaining thread safety
        synchronized (post) {
            Set<String> likedBy = post.getLikedByUser();
            boolean added = likedBy.add(user.getUsername());

            if (!added) {
                throw new UserException("You have already liked this post");
            }

            post.setLikedByUser(likedBy);
            PostStorage.posts.put(postId, post);
        }

        log.info("User '{}' (ID: {}) liked post with ID {}", username, user.getUserid(), postId);
        return post;
    }


    public List<Post> listPosts() {
        return new ArrayList<>(PostStorage.posts.values());
    }

    public List<Post> listPostsByUser(String username) {
        return PostStorage.posts.values().stream()
                .filter(post -> post.getAuthor().equals(username))
                .collect(Collectors.toList());
    }

    public Map<String, List<String>> getLikesInfo(String postId) {
        Post post = PostStorage.posts.get(postId);
        if (post == null) {
            throw new UserException("Post not found");
        }

        List<String> likedUsernames = new ArrayList<>(post.getLikedByUser());
        String key = "No of Likes: " + likedUsernames.size();

        Map<String, List<String>> result = new HashMap<>();
        result.put(key, likedUsernames);
        return result;
    }
}
