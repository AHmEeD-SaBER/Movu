package com.example.domain.usecases.mediadetails


import com.example.core_domain.MediaType
import com.example.domain.DetailsResult
import com.example.domain.MediaDetails
import kotlinx.coroutines.flow.Flow

interface IGetMediaDetailsUseCase {
    suspend operator fun invoke(mediaId: Int, mediaType: MediaType): Flow<DetailsResult<MediaDetails>>
}
