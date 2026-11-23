package com.example.tenantmanagementsystemgroupa.ui.tenants

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tenantmanagementsystemgroupa.data.model.Tenant
import com.example.tenantmanagementsystemgroupa.databinding.ItemTenantBinding

/**
 * High-performance RecyclerView Adapter for virtualized tenant listings.
 * Implements the ViewHolder caching pattern to eliminate redundant view hierarchy lookups.
 */
class TenantAdapter(
    private val onTenantClick: (Tenant) -> Unit,
    private val onCallClick: (Tenant) -> Unit,
    private val onShareClick: (Tenant) -> Unit
) : RecyclerView.Adapter<TenantAdapter.TenantViewHolder>() {

    private var tenants: List<Tenant> = emptyList()

    fun submitList(newList: List<Tenant>) {
        this.tenants = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TenantViewHolder {
        val binding = ItemTenantBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TenantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TenantViewHolder, position: Int) {
        val tenant = tenants[position]
        holder.bind(tenant)
    }

    override fun getItemCount(): Int = tenants.size

    inner class TenantViewHolder(private val binding: ItemTenantBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(tenant: Tenant) {
            binding.tvTenantName.text = tenant.name
            binding.tvTenantPhone.text = tenant.phone
            binding.tvTenantRent.text = "KES %,.2f".format(tenant.rent)
            binding.tvTenantUnit.text = "Unit: ${tenant.propertyUnit}"

            binding.btnCallTenant.setOnClickListener {
                onCallClick(tenant)
            }

            binding.btnShareTenant.setOnClickListener {
                onShareClick(tenant)
            }

            binding.root.setOnClickListener {
                onTenantClick(tenant)
            }
        }
    }
}
