package com.example.domain.usecases.mediadetails

import com.example.domain.models.MediaResult
import com.example.domain.models.MediaDetails
import com.example.domain.models.MediaType
import kotlinx.coroutines.flow.Flow

interface IGetMediaDetailsUseCase {
    suspend operator fun invoke(mediaId: Int, mediaType: MediaType): Flow<MediaResult<MediaDetails>>
}
