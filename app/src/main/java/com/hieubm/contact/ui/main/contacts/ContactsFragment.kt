package com.hieubm.contact.ui.main.contacts

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.hieubm.contact.ContactApplication
import com.hieubm.contact.databinding.FragmentContactsBinding
import com.hieubm.contact.injection.component.DaggerContactsComponent
import com.hieubm.contact.ui.base.BaseFragment
import com.hieubm.contact.ui.base.ViewModelFactory
import com.hieubm.contact.util.CONTACT
import com.hieubm.contact.util.showToast
import com.hieubm.core.data.model.Contact
import com.hieubm.core.data.model.State
import javax.inject.Inject

class ContactsFragment : BaseFragment(), ContactAdapter.ContactAdapterListener {
    private var binding: FragmentContactsBinding? = null

    private val viewModel: ContactsViewModel by viewModels { viewModelFactory }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<ContactsViewModel>

    @Inject
    lateinit var contactAdapter: ContactAdapter

    @Inject
    lateinit var contactDetailFragment: ContactDetailFragment

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setupDI()
    }

    private fun setupDI() {
        val appComponent = (requireActivity().application as ContactApplication).appComponent

        DaggerContactsComponent.builder()
            .appComponent(appComponent)
            .build()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactsBinding.inflate(inflater, container, false)
        initComponents()
        return binding?.root
    }

    private fun initComponents() {
        binding?.apply {
            btnAll.isSelected = true

            contactAdapter.listener = this@ContactsFragment
            rcvContacts.setHasFixedSize(true)
            rcvContacts.adapter = contactAdapter
        }

        setEvents()
        setObservers()
    }

    private fun setEvents() {
        binding?.apply {
            btnStarred.setOnClickListener {
                if (it.isSelected) {
                    return@setOnClickListener
                }

                btnAll.isSelected = false
                it.isSelected = true
                viewModel.getContacts(true)
            }

            btnAll.setOnClickListener {
                if (it.isSelected) {
                    return@setOnClickListener
                }

                btnStarred.isSelected = false
                it.isSelected = true
                viewModel.getContacts(false)
            }
        }
    }

    private fun setObservers() {
        viewModel.apply {
            contactList.observe(viewLifecycleOwner) {
                contactAdapter.submitList(it)
            }

            contactChangedPosition.observe(viewLifecycleOwner) {
                contactAdapter.notifyItemChanged(it)
            }

            state.observe(viewLifecycleOwner) {
                when (it) {
                    is State.Loading -> binding?.isLoading = it.isLoading

                    is State.Failure -> {
                        binding?.isLoading = false
                        showToast("An error has occurred, please try again later!")
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        contactDetailFragment.dismiss()
        binding = null
        super.onDestroyView()
    }

    override fun onItemClick(contact: Contact) {
        contactDetailFragment.arguments = bundleOf(CONTACT to contact)
        contactDetailFragment.show(childFragmentManager, this::class.java.simpleName)
    }

    override fun onFavouriteClick(contact: Contact) {
        viewModel.updateCategoryFavourite(contact)
    }
}