package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
public class PetService {

    @Autowired
    private PetRepository petRepository;

    public Pet save (Pet pet) {
        return petRepository.save(pet);
    }

    public Pet getPetById (Long id) {
        try {
            Pet pet = petRepository.getOne(id);
            return pet;
        } catch (Exception c) {
            throw new PetNotFoundException("There is no pet with this ID !");
        }
    }

    public List<Pet> getAll () {
        return petRepository.findAll();
    }

    public List<Pet> getPetsByOwnerId (Long ownerId) {
        try {
            return petRepository.findByOwnerId(ownerId);
        } catch (Exception e) {
            throw new PetNotFoundException("There is no pet with this owner ID !");
        }
    }

}