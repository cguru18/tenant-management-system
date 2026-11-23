package com.example.tenantmanagementsystemgroupa.ui.tenants

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.tenantmanagementsystemgroupa.R
import com.example.tenantmanagementsystemgroupa.databinding.FragmentDashboardBinding
import com.example.tenantmanagementsystemgroupa.ui.viewmodel.TenantViewModel

/**
 * Dashboard Fragment presenting aggregated portfolio metrics and quick access routes.
 */
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    // Shared ViewModel tied to Activity lifecycle
    private val viewModel: TenantViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeMetrics()
        setupQuickActions()
    }

    private fun observeMetrics() {
        // Observe reactive count of active tenants
        viewModel.totalTenantsCount.observe(viewLifecycleOwner) { count ->
            binding.tvMetricTenantCount.text = count.toString()
        }

        // Observe reactive monthly portfolio rent aggregate
        viewModel.totalMonthlyRent.observe(viewLifecycleOwner) { totalRent ->
            binding.tvMetricTotalRent.text = "KES %,.2f".format(totalRent)
        }
    }

    private fun setupQuickActions() {
        binding.btnQuickAddTenant.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_addTenantFragment)
        }

        binding.btnQuickViewTenants.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_viewTenantsFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Prevent view lifecycle memory leak
        _binding = null
    }
}
