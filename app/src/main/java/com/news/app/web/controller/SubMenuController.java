package com.news.app.web.controller;

import com.news.app.domain.modal.SubMenu;
import com.news.app.domain.repository.MenuRepository;
import com.news.app.domain.repository.SubMenuRepository;
import com.news.app.domain.modal.Menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sub-menus")
public class SubMenuController {

    @Autowired
    private SubMenuRepository subMenuRepository;

    @Autowired
    private MenuRepository menuRepository;

    @GetMapping
    public List<SubMenu> getAllSubMenus() {
        return subMenuRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubMenu> getSubMenuById(@PathVariable Long id) {
        Optional<SubMenu> subMenu = subMenuRepository.findById(id);
        return subMenu.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<SubMenu> createSubMenu(@RequestParam Long menuId, @RequestParam String routeName) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid menu ID"));
        SubMenu subMenu = new SubMenu();
        subMenu.setMenu(menu);
        subMenu.setRouteName(routeName);
        return ResponseEntity.ok(subMenuRepository.save(subMenu));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SubMenu> updateSubMenu(@PathVariable Long id, @RequestParam Long menuId, @RequestParam String routeName) {
        Optional<SubMenu> subMenuOptional = subMenuRepository.findById(id);

        if (!subMenuOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        SubMenu subMenu = subMenuOptional.get();

        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid menu ID"));

        subMenu.setMenu(menu);
        subMenu.setRouteName(routeName);

        return ResponseEntity.ok(subMenuRepository.save(subMenu));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubMenu(@PathVariable Long id) {
        Optional<SubMenu> subMenu = subMenuRepository.findById(id);
        return subMenu.map(sub -> {
            subMenuRepository.delete(sub);
            return ResponseEntity.ok().<Void>build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
} 