package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetNotFoundException;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent())
            return customer.get();
        else
            throw new UserNotFoundException("There is no customer with this ID !");
    }

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    public Customer getByPetId(Long petId) {

        Optional<Pet> pet = petRepository.findById(petId);
        if (pet.isPresent()) {
            Customer owner = customerRepository.findByPetsContaining(pet.get());
            return owner;
        }
        throw new PetNotFoundException("There is no pet with this ID !");

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
