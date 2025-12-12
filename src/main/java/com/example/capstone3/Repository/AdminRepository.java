package com.example.capstone3.Repository;

import com.example.capstone3.Model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Admin findAdminById(Integer id);

    @Query("select a.chatId from Admin a")
    List<String> getAllChatIds();
}
