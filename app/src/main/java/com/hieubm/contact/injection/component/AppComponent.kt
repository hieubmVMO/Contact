package com.hieubm.contact.injection.component

import android.app.Application
import com.hieubm.contact.injection.module.DatabaseModule
import com.hieubm.contact.injection.module.DispatcherModule
import com.hieubm.contact.injection.module.NetworkModule
import com.hieubm.contact.injection.module.RepositoryModule
import com.hieubm.core.data.repo.IContactRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DispatcherModule::class,
        NetworkModule::class,
        DatabaseModule::class,
        RepositoryModule::class
    ]
)
interface AppComponent {

    fun contactRepository(): IContactRepository

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance application: Application): AppComponent
    }
}