package com.news.app.web.controller;

import com.news.app.domain.modal.Menu;
import com.news.app.domain.repository.MenuRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/menus")
public class MenuController {

    @Autowired
    private MenuRepository menuRepository;

    @GetMapping
    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Menu> getMenuById(@PathVariable Long id) {
        Optional<Menu> menu = menuRepository.findById(id);
        return menu.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public Menu createMenu(@RequestParam String menuName, @RequestParam boolean isActive) {
        Menu menu = new Menu();
        menu.setMenuName(menuName);
        menu.setActive(isActive);
        return menuRepository.save(menu);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Menu> updateMenu(@PathVariable Long id, @RequestParam String menuName, @RequestParam boolean isActive) {
        Optional<Menu> menuOptional = menuRepository.findById(id);

        if (!menuOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Menu menu = menuOptional.get();
        menu.setMenuName(menuName);
        menu.setActive(isActive);

        return ResponseEntity.ok(menuRepository.save(menu));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long id) {
        Optional<Menu> menu = menuRepository.findById(id);
        return menu.map(m -> {
            menuRepository.delete(m);
            return ResponseEntity.ok().<Void>build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
} 