package com.example.demo;

import org.springframework.web.bind.annotation.*;

import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class KeyValServer {
    KeyValStore keyValStore;

    public KeyValServer() {
        keyValStore = new KeyValStore();
        keyValStore.add("app_name", "key-val-server");
        keyValStore.add("author", "opaliwal");
    }

    @GetMapping("/")
    public String get() {
        return keyValStore.toString();
    }

    @GetMapping("/get")
    public String get(@RequestParam(value = "key", defaultValue = "app_name") String key) {
        return keyValStore.get(key);
    }

    @GetMapping("/put")
    public String put(@RequestParam(value = "key") String key, @RequestParam(value = "value") String value) {
        return keyValStore.add(key, value);
    }

    @DeleteMapping("/delete")
    public boolean delete(@RequestParam(value = "key") String key) {
        return keyValStore.delete(key);
    }

    @PreDestroy public void tearDown(){
        keyValStore.close();
    }
}
