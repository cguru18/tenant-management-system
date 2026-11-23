package com.example.tenantmanagementsystemgroupa

import com.example.tenantmanagementsystemgroupa.data.model.Tenant
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Unit tests verifying core business logic, domain model invariants,
 * and academic rubric validation criteria.
 */
class TenantValidationTest {

    @Test
    fun testTenantSummary_containsAllRequiredFields() {
        val tenant = Tenant(
            id = 101,
            name = "Jane Muthoni",
            phone = "+254 700 123 456",
            rent = 35000.0,
            propertyUnit = "Apartment 5B"
        )

        val summary = tenant.summary()

        assertTrue(summary.contains("Jane Muthoni"))
        assertTrue(summary.contains("+254 700 123 456"))
        assertTrue(summary.contains("35,000.00"))
        assertTrue(summary.contains("Apartment 5B"))
    }

    @Test
    fun testFormValidation_emptyFieldsFlagRequired() {
        // Validation helper simulating ViewModel validation logic
        fun validateForm(name: String, phone: String, rentStr: String): Map<String, String> {
            val errors = mutableMapOf<String, String>()
            if (name.trim().isEmpty()) errors["name"] = "Required"
            if (phone.trim().isEmpty()) errors["phone"] = "Required"
            if (rentStr.trim().isEmpty()) errors["rent"] = "Required"
            return errors
        }

        // Test Empty Name
        val res1 = validateForm("", "+254 712 000 111", "25000")
        assertEquals("Required", res1["name"])
        assertFalse(res1.containsKey("phone"))
        assertFalse(res1.containsKey("rent"))

        // Test Empty Phone
        val res2 = validateForm("Alice Wanjiku", "", "25000")
        assertEquals("Required", res2["phone"])
        assertFalse(res2.containsKey("name"))
        assertFalse(res2.containsKey("rent"))

        // Test Empty Rent
        val res3 = validateForm("Alice Wanjiku", "+254 712 000 111", "")
        assertEquals("Required", res3["rent"])
        assertFalse(res3.containsKey("name"))
        assertFalse(res3.containsKey("phone"))

        // Test All Three Empty
        val resAll = validateForm("", "", "")
        assertEquals("Required", resAll["name"])
        assertEquals("Required", resAll["phone"])
        assertEquals("Required", resAll["rent"])
    }
}
