package com.example.capstone3.Controller;

import com.example.capstone3.DTO_out.PilgrimAdviceDTO;
import com.example.capstone3.Service.AIPilgrimAdviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ai/pilgrims")
@RequiredArgsConstructor
public class AIPilgrimAdviceController {

    private final AIPilgrimAdviceService adviceService;


    @GetMapping("/advice/{lang}")
    public ResponseEntity<PilgrimAdviceDTO> getPilgrimAdvice(
            @PathVariable String lang) {

        PilgrimAdviceDTO advice =
                adviceService.getGeneralPilgrimAdvice(lang);

        return ResponseEntity.status(200).body(advice);
    }
}