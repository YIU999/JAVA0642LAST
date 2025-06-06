package com.example.pointsystem.controller;

import com.example.pointsystem.model.Reward;
import com.example.pointsystem.service.ShopService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/store")
@CrossOrigin
public class ShopController {

    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping("/rewards")
    public List<Reward> getAll() {
        return shopService.getAllRewards();
    }

    @PostMapping("/buy")
    public void buy(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        Long rewardId = Long.parseLong(body.get("rewardId"));
        shopService.buy(username, rewardId);
    }
}
