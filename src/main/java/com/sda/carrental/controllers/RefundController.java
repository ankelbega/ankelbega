package com.sda.carrental.controllers;


import com.sda.carrental.entities.Refund;
import com.sda.carrental.models.pojo.RefundRequestParam;
import com.sda.carrental.services.RefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class RefundController {

    @Autowired
    private RefundService refundService;


    @PostMapping("/refund")
    public ResponseEntity<Refund> postRefund(@RequestBody final RefundRequestParam refundRequestParam) {
        Refund refund = this.refundService.saveTheRefund(refundRequestParam);
        return ResponseEntity.ok(refund);
    }
}
