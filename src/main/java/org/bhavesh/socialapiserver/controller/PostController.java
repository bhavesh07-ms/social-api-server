package org.bhavesh.socialapiserver.controller;


import lombok.RequiredArgsConstructor;
import org.bhavesh.socialapiserver.dto.ApiResponse;
import org.bhavesh.socialapiserver.dto.PostRequest;
import org.bhavesh.socialapiserver.model.Post;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bhavesh.springbootjwtsocialapi.service.PostService;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private PostService postService;

    @PostMapping
    public ResponseEntity<ApiResponse<Post>> createPost(@RequestBody PostRequest request, Principal principal) {
        Post post = postService.createPost(request, principal);
        return ResponseEntity.ok(new ApiResponse<>("Post created", post));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable String id, Principal principal) {
        postService.deletePost(id, principal);
        return ResponseEntity.ok(new ApiResponse<>("Post deleted", null));
    }

    @PostMapping("/like/{id}")
    public ResponseEntity<ApiResponse<Void>> likePost(@PathVariable String id, Principal principal) {
        postService.likePost(id, principal);
        return ResponseEntity.ok(new ApiResponse<>("Post liked", null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Post>>> getAllPosts() {
        List<Post> posts = postService.listPosts();
        return ResponseEntity.ok(new ApiResponse<>("Posts fetched", posts));
    }
}
