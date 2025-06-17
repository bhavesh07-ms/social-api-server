package org.bhavesh.socialapiserver.model;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {
    private String id;
    private String content;
    private String createdByUser;//many-to-one post -> user
    private Set<String> likedByUser = new HashSet<>();

}