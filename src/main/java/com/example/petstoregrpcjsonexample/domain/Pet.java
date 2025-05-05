package com.example.petstoregrpcjsonexample.domain;

// Simple domain object for Pet
public class Pet {
    private long id;
    private String name;
    private String status;

    public Pet(long id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return "Pet{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", status='" + status + '\'' +
               '}';
    }

    // Optional: equals and hashCode for map operations if needed
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return id == pet.id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }
}
