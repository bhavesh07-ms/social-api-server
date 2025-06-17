package org.bhavesh.socialapiserver.storage;

import org.bhavesh.socialapiserver.model.Post;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PostStorage {
    public static final Map<String, Post> posts = new ConcurrentHashMap<>();
}
