package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetNotFoundException;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeService;
import com.udacity.jdnd.course3.critter.user.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private PetService petService;

    @Autowired
    private EmployeeService employeeService;

    public Schedule save (Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAll () {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getSchedulesForPet (Long petId) {
            Pet pet = petService.getPetById(petId);
            return scheduleRepository.findByPetsContaining(pet);
    }

    public List<Schedule> getSchedulesForEmployee (Long empId) {
            Employee employee = employeeService.getEmployeeById(empId);
            return scheduleRepository.findByEmployeesContaining(employee);
    }

    public List<Schedule> getSchedulesForCustomer (Long custId){
            List<Pet> pets = petService.getPetsByOwnerId(custId);
            HashSet<Long> schIds = new HashSet<>();
            ArrayList<Schedule> schedules = new ArrayList<>();
            for (Pet pet : pets) {
                List<Schedule> curSch = scheduleRepository.findByPetsContaining(pet);
                for (Schedule schedule : curSch) {
                    if (!schIds.contains(schedule.getId())) {
                        schIds.add(schedule.getId());
                        schedules.add(schedule);
                    }
                }
            }
            return schedules;
    }
}
