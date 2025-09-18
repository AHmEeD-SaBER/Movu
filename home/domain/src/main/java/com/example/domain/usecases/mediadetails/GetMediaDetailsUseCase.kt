package com.example.domain.usecases.mediadetails

import com.example.domain.models.MediaResult
import com.example.domain.models.MediaDetails
import com.example.domain.models.MediaType
import com.example.domain.usecases.moviedetails.IGetMovieDetailsUseCase
import com.example.domain.usecases.tvdetails.IGetTvDetailsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetMediaDetailsUseCase(
    private val getMovieDetailsUseCase: IGetMovieDetailsUseCase,
    private val getTvDetailsUseCase: IGetTvDetailsUseCase
) : IGetMediaDetailsUseCase {

    override suspend operator fun invoke(
        mediaId: Int,
        mediaType: MediaType
    ): Flow<MediaResult<MediaDetails>> {
        return when (mediaType) {
            MediaType.MOVIE -> getMovieDetailsUseCase(mediaId).map { result ->
                when (result) {
                    is MediaResult.Success -> MediaResult.Success(result.data as MediaDetails)
                    is MediaResult.Error -> result
                }
            }
            MediaType.TV_SHOW -> getTvDetailsUseCase(mediaId).map { result ->
                when (result) {
                    is MediaResult.Success -> MediaResult.Success(result.data as MediaDetails)
                    is MediaResult.Error -> result
                }
            }
        }
    }
}
