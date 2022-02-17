package com.hieubm.contact.injection.module

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named

@Module
class DispatcherModule {

    @Provides
    @Named("DispatcherDefault")
    fun provideDispatcherDefault(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @Named("DispatcherIO")
    fun provideDispatcherIO(): CoroutineDispatcher = Dispatchers.IO
}