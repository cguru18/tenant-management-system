package com.example.tenantmanagementsystemgroupa.ui.tenants

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tenantmanagementsystemgroupa.data.model.Tenant
import com.example.tenantmanagementsystemgroupa.databinding.FragmentViewTenantsBinding
import com.example.tenantmanagementsystemgroupa.ui.viewmodel.TenantViewModel

/**
 * Fragment rendering the virtualized tenant directory with live Room observation.
 */
class ViewTenantsFragment : Fragment() {

    private var _binding: FragmentViewTenantsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TenantViewModel by activityViewModels()
    private lateinit var tenantAdapter: TenantAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewTenantsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeTenants()
    }

    private fun setupRecyclerView() {
        tenantAdapter = TenantAdapter(
            onTenantClick = { tenant ->
                Toast.makeText(
                    requireContext(),
                    "Selected: ${tenant.name} (${tenant.propertyUnit})",
                    Toast.LENGTH_SHORT
                ).show()
            },
            onCallClick = { tenant ->
                // Implicit Intent: Dial tenant's phone number
                dialTenant(tenant.phone)
            },
            onShareClick = { tenant ->
                // Implicit Intent: Share tenant's summary via Chooser
                shareTenant(tenant)
            }
        )

        binding.rvTenants.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = tenantAdapter
            setHasFixedSize(true)
        }
    }

    private fun observeTenants() {
        viewModel.allTenants.observe(viewLifecycleOwner) { tenantList ->
            if (tenantList.isNullOrEmpty()) {
                binding.llEmptyState.visibility = View.VISIBLE
                binding.rvTenants.visibility = View.GONE
            } else {
                binding.llEmptyState.visibility = View.GONE
                binding.rvTenants.visibility = View.VISIBLE
                tenantAdapter.submitList(tenantList)
            }
        }
    }

    private fun dialTenant(phone: String) {
        if (phone.isBlank()) {
            Toast.makeText(requireContext(), "No phone number available", Toast.LENGTH_SHORT).show()
            return
        }
        val dialIntent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phone")
        }
        startActivity(dialIntent)
    }

    private fun shareTenant(tenant: Tenant) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, tenant.summary())
        }
        val chooserIntent = Intent.createChooser(shareIntent, "Share Tenant Details via")
        startActivity(chooserIntent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
