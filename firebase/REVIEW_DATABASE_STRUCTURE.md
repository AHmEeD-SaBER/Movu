# Firebase Review System - Database Structure

## 📊 Database Collections Overview

The review system uses **3 main collections** in Firestore to efficiently store and retrieve reviews:

### 1. **movie_reviews** Collection
### 2. **tv_reviews** Collection  
### 3. **user_reviews** Collection

---

## 🏗️ Detailed Structure

### 1️⃣ **movie_reviews** Collection
**Purpose:** Store all reviews for each movie, organized by movie ID

```
movie_reviews/
  ├── {movieId}/              ← Document for each movie (e.g., "550" for Fight Club)
      └── reviews/             ← Sub-collection containing all reviews
          ├── {userId1}/       ← Review by user 1
          │   ├── userId: "abc123"
          │   ├── userName: "JohnDoe"
          │   ├── mediaId: 550
          │   ├── mediaType: "movie"
          │   ├── rating: 4.5
          │   ├── reviewText: "Amazing movie!"
          │   └── timestamp: 1696348800000
          │
          ├── {userId2}/       ← Review by user 2
          └── {userId3}/       ← Review by user 3
```

**Use Case:** Get all reviews for a specific movie (e.g., show all reviews on movie details page)

---

### 2️⃣ **tv_reviews** Collection
**Purpose:** Store all reviews for each TV show, organized by TV show ID

```
tv_reviews/
  ├── {tvShowId}/             ← Document for each TV show (e.g., "1396" for Breaking Bad)
      └── reviews/             ← Sub-collection containing all reviews
          ├── {userId1}/       ← Review by user 1
          │   ├── userId: "abc123"
          │   ├── userName: "JohnDoe"
          │   ├── mediaId: 1396
          │   ├── mediaType: "tv"
          │   ├── rating: 5.0
          │   ├── reviewText: "Best TV show ever!"
          │   └── timestamp: 1696348800000
          │
          ├── {userId2}/       ← Review by user 2
          └── {userId3}/       ← Review by user 3
```

**Use Case:** Get all reviews for a specific TV show (e.g., show all reviews on TV show details page)

---

### 3️⃣ **user_reviews** Collection
**Purpose:** Store all reviews made by each user, organized by user ID

```
user_reviews/
  ├── {userId}/                    ← Document for each user (e.g., "abc123")
      └── reviews/                  ← Sub-collection containing user's reviews
          ├── movie_{movieId}/      ← User's review for a movie (e.g., "movie_550")
          │   ├── userId: "abc123"
          │   ├── userName: "JohnDoe"
          │   ├── mediaId: 550
          │   ├── mediaType: "movie"
          │   ├── rating: 4.5
          │   ├── reviewText: "Amazing movie!"
          │   └── timestamp: 1696348800000
          │
          ├── tv_{tvShowId}/        ← User's review for a TV show (e.g., "tv_1396")
          │   ├── userId: "abc123"
          │   ├── userName: "JohnDoe"
          │   ├── mediaId: 1396
          │   ├── mediaType: "tv"
          │   ├── rating: 5.0
          │   ├── reviewText: "Best TV show ever!"
          │   └── timestamp: 1696348800000
          │
          ├── movie_680/            ← Another movie review
          └── tv_94605/             ← Another TV show review
```

**Use Case:** Get all reviews made by a user (e.g., show on user profile page)

---

## 🔄 Why Dual Storage?

The system stores each review in **TWO locations** simultaneously:

### Example: User "JohnDoe" (userId: abc123) reviews "Inception" (movieId: 550)

**Location 1:** `movie_reviews/550/reviews/abc123`
- Allows: Get all reviews for Inception quickly

**Location 2:** `user_reviews/abc123/reviews/movie_550`
- Allows: Get all reviews by JohnDoe quickly

### Benefits:
✅ **Fast Queries:** No need to scan entire database  
✅ **Organized Data:** Reviews grouped by movie AND by user  
✅ **Easy Updates:** Update both locations when user edits review  
✅ **Efficient Counts:** Count reviews without reading all data

---

## 🎯 Common Operations

### **Add a Movie Review**
1. Get current user ID from Firebase Auth
2. Fetch username from `users/{userId}` collection
3. Create review object
4. Store in: `movie_reviews/{movieId}/reviews/{userId}`
5. Store in: `user_reviews/{userId}/reviews/movie_{movieId}`

### **Get All Reviews for a Movie**
- Query: `movie_reviews/{movieId}/reviews/` (returns all reviews)

### **Get User's Review for a Movie**
- Query: `movie_reviews/{movieId}/reviews/{userId}` (returns single review or null)

### **Get All User's Reviews**
- Query: `user_reviews/{userId}/reviews/` (returns all user's reviews)

### **Count User's Reviews**
- Query: `user_reviews/{userId}/reviews/` (count the documents)

### **Delete a Review**
1. Delete from: `movie_reviews/{movieId}/reviews/{userId}`
2. Delete from: `user_reviews/{userId}/reviews/movie_{movieId}`

---

## 📝 FirebaseReview Model

```kotlin
data class FirebaseReview(
    val userId: String = "",           // User who wrote the review
    val userName: String = "",          // Username (fetched from users collection)
    val mediaId: Int = 0,              // Movie/TV show ID
    val mediaType: String = "",        // "movie" or "tv"
    val rating: Float = 0f,            // 0.0 to 5.0 stars
    val reviewText: String = "",       // Review text content
    val timestamp: Long = 0L           // When review was created/updated
)
```

---

## 🔑 Key Design Decisions

1. **Document ID = userId** in movie/tv collections
   - Ensures one review per user per movie/TV show
   - Easy to check if user already reviewed

2. **Document ID = "movie_{id}" or "tv_{id}"** in user collection
   - Easy to distinguish movie vs TV reviews
   - Prevents ID conflicts between movies and TV shows

3. **Username stored in review**
   - Fetched from `users/{userId}` collection at creation time
   - If username changes, reviews show old username (design choice)
   - Alternative: Fetch username when displaying (slower but always current)

4. **Timestamp included**
   - Allows sorting reviews by newest/oldest
   - Can calculate "time since review"

---

## 📊 Example Queries

```kotlin
// Get all reviews for movie ID 550
val reviews = reviewDataSource.getMovieReviews(550)

// Get user's review for movie ID 550
val myReview = reviewDataSource.getUserMovieReview(550)

// Get count of all user's reviews
val count = reviewDataSource.getUserReviewCount()

// Add/Update review
reviewDataSource.addMovieReview(
    mediaId = 550,
    rating = 4.5f,
    reviewText = "Fantastic movie!"
)
```

---

## 🗑️ Why Delete from Both Locations?

When deleting a review, we must delete from BOTH locations to maintain consistency:
- If only deleted from movie_reviews → Review still shows in user profile
- If only deleted from user_reviews → Review still shows on movie page

Same principle applies for watchlist or any dual-storage pattern!

