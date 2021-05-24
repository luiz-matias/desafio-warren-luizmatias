package br.com.warren.challenge.di

import br.com.warren.challenge.app.login.LoginViewModel
import br.com.warren.challenge.app.portfolio.PortfolioViewModel
import br.com.warren.challenge.data.AuthRepository
import br.com.warren.challenge.data.AuthRepositoryImpl
import br.com.warren.challenge.data.PortfolioRepository
import br.com.warren.challenge.data.PortfolioRepositoryImpl
import br.com.warren.challenge.data.local.SessionManager
import br.com.warren.challenge.data.local.SessionManagerImpl
import br.com.warren.challenge.data.webservice.AuthInterceptor
import br.com.warren.challenge.data.webservice.WebService
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    //Network dependencies
    factory { AuthInterceptor(get()) }
    factory { provideOkHttpClient(get()) }
    factory { provideWarrenApi(get()) }
    single { provideRetrofit(get()) }

    //Repositories, Managers, Providers, etc.
    single<SessionManager> { SessionManagerImpl(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    single<PortfolioRepository> { PortfolioRepositoryImpl(get()) }

    //ViewModels
    viewModel { LoginViewModel(Dispatchers.IO, get()) }
    viewModel { PortfolioViewModel(Dispatchers.IO, get()) }

}


fun provideWarrenApi(retrofit: Retrofit): WebService {
    return retrofit.create(WebService::class.java)
}

fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
    return OkHttpClient().newBuilder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .addInterceptor(authInterceptor)
        .build()
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://enigmatic-bayou-48219.herokuapp.com/api/v2/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}