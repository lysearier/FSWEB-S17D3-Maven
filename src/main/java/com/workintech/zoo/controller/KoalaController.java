package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Koala;
import com.workintech.zoo.exceptions.ZooException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/koalas")
public class KoalaController {

    private final Map<Integer, Koala> koalaMap = new HashMap<>();

    @PostMapping
    public ResponseEntity<Koala> saveKoala(@RequestBody Koala koala) {
        if (koala.getId() == 0) {
            throw new ZooException("Koala ID boş olamaz", HttpStatus.BAD_REQUEST);
        }
        koalaMap.put(koala.getId(), koala);
        return ResponseEntity.ok(koala);
    }

    @GetMapping
    public ResponseEntity<List<Koala>> findAll() {
        return ResponseEntity.ok(new ArrayList<>(koalaMap.values()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Koala> findById(@PathVariable int id) {
        Koala koala = koalaMap.get(id);
        if (koala == null) {
            throw new ZooException("Koala bulunamadı", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(koala);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Koala> updateKoala(@PathVariable int id, @RequestBody Koala updatedKoala) {
        Koala existing = koalaMap.get(id);
        if (existing == null) {
            throw new ZooException("Güncellenecek koala bulunamadı", HttpStatus.NOT_FOUND);
        }
        updatedKoala.setId(id);
        koalaMap.put(id, updatedKoala);
        return ResponseEntity.ok(updatedKoala);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKoala(@PathVariable int id) {
        Koala removed = koalaMap.remove(id);
        if (removed == null) {
            throw new ZooException("Silinecek koala bulunamadı", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().build();
    }
}
