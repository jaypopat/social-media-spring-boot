-- Create the User table
CREATE TABLE user_table (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(255) NOT NULL,
                            email VARCHAR(255) NOT NULL UNIQUE,
                            password VARCHAR(255) NOT NULL
);

-- Create the Post table (assuming a basic structure)
CREATE TABLE post (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      content TEXT NOT NULL,
                      created_at TIMESTAMP NOT NULL,
                      user_id BIGINT,
                      FOREIGN KEY (user_id) REFERENCES user_table(id) ON DELETE CASCADE
);

-- Create the Comment table (assuming a basic structure)
CREATE TABLE comment (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         text TEXT NOT NULL,
                         user_id BIGINT,
                         post_id BIGINT,
                         FOREIGN KEY (user_id) REFERENCES user_table(id) ON DELETE CASCADE,
                         FOREIGN KEY (post_id) REFERENCES post(id) ON DELETE CASCADE
);

-- Create the Like table (assuming a basic structure)
CREATE TABLE user_like (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        user_id BIGINT,
                        post_id BIGINT,
                        FOREIGN KEY (user_id) REFERENCES user_table(id) ON DELETE CASCADE,
                        FOREIGN KEY (post_id) REFERENCES post(id) ON DELETE CASCADE
);

-- Create the user_following table for the ManyToMany relationship
CREATE TABLE user_following (
                                user_id BIGINT,
                                following_id BIGINT,
                                PRIMARY KEY (user_id, following_id),
                                FOREIGN KEY (user_id) REFERENCES user_table(id) ON DELETE CASCADE,
                                FOREIGN KEY (following_id) REFERENCES user_table(id) ON DELETE CASCADE
);
INSERT INTO user_table (id, name, email, password) VALUES (1, 'John Doe', 'john.doe@example.com', 'password123');
INSERT INTO user_table (id, name, email, password) VALUES (2, 'Jane Smith', 'jane.smith@example.com', 'password456');
INSERT INTO user_table (id, name, email, password) VALUES (3, 'Alice Johnson', 'alice.johnson@example.com', 'password789');
