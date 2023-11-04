import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseproject.network.MyApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class MyViewModel @Inject constructor(private val myApiService: MyApiService) : ViewModel() {

    fun getAppVersion() {
        viewModelScope.launch {
            try {
                val data = myApiService.getAppVersion()
                Log.d("app_version_response", "$data")
                // You can process the appVersion response here
            } catch (e: Exception) {
                // Handle any exceptions that might occur during the API call
            }
        }
    }
}
