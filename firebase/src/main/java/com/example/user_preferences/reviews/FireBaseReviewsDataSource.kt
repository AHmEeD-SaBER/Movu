package com.example.user_preferences.reviews

import com.example.user_preferences.models.FirebaseReview
import com.example.user_preferences.models.ReviewResult
import com.example.user_preferences.models.ReviewError
import com.example.user_preferences.constants.FirebaseConstants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FireBaseReviewsDataSource(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : IReviewDataSource {

    override suspend fun addMovieReview(
        mediaId: Int,
        rating: Float,
        reviewText: String
    ): ReviewResult<Unit> {
        return try {
            val userId = getCurrentUserId() ?: return ReviewResult.Error(
                ReviewError(
                    com.example.core_data.R.string.watchlist_auth_error_title,
                    com.example.core_data.R.string.watchlist_auth_error_subtitle
                )
            )

            val userName = getUserName(userId)
            val review = FirebaseReview(
                userId = userId,
                userName = userName,
                mediaId = mediaId,
                mediaType = FirebaseConstants.MEDIA_TYPE_MOVIE,
                rating = rating,
                reviewText = reviewText,
                timestamp = System.currentTimeMillis()
            )

            firestore.collection(FirebaseConstants.MOVIE_REVIEWS_COLLECTION)
                .document(mediaId.toString())
                .collection(FirebaseConstants.REVIEWS_SUB_COLLECTION)
                .document(userId)
                .set(review)
                .await()

            firestore.collection(FirebaseConstants.USER_REVIEWS_COLLECTION)
                .document(userId)
                .collection(FirebaseConstants.REVIEWS_SUB_COLLECTION)
                .document("${FirebaseConstants.MEDIA_TYPE_MOVIE}_$mediaId")
                .set(review)
                .await()

            ReviewResult.Success(Unit)
        } catch (_: Exception) {
            ReviewResult.Error(
                ReviewError(
                    com.example.core_data.R.string.watchlist_error_title,
                    com.example.core_data.R.string.watchlist_error_subtitle
                )
            )
        }
    }

    override suspend fun addTvShowReview(
        mediaId: Int,
        rating: Float,
        reviewText: String
    ): ReviewResult<Unit> {
        return try {
            val userId = getCurrentUserId() ?: return ReviewResult.Error(
                ReviewError(
                    com.example.core_data.R.string.watchlist_auth_error_title,
                    com.example.core_data.R.string.watchlist_auth_error_subtitle
                )
            )

            val userName = getUserName(userId)
            val review = FirebaseReview(
                userId = userId,
                userName = userName,
                mediaId = mediaId,
                mediaType = FirebaseConstants.MEDIA_TYPE_TV,
                rating = rating,
                reviewText = reviewText,
                timestamp = System.currentTimeMillis()
            )

            firestore.collection(FirebaseConstants.TV_REVIEWS_COLLECTION)
                .document(mediaId.toString())
                .collection(FirebaseConstants.REVIEWS_SUB_COLLECTION)
                .document(userId)
                .set(review)
                .await()

            firestore.collection(FirebaseConstants.USER_REVIEWS_COLLECTION)
                .document(userId)
                .collection(FirebaseConstants.REVIEWS_SUB_COLLECTION)
                .document("${FirebaseConstants.MEDIA_TYPE_TV}_$mediaId")
                .set(review)
                .await()

            ReviewResult.Success(Unit)
        } catch (_: Exception) {
            ReviewResult.Error(
                ReviewError(
                    com.example.core_data.R.string.watchlist_error_title,
                    com.example.core_data.R.string.watchlist_error_subtitle
                )
            )
        }
    }

    override suspend fun updateMovieReview(
        mediaId: Int,
        rating: Float,
        reviewText: String
    ): ReviewResult<Unit> {
        return addMovieReview(mediaId, rating, reviewText)
    }

    override suspend fun updateTvShowReview(
        mediaId: Int,
        rating: Float,
        reviewText: String
    ): ReviewResult<Unit> {
        return addTvShowReview(mediaId, rating, reviewText)
    }

    override suspend fun deleteMovieReview(mediaId: Int): ReviewResult<Unit> {
        return try {
            val userId = getCurrentUserId() ?: return ReviewResult.Error(
                ReviewError(
                    com.example.core_data.R.string.watchlist_auth_error_title,
                    com.example.core_data.R.string.watchlist_auth_error_subtitle
                )
            )

            firestore.collection(FirebaseConstants.MOVIE_REVIEWS_COLLECTION)
                .document(mediaId.toString())
                .collection(FirebaseConstants.REVIEWS_SUB_COLLECTION)
                .document(userId)
                .delete()
                .await()

            firestore.collection(FirebaseConstants.USER_REVIEWS_COLLECTION)
                .document(userId)
                .collection(FirebaseConstants.REVIEWS_SUB_COLLECTION)
                .document("${FirebaseConstants.MEDIA_TYPE_MOVIE}_$mediaId")
                .delete()
                .await()

            ReviewResult.Success(Unit)
        } catch (_: Exception) {
            ReviewResult.Error(
                ReviewError(
                    com.example.core_data.R.string.watchlist_error_title,
                    com.example.core_data.R.string.watchlist_error_subtitle
                )
            )
        }
    }

    override suspend fun deleteTvShowReview(mediaId: Int): ReviewResult<Unit> {
        return try {
            val userId = getCurrentUserId() ?: return ReviewResult.Error(
                ReviewError(
                    com.example.core_data.R.string.watchlist_auth_error_title,
                    com.example.core_data.R.string.watchlist_auth_error_subtitle
                )
            )

            firestore.collection(FirebaseConstants.TV_REVIEWS_COLLECTION)
                .document(mediaId.toString())
                .collection(FirebaseConstants.REVIEWS_SUB_COLLECTION)
                .document(userId)
                .delete()
                .await()

            firestore.collection(FirebaseConstants.USER_REVIEWS_COLLECTION)
                .document(userId)
                .collection(FirebaseConstants.REVIEWS_SUB_COLLECTION)
                .document("${FirebaseConstants.MEDIA_TYPE_TV}_$mediaId")
                .delete()
                .await()

            ReviewResult.Success(Unit)
        } catch (_: Exception) {
            ReviewResult.Error(
                ReviewError(
                    com.example.core_data.R.string.watchlist_error_title,
                    com.example.core_data.R.string.watchlist_error_subtitle
                )
            )
        }
    }

    override suspend fun getMovieReviews(mediaId: Int): ReviewResult<List<FirebaseReview>> {
        return try {
            val snapshot = firestore.collection(FirebaseConstants.MOVIE_REVIEWS_COLLECTION)
                .document(mediaId.toString())
                .collection(FirebaseConstants.REVIEWS_SUB_COLLECTION)
                .get()
                .await()

            val reviews = snapshot.documents.mapNotNull { document ->
                document.toObject(FirebaseReview::class.java)
            }.sortedByDescending { it.timestamp }

            ReviewResult.Success(reviews)
        } catch (_: Exception) {
            ReviewResult.Error(
                ReviewError(
                    com.example.core_data.R.string.watchlist_error_title,
                    com.example.core_data.R.string.watchlist_error_subtitle
                )
            )
        }
    }

    override suspend fun getTvShowReviews(mediaId: Int): ReviewResult<List<FirebaseReview>> {
        return try {
            val snapshot = firestore.collection(FirebaseConstants.TV_REVIEWS_COLLECTION)
                .document(mediaId.toString())
                .collection(FirebaseConstants.REVIEWS_SUB_COLLECTION)
                .get()
                .await()

            val reviews = snapshot.documents.mapNotNull { document ->
                document.toObject(FirebaseReview::class.java)
            }.sortedByDescending { it.timestamp }

            ReviewResult.Success(reviews)
        } catch (_: Exception) {
            ReviewResult.Error(
                ReviewError(
                    com.example.core_data.R.string.watchlist_error_title,
                    com.example.core_data.R.string.watchlist_error_subtitle
                )
            )
        }
    }

    override suspend fun getUserMovieReview(mediaId: Int): ReviewResult<FirebaseReview?> {
        return try {
            val userId = getCurrentUserId() ?: return ReviewResult.Error(
                ReviewError(
                    com.example.core_data.R.string.watchlist_auth_error_title,
                    com.example.core_data.R.string.watchlist_auth_error_subtitle
                )
            )

            val document = firestore.collection(FirebaseConstants.MOVIE_REVIEWS_COLLECTION)
                .document(mediaId.toString())
                .collection(FirebaseConstants.REVIEWS_SUB_COLLECTION)
                .document(userId)
                .get()
                .await()

            val review = document.toObject(FirebaseReview::class.java)
            ReviewResult.Success(review)
        } catch (_: Exception) {
            ReviewResult.Error(
                ReviewError(
                    com.example.core_data.R.string.watchlist_error_title,
                    com.example.core_data.R.string.watchlist_error_subtitle
                )
            )
        }
    }

    override suspend fun getUserTvShowReview(mediaId: Int): ReviewResult<FirebaseReview?> {
        return try {
            val userId = getCurrentUserId() ?: return ReviewResult.Error(
                ReviewError(
                    com.example.core_data.R.string.watchlist_auth_error_title,
                    com.example.core_data.R.string.watchlist_auth_error_subtitle
                )
            )

            val document = firestore.collection(FirebaseConstants.TV_REVIEWS_COLLECTION)
                .document(mediaId.toString())
                .collection(FirebaseConstants.REVIEWS_SUB_COLLECTION)
                .document(userId)
                .get()
                .await()

            val review = document.toObject(FirebaseReview::class.java)
            ReviewResult.Success(review)
        } catch (_: Exception) {
            ReviewResult.Error(
                ReviewError(
                    com.example.core_data.R.string.watchlist_error_title,
                    com.example.core_data.R.string.watchlist_error_subtitle
                )
            )
        }
    }

    override suspend fun getUserReviewCount(): ReviewResult<Int> {
        return try {
            val userId = getCurrentUserId() ?: return ReviewResult.Error(
                ReviewError(
                    com.example.core_data.R.string.watchlist_auth_error_title,
                    com.example.core_data.R.string.watchlist_auth_error_subtitle
                )
            )

            val snapshot = firestore.collection(FirebaseConstants.USER_REVIEWS_COLLECTION)
                .document(userId)
                .collection(FirebaseConstants.REVIEWS_SUB_COLLECTION)
                .get()
                .await()

            ReviewResult.Success(snapshot.size())
        } catch (_: Exception) {
            ReviewResult.Error(
                ReviewError(
                    com.example.core_data.R.string.watchlist_error_title,
                    com.example.core_data.R.string.watchlist_error_subtitle
                )
            )
        }
    }

    override suspend fun getUserMovieReviewCount(): ReviewResult<Int> {
        return try {
            val userId = getCurrentUserId() ?: return ReviewResult.Error(
                ReviewError(
                    com.example.core_data.R.string.watchlist_auth_error_title,
                    com.example.core_data.R.string.watchlist_auth_error_subtitle
                )
            )

            val snapshot = firestore.collection(FirebaseConstants.USER_REVIEWS_COLLECTION)
                .document(userId)
                .collection(FirebaseConstants.REVIEWS_SUB_COLLECTION)
                .get()
                .await()

            val movieCount = snapshot.documents.count { doc ->
                doc.id.startsWith("${FirebaseConstants.MEDIA_TYPE_MOVIE}_")
            }

            ReviewResult.Success(movieCount)
        } catch (_: Exception) {
            ReviewResult.Error(
                ReviewError(
                    com.example.core_data.R.string.watchlist_error_title,
                    com.example.core_data.R.string.watchlist_error_subtitle
                )
            )
        }
    }

    override suspend fun getUserTvShowReviewCount(): ReviewResult<Int> {
        return try {
            val userId = getCurrentUserId() ?: return ReviewResult.Error(
                ReviewError(
                    com.example.core_data.R.string.watchlist_auth_error_title,
                    com.example.core_data.R.string.watchlist_auth_error_subtitle
                )
            )

            val snapshot = firestore.collection(FirebaseConstants.USER_REVIEWS_COLLECTION)
                .document(userId)
                .collection(FirebaseConstants.REVIEWS_SUB_COLLECTION)
                .get()
                .await()

            val tvCount = snapshot.documents.count { doc ->
                doc.id.startsWith("${FirebaseConstants.MEDIA_TYPE_TV}_")
            }

            ReviewResult.Success(tvCount)
        } catch (_: Exception) {
            ReviewResult.Error(
                ReviewError(
                    com.example.core_data.R.string.watchlist_error_title,
                    com.example.core_data.R.string.watchlist_error_subtitle
                )
            )
        }
    }

    override suspend fun getAllUserReviews(): ReviewResult<List<FirebaseReview>> {
        return try {
            val userId = getCurrentUserId() ?: return ReviewResult.Error(
                ReviewError(
                    com.example.core_data.R.string.watchlist_auth_error_title,
                    com.example.core_data.R.string.watchlist_auth_error_subtitle
                )
            )

            val snapshot = firestore.collection(FirebaseConstants.USER_REVIEWS_COLLECTION)
                .document(userId)
                .collection(FirebaseConstants.REVIEWS_SUB_COLLECTION)
                .get()
                .await()

            val reviews = snapshot.documents.mapNotNull { document ->
                document.toObject(FirebaseReview::class.java)
            }.sortedByDescending { it.timestamp }

            ReviewResult.Success(reviews)
        } catch (_: Exception) {
            ReviewResult.Error(
                ReviewError(
                    com.example.core_data.R.string.watchlist_error_title,
                    com.example.core_data.R.string.watchlist_error_subtitle
                )
            )
        }
    }

    private fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }

    private suspend fun getUserName(userId: String): String {
        return try {
            val userDoc = firestore.collection(FirebaseConstants.USERS_COLLECTION)
                .document(userId)
                .get()
                .await()
            userDoc.getString(FirebaseConstants.FIELD_USERNAME) ?: "Anonymous"
        } catch (_: Exception) {
            "Anonymous"
        }
    }
}