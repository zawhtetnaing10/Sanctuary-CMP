package com.zg.sanctuary.core.data.network

const val BASE_IP = "10.0.2.2"
//const val BASE_IP = "localhost"

const val BASE_URL = "http://$BASE_IP:8080"
const val SOCKET_PORT = 8080

const val ENDPOINT_GET_ALL_INTERESTS = "/api/interests"
const val ENDPOINT_LOGIN = "/api/login"
const val ENDPOINT_REGISTER = "/api/register"
const val ENDPOINT_UPDATE_USER = "/api/updateUser"

const val ENDPOINT_GET_USER_PROFILE = "/api/users"

const val ENDPOINT_POSTS = "/api/posts"
const val ENDPOINT_POSTS_BY_USER = "/api/posts_by_user"
const val ENDPOINT_POST_LIKE = "/api/post_like"
const val ENDPOINT_COMMENTS = "/api/comments"

const val ENDPOINT_FRIEND_REQUESTS = "/api/friend_requests"
const val ENDPOINT_ACCEPT_FRIEND_REQUEST = "/api/accept_friend_request"
const val ENDPOINT_FRIENDS = "/api/friends"

const val ENDPOINT_GET_CONVERSATIONS = "/api/conversations"
const val ENDPOINT_GET_CHAT_MESSAGE_HISTORY = "/api/chats"
