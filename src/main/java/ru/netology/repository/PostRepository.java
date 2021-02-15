package ru.netology.repository;
import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class PostRepository {
    private Map<Long, Post> posts = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    public PostRepository() {
        Post defaultPost = new Post(1L, "Hello");
        addPost(defaultPost.getId(), defaultPost);
        counter.getAndIncrement();
        Post defaultPost2 = new Post(2L, "World");
        addPost(defaultPost2.getId(), defaultPost2);
        counter.getAndIncrement();
    }

    private void addPost(long id, Post post) {
        posts.put(id, post);
    }

    public List<Post> all() {
        return new ArrayList<>(posts.values());
    }

    public Optional<Post> getById(long id) {
        Post post = posts.get(id);
        return Optional.ofNullable(post);
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
//            Создание нового поста
            post.setId(counter.incrementAndGet());
            addPost(post.getId(), post);
            return post;
        } else if (post.getId() != 0) {
            if (posts.containsKey(post.getId())) {
                Post changedPost = posts.get(post.getId());
                changedPost.setContent(post.getContent());
                return changedPost;
            }
        }
        return null;
    }

    public boolean removeById(long id) {
        if (posts.get(id) != null) {
            posts.remove(id);
            return true;
        } else {
            return false;
        }
    }
}
