"use client";

import React, { useState, useEffect } from "react";
import axios from "axios";
import {
  UserPlus,
  UserMinus,
  Heart,
  MessageCircle,
  Send,
  Search,
  User,
  Edit,
  Trash,
} from "lucide-react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Textarea } from "@/components/ui/textarea";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import {
  Card,
  CardHeader,
  CardContent,
  CardFooter,
} from "@/components/ui/card";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";

const API = "http://localhost:3000/crud";

export default function App() {
  const [posts, setPosts] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [showComments, setShowComments] = useState({});
  const [users, setUsers] = useState([]);
  const [followingUsers, setFollowingUsers] = useState([]);
  const [searchKeyword, setSearchKeyword] = useState("");
  const [isLoadingUsers, setIsLoadingUsers] = useState(true);
  const [currentUser, setCurrentUser] = useState(3);
  const [currentUserObj, setCurrentUserObj] = useState(null);
  const [newPostContent, setNewPostContent] = useState("");
  const [newCommentContent, setNewCommentContent] = useState("");
  const [editingPost, setEditingPost] = useState(null);

  useEffect(() => {
    fetchCurrentUserFeed();
    fetchUsers();
    fetchFollowing();
    fetchCurrentUser();
  }, [currentUser]);

  const fetchCurrentUser = async () => {
    try {
      const response = await axios.get(`${API}/users/${currentUser}`);
      setCurrentUserObj(response.data);
    } catch (error) {
      console.error("Error fetching current user:", error);
      setCurrentUserObj(null);
    }
  };

  const fetchUsers = async () => {
    try {
      const response = await axios.get(`${API}/users`);
      setUsers(response.data.filter((user) => user.id !== currentUser));
    } catch (error) {
      console.error("Error fetching users:", error);
      setUsers([]);
    } finally {
      setIsLoadingUsers(false);
    }
  };

  const fetchFollowing = async () => {
    try {
      const response = await axios.get(`${API}/users/${currentUser}/following`);
      setFollowingUsers(response.data);
    } catch (error) {
      console.error("Error fetching following users:", error);
      setFollowingUsers([]);
    }
  };

  const fetchCurrentUserFeed = async () => {
    try {
      const response = await axios.get(`${API}/users/${currentUser}/feed`);
      setPosts(response.data);
    } catch (error) {
      console.error("Error fetching current user's feed:", error);
      setPosts([]);
    } finally {
      setIsLoading(false);
    }
  };

  const searchUsers = async (keyword) => {
    if (keyword) {
      try {
        const response = await axios.get(`${API}/users/search`, {
          params: { keyword },
        });
        setUsers(response.data.filter((user) => user.id !== currentUser));
      } catch (error) {
        console.error("Error searching users:", error);
        setUsers([]);
      }
    } else {
      fetchUsers();
    }
  };

  const getComments = async (postId) => {
    try {
      const response = await axios.get(`${API}/posts/${postId}/comments`);
      return response.data;
    } catch (error) {
      console.error("Error fetching comments:", error);
      return [];
    }
  };

  const toggleComments = async (postId) => {
    if (showComments[postId]) {
      setShowComments((prevState) => ({
        ...prevState,
        [postId]: false,
      }));
    } else {
      const comments = await getComments(postId);
      setShowComments((prevState) => ({
        ...prevState,
        [postId]: comments,
      }));
    }
  };

  const toggleLike = async (postId) => {
    try {
      const existingLike = posts
        .find((post) => post.id === postId)
        ?.likes.find((like) => like.user.id === currentUser);
      if (existingLike) {
        await axios.delete(`${API}/likes/${existingLike.id}`);
        setPosts((prevPosts) =>
          prevPosts.map((post) =>
            post.id === postId
              ? {
                  ...post,
                  likes: post.likes.filter(
                    (like) => like.id !== existingLike.id,
                  ),
                }
              : post,
          ),
        );
      } else {
        const likeContent = {
          userId: currentUser,
          postId: postId,
        };
        const response = await axios.post(`${API}/likes`, likeContent);
        setPosts((prevPosts) =>
          prevPosts.map((post) =>
            post.id === postId
              ? { ...post, likes: [...post.likes, response.data] }
              : post,
          ),
        );
      }
    } catch (error) {
      console.error("Error toggling like:", error);
    }
  };

  const createPost = async () => {
    try {
      const postContent = {
        content: newPostContent,
        userId: currentUser,
      };
      const response = await axios.post(`${API}/posts`, postContent);
      setPosts([response.data, ...posts]);
      setNewPostContent("");
    } catch (error) {
      console.error("Error creating post:", error);
    }
  };

  const updatePost = async (postId, content) => {
    try {
      const postContent = {
        content,
        userId: currentUser,
      };
      const response = await axios.put(`${API}/posts/${postId}`, postContent);
      setPosts((prevPosts) =>
        prevPosts.map((post) =>
          post.id === postId
            ? { ...post, content: response.data.content }
            : post,
        ),
      );
      setEditingPost(null);
    } catch (error) {
      console.error("Error updating post:", error);
    }
  };

  const deletePost = async (postId) => {
    try {
      await axios.delete(`${API}/posts/${postId}`);
      setPosts((prevPosts) => prevPosts.filter((post) => post.id !== postId));
    } catch (error) {
      console.error("Error deleting post:", error);
    }
  };

  const createComment = async (postId) => {
    try {
      const commentContent = {
        text: newCommentContent,
        userId: currentUser,
      };
      const response = await axios.post(`${API}/comments`, commentContent);
      setShowComments((prevState) => ({
        ...prevState,
        [postId]: [...(prevState[postId] || []), response.data],
      }));
      setNewCommentContent("");
    } catch (error) {
      console.error("Error creating comment:", error);
    }
  };

  const followUser = async (userId) => {
    try {
      await axios.post(`${API}/users/${currentUser}/follow/${userId}`);
      const followedUser = users.find((u) => u.id === userId);
      setFollowingUsers([...followingUsers, followedUser]);
      setUsers(users.filter((u) => u.id !== userId));
    } catch (error) {
      console.error("Error following user:", error);
    }
  };

  const unfollowUser = async (userId) => {
    try {
      await axios.post(`${API}/users/${currentUser}/unfollow/${userId}`);
      const unfollowedUser = followingUsers.find((u) => u.id === userId);
      setFollowingUsers(followingUsers.filter((u) => u.id !== userId));
      setUsers([...users, unfollowedUser]);
    } catch (error) {
      console.error("Error unfollowing user:", error);
    }
  };

  if (isLoading || isLoadingUsers || !currentUserObj) {
    return (
      <div className="flex items-center justify-center h-screen">
        Loading...
      </div>
    );
  }

  return (
    <div className="flex flex-col min-h-screen bg-gray-100 dark:bg-gray-900">
      <header className="bg-white dark:bg-gray-800 shadow-sm">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-4 flex items-center justify-between">
          <h1 className="text-2xl font-bold text-gray-900 dark:text-white">
            Social Media App
          </h1>
          <div className="flex items-center space-x-4">
            <Avatar>
              <AvatarImage
                src={`https://api.dicebear.com/6.x/initials/svg?seed=${currentUserObj.name}`}
              />
              <AvatarFallback>{currentUserObj.name.charAt(0)}</AvatarFallback>
            </Avatar>
            <Select
              value={currentUser.toString()}
              onValueChange={(value) => setCurrentUser(Number(value))}
            >
              <SelectTrigger className="w-[180px]">
                <SelectValue placeholder="Select user" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem
                  key={currentUserObj.id}
                  value={currentUserObj.id.toString()}
                >
                  {currentUserObj.name}
                </SelectItem>
                {users.map((user) => (
                  <SelectItem key={user.id} value={user.id.toString()}>
                    {user.name}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
          </div>
        </div>
      </header>

      <main className="flex-grow max-w-7xl w-full mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
          <div className="md:col-span-2 space-y-6">
            <Card>
              <CardHeader>
                <h2 className="text-lg font-semibold">Create a Post</h2>
              </CardHeader>
              <CardContent>
                <Textarea
                  placeholder="What's on your mind?"
                  value={newPostContent}
                  onChange={(e) => setNewPostContent(e.target.value)}
                  className="min-h-[100px]"
                />
              </CardContent>
              <CardFooter>
                <Button onClick={createPost} className="ml-auto">
                  Post
                </Button>
              </CardFooter>
            </Card>

            {posts.length > 0 ? (
              posts.map((post) => (
                <Card key={post.id}>
                  <CardHeader>
                    <div className="flex items-center justify-between">
                      <div className="flex items-center space-x-4">
                        <Avatar>
                          <AvatarImage
                            src={`https://api.dicebear.com/6.x/initials/svg?seed=${post.user.name}`}
                          />
                          <AvatarFallback>
                            {post.user.name.charAt(0)}
                          </AvatarFallback>
                        </Avatar>
                        <div>
                          <p className="font-semibold">{post.user.name}</p>
                          <p className="text-sm text-gray-500 dark:text-gray-400">
                            {new Date(post.createdAt).toLocaleString()}
                          </p>
                        </div>
                      </div>
                      {post.user.id === currentUser && (
                        <div className="flex space-x-2">
                          <Button
                            variant="ghost"
                            size="sm"
                            onClick={() => setEditingPost(post.id)}
                          >
                            <Edit className="w-4 h-4" />
                          </Button>
                          <Button
                            variant="ghost"
                            size="sm"
                            onClick={() => deletePost(post.id)}
                          >
                            <Trash className="w-4 h-4" />
                          </Button>
                        </div>
                      )}
                    </div>
                  </CardHeader>
                  <CardContent>
                    {editingPost === post.id ? (
                      <div className="flex items-center space-x-2">
                        <Input
                          value={post.content}
                          onChange={(e) =>
                            setPosts(
                              posts.map((p) =>
                                p.id === post.id
                                  ? { ...p, content: e.target.value }
                                  : p,
                              ),
                            )
                          }
                        />
                        <Button
                          onClick={() => updatePost(post.id, post.content)}
                        >
                          Save
                        </Button>
                      </div>
                    ) : (
                      <p>{post.content}</p>
                    )}
                  </CardContent>
                  <CardFooter className="flex justify-between">
                    <Button
                      variant="ghost"
                      size="sm"
                      onClick={() => toggleLike(post.id)}
                      className={
                        post.likes.some((like) => like.user.id === currentUser)
                          ? "text-red-500"
                          : ""
                      }
                    >
                      <Heart className="w-5 h-5 mr-2" />
                      {post.likes.length}
                    </Button>
                    <Button
                      variant="ghost"
                      size="sm"
                      onClick={() => toggleComments(post.id)}
                    >
                      <MessageCircle className="w-5 h-5 mr-2" />
                      {showComments[post.id] ? "Hide" : "View"} Comments
                    </Button>
                  </CardFooter>
                  {showComments[post.id] && (
                    <CardContent>
                      <div className="space-y-4">
                        {showComments[post.id].map((comment) => (
                          <div
                            key={comment.id}
                            className="flex items-start space-x-4"
                          >
                            <Avatar>
                              <AvatarImage
                                src={`https://api.dicebear.com/6.x/initials/svg?seed=${comment.user.name}`}
                              />
                              <AvatarFallback>
                                {comment.user.name.charAt(0)}
                              </AvatarFallback>
                            </Avatar>
                            <div className="flex-grow">
                              <p className="font-semibold">
                                {comment.user.name}
                              </p>
                              <p>{comment.text}</p>
                            </div>
                          </div>
                        ))}
                        <div className="flex items-center space-x-2">
                          <Input
                            placeholder="Write a comment..."
                            value={newCommentContent}
                            onChange={(e) =>
                              setNewCommentContent(e.target.value)
                            }
                          />
                          <Button onClick={() => createComment(post.id)}>
                            <Send className="w-4 h-4" />
                          </Button>
                        </div>
                      </div>
                    </CardContent>
                  )}
                </Card>
              ))
            ) : (
              <p className="text-center text-gray-500 dark:text-gray-400">
                No posts available.
              </p>
            )}
          </div>

          <div className="space-y-6">
            <Card>
              <CardHeader>
                <h2 className="text-lg font-semibold">Users</h2>
              </CardHeader>
              <CardContent>
                <div className="relative">
                  <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400" />
                  <Input
                    type="text"
                    placeholder="Search users..."
                    value={searchKeyword}
                    onChange={(e) => {
                      setSearchKeyword(e.target.value);
                      searchUsers(e.target.value);
                    }}
                    className="pl-10"
                  />
                </div>
              </CardContent>
              <CardContent>
                {users.length > 0 ? (
                  <div className="space-y-4">
                    {users.map((user) => (
                      <div
                        key={user.id}
                        className="flex items-center justify-between"
                      >
                        <div className="flex items-center space-x-4">
                          <Avatar>
                            <AvatarImage
                              src={`https://api.dicebear.com/6.x/initials/svg?seed=${user.name}`}
                            />
                            <AvatarFallback>
                              {user.name.charAt(0)}
                            </AvatarFallback>
                          </Avatar>
                          <p className="font-semibold">{user.name}</p>
                        </div>
                        <Button
                          variant="outline"
                          size="sm"
                          onClick={() => followUser(user.id)}
                        >
                          <UserPlus className="w-4 h-4 mr-2" />
                          Follow
                        </Button>
                      </div>
                    ))}
                  </div>
                ) : (
                  <p className="text-center text-gray-500 dark:text-gray-400">
                    No users found.
                  </p>
                )}
              </CardContent>
            </Card>

            <Card>
              <CardHeader>
                <h2 className="text-lg font-semibold">Following</h2>
              </CardHeader>
              <CardContent>
                {followingUsers.length > 0 ? (
                  <div className="space-y-4">
                    {followingUsers.map((user) => (
                      <div
                        key={user.id}
                        className="flex items-center justify-between"
                      >
                        <div className="flex items-center space-x-4">
                          <Avatar>
                            <AvatarImage
                              src={`https://api.dicebear.com/6.x/initials/svg?seed=${user.name}`}
                            />
                            <AvatarFallback>
                              {user.name.charAt(0)}
                            </AvatarFallback>
                          </Avatar>
                          <p className="font-semibold">{user.name}</p>
                        </div>
                        <Button
                          variant="outline"
                          size="sm"
                          onClick={() => unfollowUser(user.id)}
                        >
                          <UserMinus className="w-4 h-4 mr-2" />
                          Unfollow
                        </Button>
                      </div>
                    ))}
                  </div>
                ) : (
                  <p className="text-center text-gray-500 dark:text-gray-400">
                    You're not following anyone yet.
                  </p>
                )}
              </CardContent>
            </Card>
          </div>
        </div>
      </main>
    </div>
  );
}
