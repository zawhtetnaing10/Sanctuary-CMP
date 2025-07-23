# Sanctuary
Sanctuary Mobile is the client-side application for a full-stack social media platform, built using Kotlin/Compose Multiplatform. This project aims to provide a seamless and engaging social media experience across various platforms from a single codebase.

# Screenshots
<img width="360" height="760" alt="PostListScreen" src="https://github.com/user-attachments/assets/08c77aed-84bb-41ef-9f2f-6f1ae6d5afd9" />
<img width="360" height="760" alt="PostDetailsScreen" src="https://github.com/user-attachments/assets/b6e68f4d-1845-42b1-a072-67938563b585" />
<img width="360" height="760" alt="Friends" src="https://github.com/user-attachments/assets/55f9fce8-01f2-4caa-be7a-ff51635f1e85" />
<img width="360" height="760" alt="Conversations" src="https://github.com/user-attachments/assets/8d885dd2-6ac4-4445-9caf-4948a01a3218" />
<img width="360" height="760" alt="ChatMessage" src="https://github.com/user-attachments/assets/f6c4ea5d-d958-45ad-81b5-ea6d18877b58" />
<img width="360" height="760" alt="Profile" src="https://github.com/user-attachments/assets/74eb0936-8116-4c7b-b4ee-d1b6e5d0a51c" />

# Features
Account Management: Secure user registration, login, and profile management.

Post Interaction: Browse, create, like, and comment on posts.

Social Connections: Send and accept friend requests.

Real-time Chat: Engage in peer-to-peer conversations with friends.

Cross-Platform UI: A consistent and native-feeling user interface on Android, iOS, and potentially Desktop.

# Tech Stack
The mobile application leverages the power of Kotlin and Compose Multiplatform for a modern, reactive, and maintainable codebase.

Kotlin/Compose Multiplatform: For building native UIs across Android, iOS, and Desktop from a shared codebase.

MVI (Model-View-Intent): A robust architectural design pattern ensuring predictable state management and clear separation of concerns.

Room: For local data persistence and caching. 

Ktor Client: A powerful and flexible HTTP client for all network requests to the backend.

Kotlinx Serialization: For efficient and type-safe JSON parsing and serialization.

Compose Navigation: For handling in-app navigation within the Compose UI.

# Architecture
The project adheres to the MVI (Model-View-Intent) architectural pattern, promoting a unidirectional data flow. This ensures:

Predictable State: The UI state is derived from a single source of truth.

Testability: Business logic is isolated and easily testable.

Maintainability: Clear separation of concerns makes the codebase easier to understand and extend.


## This mobile application is part of a larger full-stack project. Backend Repository:

https://github.com/zawhtetnaing10/Sanctuary-Backend
