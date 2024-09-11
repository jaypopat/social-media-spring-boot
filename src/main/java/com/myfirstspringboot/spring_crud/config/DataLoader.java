    package com.myfirstspringboot.spring_crud.config;


    import com.myfirstspringboot.spring_crud.model.Comment;
    import com.myfirstspringboot.spring_crud.model.Post;
    import com.myfirstspringboot.spring_crud.model.User;
    import com.myfirstspringboot.spring_crud.repository.CommentRepository;
    import com.myfirstspringboot.spring_crud.repository.LikeRepository;
    import com.myfirstspringboot.spring_crud.repository.PostRepository;
    import com.myfirstspringboot.spring_crud.repository.UserRepository;
    import org.springframework.boot.CommandLineRunner;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;

    import java.time.LocalDateTime;

    @Configuration
    public class DataLoader {

        @Bean
        public CommandLineRunner loadData(
                UserRepository userRepository,
                PostRepository postRepository,
                CommentRepository commentRepository,
                LikeRepository likeRepository) {
            return args -> {
                commentRepository.deleteAll();
                likeRepository.deleteAll();
                postRepository.deleteAll();
                userRepository.deleteAll();

                // Sample users
                User user1 = new User();
                user1.setName("John Doe");
                user1.setEmail("john.doe@example.com");
                user1.setPassword("password123");

                User user2 = new User();
                user2.setName("Jane Smith");
                user2.setEmail("jane.smith@example.com");
                user2.setPassword("password456");

                // Save sample users to the database
                user1 = userRepository.save(user1);
                user2 = userRepository.save(user2);

                // Sample posts
                Post post1 = new Post();
                post1.setContent("This is the first post by John.");
                post1.setCreatedAt(LocalDateTime.now());
                post1.setUser(user1);

                Post post2 = new Post();
                post2.setContent("This is the second post by John.");
                post2.setCreatedAt(LocalDateTime.now());
                post2.setUser(user1);

                Post post3 = new Post();
                post3.setContent("This is a post by Jane.");
                post3.setCreatedAt(LocalDateTime.now());
                post3.setUser(user2);

                // Save sample posts to the database
                post1 = postRepository.save(post1);
                post2 = postRepository.save(post2);
                post3 = postRepository.save(post3);

                // Sample comments
                Comment comment1 = new Comment();
                comment1.setText("This is a comment on John's first post.");
                comment1.setUser(user2);
                comment1.setPost(post1);

                Comment comment2 = new Comment();
                comment2.setText("Another comment on John's post.");
                comment2.setUser(user1);
                comment2.setPost(post1);

                Comment comment3 = new Comment();
                comment3.setText("This is a comment on Jane's post.");
                comment3.setUser(user1);
                comment3.setPost(post3);

                // Save sample comments to the database
                commentRepository.save(comment1);
                commentRepository.save(comment2);
                commentRepository.save(comment3);
            };
        }
    }
