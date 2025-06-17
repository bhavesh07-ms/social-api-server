package org.bhavesh.socialapiserver.storage;


import org.bhavesh.socialapiserver.model.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserStorage {
    public static final Map<String, User> users = new ConcurrentHashMap<>();
}