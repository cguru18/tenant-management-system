package com.example.tenantmanagementsystemgroupa.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tenantmanagementsystemgroupa.data.model.Tenant

/**
 * Data Access Object (DAO) for Tenant persistence operations.
 * Reactive queries return LiveData to automatically notify UI observers on changes.
 */
@Dao
interface TenantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tenant: Tenant): Long

    @Update
    suspend fun update(tenant: Tenant)

    @Delete
    suspend fun delete(tenant: Tenant)

    @Query("SELECT * FROM tenants ORDER BY id DESC")
    fun getAllTenants(): LiveData<List<Tenant>>

    @Query("SELECT * FROM tenants WHERE id = :id LIMIT 1")
    suspend fun getTenantById(id: Int): Tenant?

    @Query("SELECT COUNT(*) FROM tenants")
    fun getTenantCount(): LiveData<Int>

    @Query("SELECT SUM(rent) FROM tenants")
    fun getTotalRent(): LiveData<Double?>
}
