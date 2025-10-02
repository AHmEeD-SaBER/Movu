package com.example.domain.use_cases.get_user

import com.example.domain.models.UserResult
import com.example.domain.repositories.IUserRepository

class GetWatchlistCount(private val repository: IUserRepository) : IGetWatchlistCount {
    override suspend operator fun invoke() =
        repository.getUserWatchListCount()
}
