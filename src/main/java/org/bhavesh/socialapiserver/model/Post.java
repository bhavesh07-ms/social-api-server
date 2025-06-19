package org.bhavesh.socialapiserver.model;



import lombok.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

//@RedisHash("Post")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {
    private String id;
    private String content;
    private String author;//many-to-one post -> user
    private LocalDateTime createdAt;
    private Set<String> likedByUser = new HashSet<>();

}