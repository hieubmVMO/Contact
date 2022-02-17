package com.hieubm.contact.injection.module

import com.hieubm.core.data.repo.ContactRepository
import com.hieubm.core.data.repo.IContactRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindContactRepository(contactRepository: ContactRepository): IContactRepository
}