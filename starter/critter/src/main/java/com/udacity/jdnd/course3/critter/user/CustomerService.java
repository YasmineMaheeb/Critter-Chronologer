package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetNotFoundException;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PetRepository petRepository;

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer getById(Long id) {
        try {
            return customerRepository.getOne(id);
        } catch (Exception e) {
            throw new UserNotFoundException("There is no customer with this ID !");
        }
    }

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    public Customer getByPetId(Long petId) {
        try {
            Pet pet = petRepository.getOne(petId);
            Customer owner = customerRepository.findByPetsContaining(pet);
            System.out.println(pet);
            return owner;
        } catch (Exception e) {
            throw new PetNotFoundException("There is no pet with this ID !");
        }
    }

    public void addPetToCustomer(Pet pet, Customer customer) {
        List<Pet> pets = customer.getPets();
        if (pets != null)
            pets.add(pet);
        else {
            pets = new ArrayList<Pet>();
            pets.add(pet);
        }
        customer.setPets(pets);
        customerRepository.save(customer);
    }
}
