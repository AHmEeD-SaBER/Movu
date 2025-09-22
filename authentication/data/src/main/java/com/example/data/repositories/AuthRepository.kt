import com.example.core_data.utils.INetworkMonitor
import com.example.authentication.data.utils.toData
import com.example.authentication.data.utils.toDomain
import com.example.domain.repositories.IAuthRepository
import com.example.user_preferences.IAuthDataSource
import com.movu.authentication.data.R
import com.example.domain.models.DomainAuthResult
import com.example.domain.models.DomainSignInRequest
import com.example.domain.models.DomainSignUpRequest
import com.example.domain.models.DomainUser

class AuthRepository(private val firebase: IAuthDataSource, private val networkMonitor: INetworkMonitor) : IAuthRepository {
    override suspend fun signUp(request: DomainSignUpRequest): DomainAuthResult<DomainUser> {
        val networkConnectivity = networkMonitor.isNetworkAvailable()
        if (!networkConnectivity) {
            return DomainAuthResult.Error(R.string.no_internet_connection)
        }
        val firebaseRequest = request.toData()
        val result = firebase.signUp(firebaseRequest)
        return result.toDomain { it.toDomain() }


    }

    override suspend fun signIn(request: DomainSignInRequest): DomainAuthResult<DomainUser> {
        val networkConnectivity = networkMonitor.isNetworkAvailable()
        if (!networkConnectivity) {
            return DomainAuthResult.Error(R.string.no_internet_connection)
        }
        val firebaseRequest = request.toData()
        val result = firebase.signIn(firebaseRequest)
        return result.toDomain { it.toDomain() }

    }

}