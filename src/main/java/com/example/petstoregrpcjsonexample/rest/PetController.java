package com.example.petstoregrpcjsonexample.rest;

import com.example.petstoregrpcjsonexample.domain.Pet;
import com.example.petstoregrpcjsonexample.service.PetDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/pets")
public class PetController {

    private final PetDataService petDataService;

    @Autowired
    public PetController(PetDataService petDataService) {
        this.petDataService = petDataService;
    }

    // Request body DTO for AddPet
    public static class AddPetRequestDto {
        private String name;
        private String status;

        // Getters and Setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    @PostMapping
    public ResponseEntity<Pet> addPet(@RequestBody AddPetRequestDto request) {
        if (request.getName() == null || request.getStatus() == null) {
            return ResponseEntity.badRequest().build(); // Basic validation
        }
        Pet newPet = petDataService.addPet(request.getName(), request.getStatus());
        return ResponseEntity.status(HttpStatus.CREATED).body(newPet);
    }

    @GetMapping("/{petId}")
    public ResponseEntity<Pet> getPetById(@PathVariable long petId) {
        return petDataService.getPetById(petId)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found with ID: " + petId));
    }
}
