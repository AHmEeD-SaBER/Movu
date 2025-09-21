package com.example.domain.usecases.mediadetails


import com.example.core_domain.MediaType
import com.example.domain.DetailsResult
import com.example.domain.MediaDetails
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
    ): Flow<DetailsResult<MediaDetails>> {
        return when (mediaType) {
            MediaType.MOVIE -> getMovieDetailsUseCase(mediaId).map { result ->
                when (result) {
                    is DetailsResult.Success -> DetailsResult.Success(result.data as MediaDetails)
                    is DetailsResult.Error -> result
                }
            }
            MediaType.TV_SHOW -> getTvDetailsUseCase(mediaId).map { result ->
                when (result) {
                    is DetailsResult.Success -> DetailsResult.Success(result.data as MediaDetails)
                    is DetailsResult.Error -> result
                }
            }
        }
    }
}
