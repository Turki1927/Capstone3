package com.example.capstone3.Service;

import com.example.capstone3.Api.ApiException;
import com.example.capstone3.Model.Admin;
import com.example.capstone3.Model.Inspector;
import com.example.capstone3.Model.Kitchen;
import com.example.capstone3.Repository.AdminRepository;
import com.example.capstone3.Repository.InspectorRepository;
import com.example.capstone3.Repository.KitchenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor

public class AdminService {

    private final AdminRepository adminRepository;
    private final KitchenRepository kitchenRepository;
    private final InspectorRepository inspectorRepository;


    public List<Admin> getAll(){
        return adminRepository.findAll();
    }



    public void addAdmin(Admin admin) {
        adminRepository.save(admin);
    }



    public void updateAdmin(Admin admin, Integer id) {

        Admin admin1 = adminRepository.findAdminById(id);

        if (admin1 == null) {
            throw new ApiException("the admin is not found");
        }

        admin1.setUsername(admin.getUsername());
        admin1.setPassword(admin.getPassword());
        adminRepository.save(admin1);


    }




    public void deleteAdmin(Integer id){

        Admin admin= adminRepository.findAdminById(id);

        if (admin==null){
            throw new ApiException("admin is not found");
        }

        adminRepository.delete(admin);


    }

// 1 by turki
    public void activateKitchen(Integer id){
        Kitchen kitchen = kitchenRepository.findKitchenById(id);

        if(kitchen == null){
            throw new ApiException("Kitchen not found");
        }

        kitchen.setStatus("Active");
        kitchenRepository.save(kitchen);
    }

//2 by turki
    public void suspendKitchen(Integer id){
        Kitchen kitchen = kitchenRepository.findKitchenById(id);

        if(kitchen == null){
            throw new ApiException("Kitchen not found");
        }

        kitchen.setStatus("Suspended");
        kitchenRepository.save(kitchen);
    }


//3 by turki
    public void assignKitchensToInspector(Integer inspectorId, Set<Integer> kitchenIds) {
        Inspector inspector = inspectorRepository.findInspectorById(inspectorId);
        if (inspector == null) {
            throw new ApiException("Inspector id not found");
        }

        Set<Kitchen> kitchens = new HashSet<>();

        for (Integer kitchenId : kitchenIds) {
            Kitchen kitchen = kitchenRepository.findKitchenById(kitchenId);
            if (kitchen == null) {
                throw new ApiException("Kitchen id not found: " + kitchenId);
            }
            kitchens.add(kitchen);
        }

        inspector.setKitchens(kitchens);
        inspectorRepository.save(inspector);

    }

    public void rejectKitchen(Integer id){
        Kitchen kitchen = kitchenRepository.findKitchenById(id);

        if (kitchen == null) {
            throw new ApiException("Kitchen not found");
        }

        kitchen.setStatus("Reject");
        kitchenRepository.save(kitchen);
    }


}
