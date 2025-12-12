package com.example.capstone3.Service;

import com.example.capstone3.Api.ApiException;
import com.example.capstone3.Model.Admin;
import com.example.capstone3.Model.Campaign;
import com.example.capstone3.Model.Inspector;
import com.example.capstone3.Model.Kitchen;
import com.example.capstone3.Repository.AdminRepository;
import com.example.capstone3.Repository.CampaignRepository;
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
    private final  WhatsAppService whatsAppService;
    private final CampaignRepository campaignRepository;


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

public void activateKitchen(Integer id){

    Kitchen kitchen = kitchenRepository.findKitchenById(id);

    if(kitchen == null){
        throw new ApiException("Kitchen not found");
    }

    kitchen.setStatus("Active");
    kitchenRepository.save(kitchen);

    sendKitchenActivationToWhatsApp(id);
}




    public void sendKitchenActivationToWhatsApp(Integer kitchenId) {

        Kitchen kitchen = kitchenRepository.findKitchenById(kitchenId);
        if (kitchen == null) {
            throw new ApiException("Kitchen not found");
        }

        String to = "966544593236";

        StringBuilder msg = new StringBuilder();
        msg.append("✅ *تم اعتماد مطبخكم رسميًا*\n\n");

        msg.append("📌 *بيانات المطبخ:*\n");
        msg.append("🏠 اسم المطبخ: ").append(kitchen.getName()).append("\n");
        msg.append("👤 اسم المالك: ").append(kitchen.getOwnerName()).append("\n");
        msg.append("📱 رقم التواصل: ").append(kitchen.getOwnerPhone()).append("\n\n");

        msg.append("🟢 *حالة المطبخ:* مفعل وجاهز للعمل\n\n");

        msg.append("يمكنكم الآن البدء في استقبال الحملات\n");
        msg.append("وإعداد الوجبات حسب الاشتراطات الصحية.\n\n");

        msg.append("نتمنى لكم التوفيق والنجاح 🌟\n");
        msg.append("-----------------------------\n");
        msg.append("— إدارة نظام تغذية الحجاج");

        whatsAppService.sendMessage(to, msg.toString());
    }






    public void suspendKitchen(Integer id){
        Kitchen kitchen = kitchenRepository.findKitchenById(id);

        if(kitchen == null){
            throw new ApiException("Kitchen not found");
        }

        kitchen.setStatus("Suspended");
        kitchenRepository.save(kitchen);
    }


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

        if (!"Active".equalsIgnoreCase(kitchen.getStatus())) {
            throw new ApiException("Kitchen with ID " + kitchenId + " is not active and cannot be assigned.");
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

    public List<String> getAllChatIds() {
        return adminRepository.getAllChatIds();
    }


}
