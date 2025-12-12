package com.example.capstone3.Controller;

import com.example.capstone3.Api.ApiResponse;
import com.example.capstone3.Model.Admin;
import com.example.capstone3.Service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllAdmins() {
        List<Admin> admins = adminService.getAll();
        return ResponseEntity.status(200).body(admins);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addAdmin(@RequestBody Admin admin) {
        adminService.addAdmin(admin);
        return ResponseEntity.status(200).body(new ApiResponse("Admin added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAdmin(@RequestBody Admin admin, @PathVariable Integer id) {
        adminService.updateAdmin(admin, id);
        return ResponseEntity.status(200).body(new ApiResponse("Admin updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAdmin(@PathVariable Integer id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.status(200).body(new ApiResponse("Admin deleted successfully"));
    }

    //  Activate kitchen 1
    @PutMapping("/activate-kitchen/{id}")
    public ResponseEntity<?> activateKitchen(@PathVariable Integer id) {
        adminService.activateKitchen(id);
        return ResponseEntity.status(200).body(new ApiResponse("Kitchen activated successfully"));
    }

    //  Suspend kitchen 2
    @PutMapping("/suspend-kitchen/{id}")
    public ResponseEntity<?> suspendKitchen(@PathVariable Integer id) {
        adminService.suspendKitchen(id);
        return ResponseEntity.status(200).body(new ApiResponse("Kitchen suspended successfully"));
    }

//3

    @PutMapping("/assign-kitchens/{inspectorId}")
    public ResponseEntity<?> assignKitchensToInspector(@PathVariable Integer inspectorId, @RequestBody Set<Integer> kitchenIds) {
        adminService.assignKitchensToInspector(inspectorId, kitchenIds);
        return ResponseEntity.status(200).body(new ApiResponse("Kitchens assigned to inspector successfully"));
    }


    @PutMapping("/reject-kitchen/{kitchenId}")
    public ResponseEntity<?> rejectKitchen (@PathVariable Integer kitchenId) {
        adminService.rejectKitchen(kitchenId);
        return ResponseEntity.status(200).body(new ApiResponse("Kitchen rejected successfully"));
    }



    @GetMapping("/chat-ids")
    public List<String> getChatIds() {
        return adminService.getAllChatIds();
    }

}
