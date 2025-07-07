package com.zg.sanctuary.profile.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.zg.sanctuary.auth.domain.User
import com.zg.sanctuary.auth.presentation.personal_information.PersonalInformationActions
import com.zg.sanctuary.core.DIVIDER_COLOR
import com.zg.sanctuary.core.HINT_COLOR
import com.zg.sanctuary.core.MARGIN_56
import com.zg.sanctuary.core.MARGIN_CARD_MEDIUM_2
import com.zg.sanctuary.core.MARGIN_LARGE
import com.zg.sanctuary.core.MARGIN_MEDIUM
import com.zg.sanctuary.core.MARGIN_MEDIUM_2
import com.zg.sanctuary.core.MARGIN_SMALL
import com.zg.sanctuary.core.MARGIN_XLARGE
import com.zg.sanctuary.core.PROFILE_PICTURE_SIZE
import com.zg.sanctuary.core.TEXT_REGULAR_2X
import com.zg.sanctuary.core.TEXT_REGULAR_3X
import com.zg.sanctuary.core.TEXT_REGULAR_4X
import com.zg.sanctuary.core.presentation.components.InterestsSelection
import com.zg.sanctuary.core.presentation.components.SanctuaryPrimaryButton
import com.zg.sanctuary.interests.domain.Interest
import com.zg.sanctuary.posts.domain.Post
import com.zg.sanctuary.posts.presentation.components.PostListItem
import com.zg.sanctuary.posts.presentation.post_list.PostListAction
import com.zg.sanctuary.profile.presentation.components.ProfileAppbar
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import sanctuary.composeapp.generated.resources.Res
import sanctuary.composeapp.generated.resources.friend_request_pending
import sanctuary.composeapp.generated.resources.profile_friends
import sanctuary.composeapp.generated.resources.sample_profile_picture
import sanctuary.composeapp.generated.resources.sent_you_a_friend_request
import sanctuary.composeapp.generated.resources.log_out
import sanctuary.composeapp.generated.resources.posts

@Composable
fun ProfileRoute() {
    ProfileScreen()
}

@Composable
fun ProfileScreen() {
    Scaffold(
        topBar = {
            ProfileAppbar(
                onTapBack = {}
            )
        },
        containerColor = Color.White
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            item {
                // Profile Info
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = MARGIN_CARD_MEDIUM_2).padding(horizontal = MARGIN_MEDIUM_2)
                ) {
                    // Profile Image
                    Image(
                        painterResource(Res.drawable.sample_profile_picture),
                        contentDescription = null,
                        modifier = Modifier.size(PROFILE_PICTURE_SIZE)
                            .clip(shape = CircleShape)
                    )
                    Spacer(Modifier.width(MARGIN_MEDIUM_2))
                    // Profile Info
                    Column(verticalArrangement = Arrangement.spacedBy(MARGIN_MEDIUM)) {
                        Text("Liam", fontWeight = FontWeight.Bold, fontSize = TEXT_REGULAR_3X)
                        Text("liam888@gmail.com", fontSize = TEXT_REGULAR_2X, color = HINT_COLOR)
                        Text("Joined in 2025", fontSize = TEXT_REGULAR_2X, color = HINT_COLOR)
                    }
                }
            }

            // Friends or not button
            item {
                SanctuaryPrimaryButton(
                    title = stringResource(Res.string.profile_friends),
                    onClick = {},
                    modifier = Modifier.fillMaxWidth().padding(top = MARGIN_MEDIUM_2).padding(horizontal = MARGIN_MEDIUM_2)
                )
            }

            // Sent you a friend request button
            item {
                SanctuaryPrimaryButton(
                    title = stringResource(Res.string.sent_you_a_friend_request), onClick = {
                        // TODO: - Accept friend request
                    }, modifier = Modifier.fillMaxWidth().padding(top = MARGIN_MEDIUM_2).padding(horizontal = MARGIN_MEDIUM_2)
                )
            }

            // Friend request pending button
            item {
                SanctuaryPrimaryButton(
                    title = stringResource(Res.string.friend_request_pending),
                    onClick = {},
                    modifier = Modifier.fillMaxWidth().padding(top = MARGIN_MEDIUM_2).padding(horizontal = MARGIN_MEDIUM_2)
                )
            }

            // Logout Button
            item {
                SanctuaryPrimaryButton(
                    title = stringResource(Res.string.log_out), onClick = {
                        // TODO:- Implement logout
                    }, modifier = Modifier.fillMaxWidth().padding(top = MARGIN_MEDIUM_2).padding(horizontal = MARGIN_MEDIUM_2)
                )
            }

            // Interests
            item {
                InterestsSelection(
                    interests = dummyInterests,
                    allowSelection = false,
                    chosenInterests = setOf(),
                    onInterestPicked = {
                        // Do nothing
                    },
                    modifier = Modifier.padding(top = MARGIN_LARGE).padding(horizontal = MARGIN_MEDIUM_2)
                )
            }

            item {
                Spacer(Modifier.height(MARGIN_XLARGE))
            }

            // Posts Title
            item {
                Text(
                    stringResource(Res.string.posts),
                    fontSize = TEXT_REGULAR_4X,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = MARGIN_MEDIUM_2)
                )
            }

            item {
                Spacer(Modifier.height(MARGIN_MEDIUM_2))
            }

            // Posts by user.
            val samplePosts = generateSamplePosts()
            items(samplePosts.count()) { index ->
                val post = samplePosts[index]
                PostListItem(
                    post = post,
                    onLikeClicked = {
                        //onAction(PostListAction.OnTapLike(post.id))
                    }, onCommentClicked = {
                        //onAction(PostListAction.OnTapComment(post.id))
                    }, onShareClicked = {
                        //onAction(PostListAction.OnTapShare(post.id))
                    }, onPostClicked = {
                        //onAction(PostListAction.OnTapPost(post.id))
                    }, onUserClicked = {
                        //onAction(PostListAction.OnTapProfile(post.user.id))
                    })

                // Divider
                if (index < samplePosts.count() - 1) {
                    HorizontalDivider(color = DIVIDER_COLOR, modifier = Modifier.height(MARGIN_SMALL))
                }
            }


            // Bottom Margin
            item {
                Spacer(modifier = Modifier.height(MARGIN_56))
            }
        }
    }
}

