package com.example.tenantmanagementsystemgroupa.data.repository

import androidx.lifecycle.LiveData
import com.example.tenantmanagementsystemgroupa.data.local.TenantDao
import com.example.tenantmanagementsystemgroupa.data.model.Tenant

/**
 * Clean Architecture Repository mediating between persistent Room storage and ViewModels.
 */
class TenantRepository(private val tenantDao: TenantDao) {

    val allTenants: LiveData<List<Tenant>> = tenantDao.getAllTenants()
    val tenantCount: LiveData<Int> = tenantDao.getTenantCount()
    val totalRent: LiveData<Double?> = tenantDao.getTotalRent()

    suspend fun insert(tenant: Tenant): Long {
        return tenantDao.insert(tenant)
    }

    suspend fun update(tenant: Tenant) {
        tenantDao.update(tenant)
    }

    suspend fun delete(tenant: Tenant) {
        tenantDao.delete(tenant)
    }

    suspend fun getTenantById(id: Int): Tenant? {
        return tenantDao.getTenantById(id)
    }
}
