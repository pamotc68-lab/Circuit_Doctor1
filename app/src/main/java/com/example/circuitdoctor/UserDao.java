package com.example.circuitdoctor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {
    // บันทึกข้อมูลผู้ใช้ใหม่
    @Insert
    void registerUser(User user);

    // ตรวจสอบข้อมูลอีเมลและรหัสผ่านสำหรับล็อกอิน
    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    User login(String email, String password);

    // เช็กว่าอีเมลนี้เคยถูกสมัครแล้วหรือยัง
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    User checkEmailExists(String email);

    // ดึงข้อมูลผู้ใช้จาก ID (เพิ่มบรรทัดนี้เพื่อแก้ Error)
    @Query("SELECT * FROM users WHERE id = :userId LIMIT 1")
    User getUserById(int userId);
}