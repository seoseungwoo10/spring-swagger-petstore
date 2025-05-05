package com.example.petstoregrpcjsonexample.service;

import com.example.petstoregrpcjsonexample.domain.Pet;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PetDataService {

    private final Map<Long, Pet> petStore = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong();

    /**
     * Adds a new pet to the in-memory store.
     *
     * @param name   The name of the pet.
     * @param status The status of the pet.
     * @return The newly created Pet object with its assigned ID.
     */
    public Pet addPet(String name, String status) {
        long newId = idCounter.incrementAndGet();
        Pet newPet = new Pet(newId, name, status);
        petStore.put(newId, newPet);
        return newPet;
    }

    /**
     * Retrieves a pet by its ID.
     *
     * @param petId The ID of the pet to retrieve.
     * @return An Optional containing the Pet if found, otherwise an empty Optional.
     */
    public Optional<Pet> getPetById(long petId) {
        return Optional.ofNullable(petStore.get(petId));
    }
}
