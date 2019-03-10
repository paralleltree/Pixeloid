package net.paltee.pixeloid.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import net.paltee.pixeloid.api.GraphsResponseAdapter
import net.paltee.pixeloid.api.PixelaService
import net.paltee.pixeloid.util.LiveDataCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class DataModule {
    @Singleton
    @Provides
    fun provideMoshi() = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(GraphsResponseAdapter.FACTORY)
            .build()

    @Singleton
    @Provides
    fun providePixelaService(moshi: Moshi): PixelaService =
            Retrofit.Builder()
                    .baseUrl("https://pixe.la/v1/")
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .addCallAdapterFactory(LiveDataCallAdapterFactory())
                    .build()
                    .create(PixelaService::class.java)
}
