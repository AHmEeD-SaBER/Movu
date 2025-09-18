package datasources.tvdetailsdatasource

import models.tvdetails.TvShowDetails
import kotlinx.coroutines.flow.Flow

interface ITvDetailsDataSource {
    fun getTvDetails(tvId: Int): Flow<TvShowDetails>
}
