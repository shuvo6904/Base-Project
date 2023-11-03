import androidx.lifecycle.ViewModel
import com.example.baseproject.network.MyApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(private val myApiService: MyApiService) : ViewModel() {
    // Use myApiService to make API calls
}