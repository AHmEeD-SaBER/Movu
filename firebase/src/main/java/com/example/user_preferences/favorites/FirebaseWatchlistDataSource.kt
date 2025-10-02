package com.example.user_preferences.favorites

import com.example.user_preferences.models.FireBaseMediaItem
import com.example.user_preferences.models.WatchlistResult
import com.example.user_preferences.models.WatchlistError
import com.example.user_preferences.constants.FirebaseConstants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseWatchlistDataSource(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : IWatchlistDataSource {

    override suspend fun addMovieToWatchlist(mediaItem: FireBaseMediaItem): WatchlistResult<Unit> {
        return try {
            val userId = getCurrentUserId() ?: return WatchlistResult.Error(
                WatchlistError(
                    com.example.core_data.R.string.watchlist_auth_error_title,
                    com.example.core_data.R.string.watchlist_auth_error_subtitle
                )
            )

            firestore.collection(FirebaseConstants.USER_MOVIES_COLLECTION)
                .document(userId)
                .collection(FirebaseConstants.MOVIES_SUB_COLLECTION)
                .document(mediaItem.id.toString())
                .set(mediaItem)
                .await()

            WatchlistResult.Success(Unit)
        } catch (_: Exception) {
            WatchlistResult.Error(
                WatchlistError(
                    com.example.core_data.R.string.watchlist_error_title,
                    com.example.core_data.R.string.watchlist_error_subtitle
                )
            )
        }
    }

    override suspend fun removeMovieFromWatchlist(mediaId: Int): WatchlistResult<Unit> {
        return try {
            val userId = getCurrentUserId() ?: return WatchlistResult.Error(
                WatchlistError(
                    com.example.core_data.R.string.watchlist_auth_error_title,
                    com.example.core_data.R.string.watchlist_auth_error_subtitle
                )
            )

            firestore.collection(FirebaseConstants.USER_MOVIES_COLLECTION)
                .document(userId)
                .collection(FirebaseConstants.MOVIES_SUB_COLLECTION)
                .document(mediaId.toString())
                .delete()
                .await()

            WatchlistResult.Success(Unit)
        } catch (_: Exception) {
            WatchlistResult.Error(
                WatchlistError(
                    com.example.core_data.R.string.watchlist_error_title,
                    com.example.core_data.R.string.watchlist_error_subtitle
                )
            )
        }
    }

    override suspend fun getUserMovieWatchlist(): WatchlistResult<List<FireBaseMediaItem>> {
        return try {
            val userId = getCurrentUserId() ?: return WatchlistResult.Error(
                WatchlistError(
                    com.example.core_data.R.string.watchlist_auth_error_title,
                    com.example.core_data.R.string.watchlist_auth_error_subtitle
                )
            )

            val snapshot = firestore.collection(FirebaseConstants.USER_MOVIES_COLLECTION)
                .document(userId)
                .collection(FirebaseConstants.MOVIES_SUB_COLLECTION)
                .get()
                .await()

            val mediaItems = snapshot.documents.mapNotNull { document ->
                document.toObject(FireBaseMediaItem::class.java)
            }

            WatchlistResult.Success(mediaItems)
        } catch (_: Exception) {
            WatchlistResult.Error(
                WatchlistError(
                    com.example.core_data.R.string.watchlist_error_title,
                    com.example.core_data.R.string.watchlist_error_subtitle
                )
            )
        }
    }

    override suspend fun isMovieInWatchlist(mediaId: Int): WatchlistResult<Boolean> {
        return try {
            val userId = getCurrentUserId() ?: return WatchlistResult.Error(
                WatchlistError(
                    com.example.core_data.R.string.watchlist_auth_error_title,
                    com.example.core_data.R.string.watchlist_auth_error_subtitle
                )
            )

            val document = firestore.collection(FirebaseConstants.USER_MOVIES_COLLECTION)
                .document(userId)
                .collection(FirebaseConstants.MOVIES_SUB_COLLECTION)
                .document(mediaId.toString())
                .get()
                .await()

            WatchlistResult.Success(document.exists())
        } catch (_: Exception) {
            WatchlistResult.Error(
                WatchlistError(
                    com.example.core_data.R.string.watchlist_error_title,
                    com.example.core_data.R.string.watchlist_error_subtitle
                )
            )
        }
    }

    override suspend fun addTvShowToWatchlist(mediaItem: FireBaseMediaItem): WatchlistResult<Unit> {
        return try {
            val userId = getCurrentUserId() ?: return WatchlistResult.Error(
                WatchlistError(
                    com.example.core_data.R.string.watchlist_auth_error_title,
                    com.example.core_data.R.string.watchlist_auth_error_subtitle
                )
            )

            firestore.collection(FirebaseConstants.USER_TV_SHOWS_COLLECTION)
                .document(userId)
                .collection(FirebaseConstants.TV_SHOWS_SUB_COLLECTION)
                .document(mediaItem.id.toString())
                .set(mediaItem)
                .await()

            WatchlistResult.Success(Unit)
        } catch (_: Exception) {
            WatchlistResult.Error(
                WatchlistError(
                    com.example.core_data.R.string.watchlist_error_title,
                    com.example.core_data.R.string.watchlist_error_subtitle
                )
            )
        }
    }

    override suspend fun removeTvShowFromWatchlist(mediaId: Int): WatchlistResult<Unit> {
        return try {
            val userId = getCurrentUserId() ?: return WatchlistResult.Error(
                WatchlistError(
                    com.example.core_data.R.string.watchlist_auth_error_title,
                    com.example.core_data.R.string.watchlist_auth_error_subtitle
                )
            )

            firestore.collection(FirebaseConstants.USER_TV_SHOWS_COLLECTION)
                .document(userId)
                .collection(FirebaseConstants.TV_SHOWS_SUB_COLLECTION)
                .document(mediaId.toString())
                .delete()
                .await()

            WatchlistResult.Success(Unit)
        } catch (_: Exception) {
            WatchlistResult.Error(
                WatchlistError(
                    com.example.core_data.R.string.watchlist_error_title,
                    com.example.core_data.R.string.watchlist_error_subtitle
                )
            )
        }
    }

    override suspend fun getUserTvShowWatchlist(): WatchlistResult<List<FireBaseMediaItem>> {
        return try {
            val userId = getCurrentUserId() ?: return WatchlistResult.Error(
                WatchlistError(
                    com.example.core_data.R.string.watchlist_auth_error_title,
                    com.example.core_data.R.string.watchlist_auth_error_subtitle
                )
            )

            val snapshot = firestore.collection(FirebaseConstants.USER_TV_SHOWS_COLLECTION)
                .document(userId)
                .collection(FirebaseConstants.TV_SHOWS_SUB_COLLECTION)
                .get()
                .await()

            val mediaItems = snapshot.documents.mapNotNull { document ->
                document.toObject(FireBaseMediaItem::class.java)
            }

            WatchlistResult.Success(mediaItems)
        } catch (_: Exception) {
            WatchlistResult.Error(
                WatchlistError(
                    com.example.core_data.R.string.watchlist_error_title,
                    com.example.core_data.R.string.watchlist_error_subtitle
                )
            )
        }
    }

    override suspend fun isTvShowInWatchlist(mediaId: Int): WatchlistResult<Boolean> {
        return try {
            val userId = getCurrentUserId() ?: return WatchlistResult.Error(
                WatchlistError(
                    com.example.core_data.R.string.watchlist_auth_error_title,
                    com.example.core_data.R.string.watchlist_auth_error_subtitle
                )
            )

            val document = firestore.collection(FirebaseConstants.USER_TV_SHOWS_COLLECTION)
                .document(userId)
                .collection(FirebaseConstants.TV_SHOWS_SUB_COLLECTION)
                .document(mediaId.toString())
                .get()
                .await()

            WatchlistResult.Success(document.exists())
        } catch (_: Exception) {
            WatchlistResult.Error(
                WatchlistError(
                    com.example.core_data.R.string.watchlist_error_title,
                    com.example.core_data.R.string.watchlist_error_subtitle
                )
            )
        }
    }

    override suspend fun getUserMovieWatchlistCount(): WatchlistResult<Int> {
        return try {
            val userId = getCurrentUserId() ?: return WatchlistResult.Error(
                WatchlistError(
                    com.example.core_data.R.string.watchlist_auth_error_title,
                    com.example.core_data.R.string.watchlist_auth_error_subtitle
                )
            )

            val snapshot = firestore.collection(FirebaseConstants.USER_MOVIES_COLLECTION)
                .document(userId)
                .collection(FirebaseConstants.MOVIES_SUB_COLLECTION)
                .get()
                .await()

            WatchlistResult.Success(snapshot.size())
        } catch (_: Exception) {
            WatchlistResult.Error(
                WatchlistError(
                    com.example.core_data.R.string.watchlist_error_title,
                    com.example.core_data.R.string.watchlist_error_subtitle
                )
            )
        }
    }

    override suspend fun getUserTvShowWatchlistCount(): WatchlistResult<Int> {
        return try {
            val userId = getCurrentUserId() ?: return WatchlistResult.Error(
                WatchlistError(
                    com.example.core_data.R.string.watchlist_auth_error_title,
                    com.example.core_data.R.string.watchlist_auth_error_subtitle
                )
            )

            val snapshot = firestore.collection(FirebaseConstants.USER_TV_SHOWS_COLLECTION)
                .document(userId)
                .collection(FirebaseConstants.TV_SHOWS_SUB_COLLECTION)
                .get()
                .await()

            WatchlistResult.Success(snapshot.size())
        } catch (_: Exception) {
            WatchlistResult.Error(
                WatchlistError(
                    com.example.core_data.R.string.watchlist_error_title,
                    com.example.core_data.R.string.watchlist_error_subtitle
                )
            )
        }
    }

    override suspend fun getUserTotalWatchlistCount(): WatchlistResult<Pair<Int, Int>> {
        return try {
            val userId = getCurrentUserId() ?: return WatchlistResult.Error(
                WatchlistError(
                    com.example.core_data.R.string.watchlist_auth_error_title,
                    com.example.core_data.R.string.watchlist_auth_error_subtitle
                )
            )

            // Get both counts in parallel for better performance
            val moviesSnapshot = firestore.collection(FirebaseConstants.USER_MOVIES_COLLECTION)
                .document(userId)
                .collection(FirebaseConstants.MOVIES_SUB_COLLECTION)
                .get()
                .await()

            val tvShowsSnapshot = firestore.collection(FirebaseConstants.USER_TV_SHOWS_COLLECTION)
                .document(userId)
                .collection(FirebaseConstants.TV_SHOWS_SUB_COLLECTION)
                .get()
                .await()

            WatchlistResult.Success(Pair(moviesSnapshot.size(), tvShowsSnapshot.size()))
        } catch (_: Exception) {
            WatchlistResult.Error(
                WatchlistError(
                    com.example.core_data.R.string.watchlist_error_title,
                    com.example.core_data.R.string.watchlist_error_subtitle
                )
            )
        }
    }

    private fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }
}