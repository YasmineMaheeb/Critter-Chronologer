package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.schedule.Schedule;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private PetService petService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = customerService.save(convertFromDTO(customerDTO));
        return convertToDTO(customer);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customers = customerService.getAll();
        return convertListToDTO(customers);
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Customer customer = customerService.getByPetId(petId);
        return convertToDTO(customer);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = employeeService.save(convertEmployeeFromDTO(employeeDTO));
        return convertEmployeeToDTO(employee);
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        return convertEmployeeToDTO(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setAvailability(daysAvailable, employeeId);
    }

    @PostMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<Employee> employees = employeeService.getAvailableEmployeesWithSkills(employeeDTO.getSkills(),employeeDTO.getDate());
        return convertEmployeeListToDTO(employees);
    }

/////      FOR CUSTOMERS          ////
    private Customer convertFromDTO(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        customer.setPets(getPetsFromIds(customerDTO.getPetIds()));
        return customer;
    }

    private List<Pet> getPetsFromIds(List<Long> petIds) {
        if(petIds == null) return null;
        List<Pet> pets = new ArrayList<>();
        for (Long id : petIds)
            pets.add(petService.getPetById(id));
        return pets;
    }

    private CustomerDTO convertToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        customerDTO.setPetIds(getIdsFromPets(customer.getPets()));
        return customerDTO;
    }
    

    List<Long> getIdsFromPets(List<Pet> pets) {
        if(pets == null) return null;
        List<Long> ids = new ArrayList<>();
        for( Pet pet: pets)
            ids.add(pet.getId());
        return ids;
    }

    private List<CustomerDTO> convertListToDTO(List<Customer> customers) {
        ArrayList<CustomerDTO> customerDTOs = new ArrayList<>();
        for (Customer customer : customers)
            customerDTOs.add(convertToDTO(customer));
        return customerDTOs;
    }
    
    
    ///         FOR EMPLOYEES             /////
    private Employee convertEmployeeFromDTO(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        return employee;
    }

    private EmployeeDTO convertEmployeeToDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }

    private List<EmployeeDTO> convertEmployeeListToDTO(List<Employee> employees) {
        ArrayList<EmployeeDTO> employeeDTOs = new ArrayList<>();
        for (Employee employee : employees)
            employeeDTOs.add(convertEmployeeToDTO(employee));
        return employeeDTOs;
    }
    
}
