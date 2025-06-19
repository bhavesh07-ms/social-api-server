package org.bhavesh.socialapiserver.controller;


import org.bhavesh.socialapiserver.dto.ApiResponse;
import org.bhavesh.socialapiserver.dto.PostRequest;
import org.bhavesh.socialapiserver.model.Post;
import org.bhavesh.socialapiserver.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Post>> createPost(@RequestBody PostRequest request, Principal principal) {
        Post post = postService.createPost(request, principal);
        return ResponseEntity.ok(new ApiResponse<>("Post created", post));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable("id") String id, Principal principal) {
        postService.deletePost(id, principal);
        return ResponseEntity.ok(new ApiResponse<>("Post deleted", null));
    }

    @PostMapping("like/{id}")
    public ResponseEntity<ApiResponse<Post>> likePost(@PathVariable("id") String id,Principal principal) {
        Post post = postService.likePost(id, principal);
        return ResponseEntity.ok(new ApiResponse<>("Posts fetched", post));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Post>>> getAllPosts() {
        List<Post> posts = postService.listPosts();
        return ResponseEntity.ok(new ApiResponse<>("Posts fetched", posts));
    }

    @GetMapping("/by-user/{username}")
    public ResponseEntity<ApiResponse<List<Post>>> getPostsByUser(@PathVariable("username") String username) {
        List<Post> userPosts = postService.listPostsByUser(username);
        return ResponseEntity.ok(new ApiResponse<>("Posts by user fetched", userPosts));
    }

    @GetMapping("/{id}/like-info")
    public ResponseEntity<ApiResponse<Map<String, List<String>>>> getPostLikes(@PathVariable("id") String id) {
        Map<String, List<String>> likeInfo = postService.getLikesInfo(id);
        return ResponseEntity.ok(new ApiResponse<>("Post likes fetched", likeInfo));
    }



}
