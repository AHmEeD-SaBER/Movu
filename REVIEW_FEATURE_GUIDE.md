# 🎬 Review Feature Implementation Guide

## 📋 Overview

The review system allows users to rate and review movies and TV shows with a 5-star rating system and text reviews. Reviews are stored per user and per media, with full analytics support.

---

## 🏗️ Architecture

### **Modules Affected**

1. **firebase** - Data source implementation
2. **details** - Review submission and display for movies/TV shows
3. **profile** - User review statistics and analytics

---

## 📂 File Structure

```
firebase/
  ├── models/ReviewModels.kt           (FirebaseReview, ReviewResult, ReviewError)
  ├── reviews/
  │   ├── IReviewDataSource.kt         (Interface)
  │   └── FireBaseReviewsDataSource.kt (Implementation)
  └── constants/FirebaseConstants.kt   (Collection names)

details/
  ├── domain/
  │   ├── ReviewModels.kt              (Review, ReviewItem, ReviewResult, ReviewStatistics)
  │   ├── repositories/
  │   │   ├── IMovieDetailsRepository.kt
  │   │   └── ITvDetailsRepository.kt
  │   └── usecases/reviews/
  │       ├── IAddMovieReviewUseCase.kt
  │       ├── AddMovieReviewUseCase.kt
  │       ├── IGetMovieReviewsUseCase.kt
  │       ├── GetMovieReviewsUseCase.kt
  │       ├── IGetUserMovieReviewUseCase.kt
  │       ├── GetUserMovieReviewUseCase.kt
  │       └── [Same for TV shows...]
  └── data/
      └── repositories/
          ├── MovieDetailsRepository.kt
          └── TvDetailsRepository.kt

profile/
  ├── domain/
  │   ├── models/ReviewStatistics.kt
  │   ├── repositories/IUserRepository.kt
  │   └── usecases/reviews/
  │       ├── IGetUserReviewStatisticsUseCase.kt
  │       └── GetUserReviewStatisticsUseCase.kt
  └── data/
      └── repositories/UserRepository.kt
```

---

## 🔧 How to Use

### **1. Add a Movie Review**

```kotlin
class MovieDetailsViewModel(
    private val addMovieReviewUseCase: IAddMovieReviewUseCase
) : ViewModel() {

    fun submitReview(movieId: Int, rating: Float, reviewText: String) {
        viewModelScope.launch {
            val reviewItem = ReviewItem(
                mediaId = movieId,
                rating = rating,  // 0.0f to 5.0f
                reviewText = reviewText
            )
            
            when (val result = addMovieReviewUseCase(reviewItem)) {
                is ReviewResult.Success -> {
                    // Show success message
                }
                is ReviewResult.Error -> {
                    // Show error message
                }
            }
        }
    }
}
```

### **2. Get All Reviews for a Movie**

```kotlin
class MovieDetailsViewModel(
    private val getMovieReviewsUseCase: IGetMovieReviewsUseCase
) : ViewModel() {

    fun loadReviews(movieId: Int) {
        viewModelScope.launch {
            when (val result = getMovieReviewsUseCase(movieId)) {
                is ReviewResult.Success -> {
                    val reviews: List<Review> = result.data
                    // Display reviews sorted by timestamp (newest first)
                    reviews.forEach { review ->
                        println("${review.userName}: ${review.rating}★ - ${review.reviewText}")
                    }
                }
                is ReviewResult.Error -> {
                    // Handle error
                }
            }
        }
    }
}
```

### **3. Check if User Already Reviewed**

```kotlin
class MovieDetailsViewModel(
    private val getUserMovieReviewUseCase: IGetUserMovieReviewUseCase
) : ViewModel() {

    fun checkUserReview(movieId: Int) {
        viewModelScope.launch {
            when (val result = getUserMovieReviewUseCase(movieId)) {
                is ReviewResult.Success -> {
                    val userReview: Review? = result.data
                    if (userReview != null) {
                        // User has already reviewed, show edit option
                        _userRating.value = userReview.rating
                        _userReviewText.value = userReview.reviewText
                    } else {
                        // User hasn't reviewed yet, show add option
                    }
                }
                is ReviewResult.Error -> {
                    // Handle error
                }
            }
        }
    }
}
```

### **4. Get User Review Statistics (Profile)**

```kotlin
class ProfileViewModel(
    private val getUserReviewStatisticsUseCase: IGetUserReviewStatisticsUseCase
) : ViewModel() {

    fun loadReviewStatistics() {
        viewModelScope.launch {
            getUserReviewStatisticsUseCase().collect { result ->
                when (result) {
                    is UserResult.Success -> {
                        val stats: ReviewStatistics = result.data
                        println("Total Reviews: ${stats.totalReviews}")
                        println("Movie Reviews: ${stats.movieReviews}")
                        println("TV Reviews: ${stats.tvReviews}")
                        println("Average Rating: ${stats.averageRating}★")
                    }
                    is UserResult.Error -> {
                        // Handle error
                    }
                }
            }
        }
    }
}
```

