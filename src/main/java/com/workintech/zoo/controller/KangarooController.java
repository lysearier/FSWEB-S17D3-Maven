package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.exceptions.ZooException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/kangaroos")
public class KangarooController {

    private final Map<Integer, Kangaroo> kangarooMap = new HashMap<>();

    @PostMapping
    public ResponseEntity<Kangaroo> saveKangaroo(@RequestBody Kangaroo kangaroo) {
        if (kangaroo.getId() == 0) {
            throw new ZooException("Kangaroo ID boş olamaz", HttpStatus.BAD_REQUEST);
        }
        kangarooMap.put(kangaroo.getId(), kangaroo);
        return ResponseEntity.ok(kangaroo);
    }

    @GetMapping
    public ResponseEntity<List<Kangaroo>> findAll() {
        return ResponseEntity.ok(new ArrayList<>(kangarooMap.values()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Kangaroo> findById(@PathVariable int id) {
        Kangaroo kangaroo = kangarooMap.get(id);
        if (kangaroo == null) {
            throw new ZooException("Kangaroo bulunamadı", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(kangaroo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Kangaroo> updateKangaroo(@PathVariable int id, @RequestBody Kangaroo updatedKangaroo) {
        Kangaroo existing = kangarooMap.get(id);
        if (existing == null) {
            throw new ZooException("Güncellenecek kangaroo bulunamadı", HttpStatus.NOT_FOUND);
        }
        updatedKangaroo.setId(id);
        kangarooMap.put(id, updatedKangaroo);
        return ResponseEntity.ok(updatedKangaroo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Kangaroo> deleteKangaroo(@PathVariable int id) {
        Kangaroo removed = kangarooMap.remove(id);
        if (removed == null) {
            throw new ZooException("Silinecek kangroo bulunamadı", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(removed);
    }
}
