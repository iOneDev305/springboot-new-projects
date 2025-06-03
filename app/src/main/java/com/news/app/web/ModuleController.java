package com.news.app.web;

import com.news.app.domain.modal.Module;
import com.news.app.domain.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/modules")
public class ModuleController {

    @Autowired
    private ModuleRepository moduleRepository;

    @GetMapping
    public List<Module> getAllModules() {
        return moduleRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Module> getModuleById(@PathVariable Long id) {
        Optional<Module> module = moduleRepository.findById(id);
        return module.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Module createModule(@RequestBody Module module) {
        return moduleRepository.save(module);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Module> updateModule(@PathVariable Long id, @RequestBody Module moduleDetails) {
        Optional<Module> module = moduleRepository.findById(id);
        return module.map(mod -> {
            mod.setModuleName(moduleDetails.getModuleName());
            mod.setView(moduleDetails.isView());
            return ResponseEntity.ok(moduleRepository.save(mod));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModule(@PathVariable Long id) {
        Optional<Module> module = moduleRepository.findById(id);
        return module.map(mod -> {
            moduleRepository.delete(mod);
            return ResponseEntity.ok().<Void>build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
} 