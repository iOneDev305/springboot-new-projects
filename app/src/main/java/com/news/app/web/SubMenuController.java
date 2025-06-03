package com.news.app.web;

import com.news.app.domain.modal.SubMenu;
import com.news.app.domain.SubMenuRepository;
import com.news.app.domain.MenuRepository;
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

    @PostMapping
    public ResponseEntity<SubMenu> createSubMenu(@RequestBody SubMenu subMenu) {
        // Basic validation to ensure referenced menu exists
        if (!menuRepository.existsById(subMenu.getMenu().getId())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(subMenuRepository.save(subMenu));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubMenu> updateSubMenu(@PathVariable Long id, @RequestBody SubMenu subMenuDetails) {
        Optional<SubMenu> subMenuOptional = subMenuRepository.findById(id);

        if (!subMenuOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        SubMenu subMenu = subMenuOptional.get();

        // Basic validation to ensure referenced menu exists
        if (subMenuDetails.getMenu() != null && !menuRepository.existsById(subMenuDetails.getMenu().getId())) {
            return ResponseEntity.badRequest().build();
        }

        subMenu.setMenu(subMenuDetails.getMenu());
        subMenu.setRouteName(subMenuDetails.getRouteName());

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