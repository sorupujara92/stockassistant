package com.sourav.stockassistant.controller;

import com.sourav.stockassistant.request.ConcallRequest;
import com.sourav.stockassistant.service.ConcallService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;

@RestController
@RequestMapping("/api/v1/concall")
public class ConcallController {

    @Autowired
    ConcallService concallService;

    @PostMapping ResponseEntity<String> updateConcall(@RequestBody ConcallRequest concallRequest) {
        if (concallRequest == null || StringUtils.isBlank(concallRequest.getStock())) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Invalid Stock");
        }
        concallService.updateConcall(concallRequest.getStock());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Success");
    }

}
