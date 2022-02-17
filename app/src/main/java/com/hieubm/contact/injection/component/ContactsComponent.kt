package com.hieubm.contact.injection.component

import com.hieubm.contact.injection.scope.ContactScope
import com.hieubm.contact.ui.main.contacts.ContactsFragment
import dagger.Component

@ContactScope
@Component(dependencies = [AppComponent::class])
interface ContactsComponent {

    fun inject(contactsFragment: ContactsFragment)
}