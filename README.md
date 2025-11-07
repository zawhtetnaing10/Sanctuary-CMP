# Sanctuary
Sanctuary Mobile is the client-side application for a full-stack social media platform, built using Kotlin/Compose Multiplatform. 

<img width="360" height="760" alt="PostListScreen" src="https://github.com/user-attachments/assets/08c77aed-84bb-41ef-9f2f-6f1ae6d5afd9" />
<img width="360" height="760" alt="PostDetailsScreen" src="https://github.com/user-attachments/assets/b6e68f4d-1845-42b1-a072-67938563b585" />
<img width="360" height="760" alt="Friends" src="https://github.com/user-attachments/assets/55f9fce8-01f2-4caa-be7a-ff51635f1e85" />
<img width="360" height="760" alt="Conversations" src="https://github.com/user-attachments/assets/8d885dd2-6ac4-4445-9caf-4948a01a3218" />
<img width="360" height="760" alt="ChatMessage" src="https://github.com/user-attachments/assets/f6c4ea5d-d958-45ad-81b5-ea6d18877b58" />
<img width="360" height="760" alt="Profile" src="https://github.com/user-attachments/assets/74eb0936-8116-4c7b-b4ee-d1b6e5d0a51c" />

# Motivation
I built this complicated social media app to prove that Kotlin/Compose Multiplatform is ready for the big leagues. This isn't just a simple demo—it's a hands-on guide to creating a robust, real-world application.

# Quick Start
## Install Android Studio
Android Studio Meerkat (or newer) : Recommended IDE or Kotlin/Compose multiplatform development.

Java Development Kit (JDK): Version 17 or higher is typically required. Android Studio usually manages this, but confirm it's available.

## Clone and run the Backend Repository
Please clone the Backend Repo https://github.com/zawhtetnaing10/Sanctuary-Backend and follow the quick start guide there to get the backend up and running on your machine.

## Running the app
Clone the repository

Open Andorid Studio and run the app. (No additional configuration required)

# Usage and Features
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

## 🤝 Contributing
### Clone the repo
```bash
git clone https://github.com/zawhtetnaing10/Sanctuary-CMP.git
```

### Submit a pull request
If you'd like to contribute, please fork the repository and open a pull request to the `main` branch.

### Guidelines
We prefer contributions that target the Kotlin/Compose Multiplatform shared modules whenever possible.

Please ensure your code builds cleanly and passes any existing lint checks.

For major features or architectural changes, please open an issue first to discuss the change before starting work.


## This mobile application is part of a larger full-stack project. Backend Repository:

https://github.com/zawhtetnaing10/Sanctuary-Backend
