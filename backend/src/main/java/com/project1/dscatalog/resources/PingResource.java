package com.project1.dscatalog.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping(value = "/ping")
public class PingResource {

    @GetMapping
    public ResponseEntity getPing() {
        HashMap<String, String> hashMap = new HashMap();
        hashMap.put("ping", "pong");
        return ResponseEntity.ok().body(hashMap);
    }
}
