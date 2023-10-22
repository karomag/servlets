package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// Stub
@Repository
public class PostRepository {
  private AtomicLong count;
  private ConcurrentHashMap<Long, Post> posts;

  public PostRepository() {
    count = new AtomicLong(0);
    posts = new ConcurrentHashMap<>();
  }

  public List<Post> all() {
    List<Post> list = new ArrayList<>();
    posts.entrySet().forEach(x -> list.add(x.getValue()));
    return list;
  }

  public Optional<Post> getById(long id) {
    return Optional.ofNullable(posts.get(id));
  }

  public Post save(Post post) {
    long currentID = post.getId();
    if ( currentID == 0) {
      long newId = count.incrementAndGet();
      post.setId(newId);
      posts.put(newId, post);
    } else {
      posts.replace(currentID, post);
    }
    return post;
  }

  public void removeById(long id) {
    if (posts.containsKey(id)) {
      posts.remove(id);
    } else {
      throw new NotFoundException("Nothing to delete. Repository doesn't contain current post");
    }

  }
}