---

## 📊 Data Models

### **ReviewItem** (Input for adding reviews)
```kotlin
data class ReviewItem(
    val mediaId: Int,
    val rating: Float,      // 0.0 to 5.0
    val reviewText: String
)
```

### **Review** (Output when fetching reviews)
```kotlin
data class Review(
    val userId: String,
    val userName: String,
    val mediaId: Int,
    val rating: Float,
    val reviewText: String,
    val timestamp: Long
)
```

### **ReviewStatistics** (Profile analytics)
```kotlin
data class ReviewStatistics(
    val totalReviews: Int,
    val movieReviews: Int,
    val tvReviews: Int,
    val averageRating: Float
)
```

---

## 🎯 Available Use Cases

### **Movie Reviews**
- `IAddMovieReviewUseCase` - Add/update movie review
- `IGetMovieReviewsUseCase` - Get all reviews for a movie
- `IGetUserMovieReviewUseCase` - Get current user's review

### **TV Show Reviews**
- `IAddTvReviewUseCase` - Add/update TV show review
- `IGetTvReviewsUseCase` - Get all reviews for a TV show
- `IGetUserTvReviewUseCase` - Get current user's review

### **Profile Statistics**
- `IGetUserReviewStatisticsUseCase` - Get user's review analytics

---

## 🗄️ Firebase Structure

Reviews are stored in dual locations for efficient queries:

### **By Media (for showing all reviews)**
```
movie_reviews/{movieId}/reviews/{userId}
tv_reviews/{tvShowId}/reviews/{userId}
```

### **By User (for profile statistics)**
```
user_reviews/{userId}/reviews/movie_{movieId}
user_reviews/{userId}/reviews/tv_{tvShowId}
```

---

## ✨ Features

### **For Details Screen**
✅ Add review with star rating (0-5 stars)  
✅ Add review text  
✅ View all reviews for specific media  
✅ See your own review status  
✅ Update existing review  
✅ Delete your review  
✅ Reviews sorted by newest first  
✅ Shows username and timestamp  

### **For Profile Screen**
✅ Total review count  
✅ Movie review count  
✅ TV show review count  
✅ Average rating across all reviews  
✅ Review analytics display  

---

## 🔌 Dependency Injection

All use cases are provided via Koin:

### **Details Module**
```kotlin
val detailsDomainModule = module {
    // Movie reviews
    factory<IAddMovieReviewUseCase> { AddMovieReviewUseCase(get()) }
    factory<IGetMovieReviewsUseCase> { GetMovieReviewsUseCase(get()) }
    factory<IGetUserMovieReviewUseCase> { GetUserMovieReviewUseCase(get()) }
    
    // TV reviews
    factory<IAddTvReviewUseCase> { AddTvReviewUseCase(get()) }
    factory<IGetTvReviewsUseCase> { GetTvReviewsUseCase(get()) }
    factory<IGetUserTvReviewUseCase> { GetUserTvReviewUseCase(get()) }
}
```

### **Profile Module**
```kotlin
val profileDomainModule = module {
    factory<IGetUserReviewStatisticsUseCase> { 
        GetUserReviewStatisticsUseCase(get()) 
    }
}
```

---

## 🎨 UI Implementation Tips

### **Details Screen - Add Review Section**
1. Show star rating selector (0-5 stars)
2. Text input for review
3. Submit button
4. Check if user already reviewed (show edit mode)
5. Display success/error messages

### **Details Screen - Reviews List**
1. RecyclerView with review items
2. Each item shows:
   - Username
   - Star rating
   - Review text
   - Time ago (e.g., "2 days ago")
3. Empty state if no reviews

### **Profile Screen - Statistics Card**
1. Total reviews count
2. Movie vs TV breakdown (pie chart or bars)
3. Average rating with stars
4. Option to view all user reviews

---

## 🚀 Next Steps

1. Implement UI for review submission in movie/TV details screens
2. Implement reviews list display
3. Add review statistics card to profile screen
4. Add analytics tracking
5. Implement pagination for reviews (if needed)
6. Add review moderation features (if needed)

---

## 📚 Related Documentation

- [Firebase Database Structure](./firebase/REVIEW_DATABASE_STRUCTURE.md)
- Details Module Architecture
- Profile Module Architecture

---

**Created by:** AI Assistant  
**Date:** October 3, 2025  
**Status:** ✅ Complete & Ready to Use

