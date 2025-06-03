package com.news.app.web;

import com.news.app.domain.modal.Menu;
import com.news.app.domain.MenuRepository;
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

    @PostMapping
    public Menu createMenu(@RequestBody Menu menu) {
        return menuRepository.save(menu);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Menu> updateMenu(@PathVariable Long id, @RequestBody Menu menuDetails) {
        Optional<Menu> menu = menuRepository.findById(id);
        return menu.map(m -> {
            m.setMenuName(menuDetails.getMenuName());
            m.setActive(menuDetails.isActive());
            return ResponseEntity.ok(menuRepository.save(m));
        }).orElseGet(() -> ResponseEntity.notFound().build());
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