// TODO: - Delete this after integrating with api
val dummyInterests = listOf<Interest>(
    Interest(1, "Swimming", "2025-12-20", ""),
    Interest(2, "Fishing", "2025-12-20", ""),
    Interest(3, "Surfing", "2025-12-20", ""),
    Interest(4, "Hunting", "2025-12-20", ""),
    Interest(5, "Football", "2025-12-20", ""),
    Interest(
        6, "Basketball", "2025-12-20", ""
    ),
    Interest(7, "Rugby", "2025-12-20", ""),
)

// TODO: - Delete this after integrating with api
// Function to generate sample posts
fun generateSamplePosts(): List<Post> {
    val users = listOf(
        User(
            id = 101,
            email = "john.doe@example.com",
            userName = "johndoe",
            fullName = "John Doe",
            createdAt = "2023-01-15T10:00:00Z",
            updatedAt = "2024-06-01T12:30:00Z",
            profileImageUrl = "https://placehold.co/150x150/FF5733/FFFFFF?text=JD",
            dob = "1990-05-20",
            accessToken = "dummy_token_101",
            interests = emptyList()
        ),
        User(
            id = 102,
            email = "jane.doe@example.com",
            userName = "janedoe",
            fullName = "Jane Doe",
            createdAt = "2023-02-20T11:00:00Z",
            updatedAt = "2024-06-05T14:45:00Z",
            profileImageUrl = "https://placehold.co/150x150/33FF57/FFFFFF?text=JD",
            dob = "1992-11-10",
            accessToken = "dummy_token_102",
            interests = emptyList()
        ),
        User(
            id = 103,
            email = "alice.w@example.com",
            userName = "alice_w",
            fullName = "Alice Wonderland",
            createdAt = "2023-03-25T12:00:00Z",
            updatedAt = "2024-06-10T16:00:00Z",
            profileImageUrl = "https://placehold.co/150x150/3357FF/FFFFFF?text=AW",
            dob = "1988-03-01",
            accessToken = "dummy_token_103",
            interests = emptyList()
        ),
        User(
            id = 104,
            email = "bob.s@example.com",
            userName = "bob_s",
            fullName = "Bob Smith",
            createdAt = "2023-04-30T13:00:00Z",
            updatedAt = "2024-06-15T18:15:00Z",
            profileImageUrl = "https://placehold.co/150x150/FF33A1/FFFFFF?text=BS",
            dob = "1995-07-22",
            accessToken = "dummy_token_104",
            interests = emptyList()
        ),
        User(
            id = 105,
            email = "charlie.p@example.com",
            userName = "charlie_p",
            fullName = "Charlie Parker",
            createdAt = "2023-05-05T14:00:00Z",
            updatedAt = "2024-06-20T20:00:00Z",
            profileImageUrl = "https://placehold.co/150x150/A133FF/FFFFFF?text=CP",
            dob = "1991-09-05",
            accessToken = "dummy_token_105",
            interests = emptyList()
        )
    )

    val samplePosts = mutableListOf<Post>()

    for (i in 1..20) {
        val user = users[i % users.size] // Cycle through users
        val postId = i
        val content = when (i % 5) {
            0 -> "Exploring new horizons! 🌍 #travel #adventure"
            1 -> "Coding session in full swing. 💻 #developer #kotlin"
            2 -> "Delicious homemade pasta tonight! 🍝 #foodie #cooking"
            3 -> "Beautiful sunset views. 🌅 #nature #photography"
            else -> "Just a thought for the day: stay positive! ✨ #motivation #inspiration"
        }
        val mediaUrl = "https://placehold.co/600x400/${(0..9).random()}${(0..9).random()}${(0..9).random()}/FFFFFF?text=Post+Image+${i}"
        val likedByUser = i % 3 == 0 // Every 3rd post is liked
        val likeCount = (50..500).random()
        val commentCount = (5..50).random()

        // Fix for date formatting: ensure month and day are always two digits
        val month = (1..9).random().toString().padStart(2, '0')
        val day = (1..28).random().toString().padStart(2, '0')
        val hour = (10..23).random().toString().padStart(2, '0')
        val minute = (10..59).random().toString().padStart(2, '0')
        val second = (10..59).random().toString().padStart(2, '0')

        val createdAt = "2024-$month-${day}T$hour:$minute:${second}Z"
        val updatedAt = createdAt // For simplicity, updated_at is same as created_at

        samplePosts.add(
            Post(
                id = postId,
                content = content,
                mediaUrl = mediaUrl,
                likedByUser = likedByUser,
                likeCount = likeCount,
                commentCount = commentCount,
                createdAt = createdAt,
                updatedAt = updatedAt,
                user = user
            )
        )
    }
    return samplePosts
}