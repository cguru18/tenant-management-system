# ProGuard / R8 rules for Tenant Management System
-keepclassmembers class * extends androidx.room.RoomDatabase {
    <init>();
}
