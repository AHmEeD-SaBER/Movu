import com.example.ui.signin.SignInViewModel
import com.example.ui.signup.SignUpViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel


val authUiModule = module {
    viewModel { SignInViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
}