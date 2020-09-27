package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PetService petService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = convertFromDTO(scheduleDTO);
        schedule = scheduleService.save(schedule);
        return convertToDTO(schedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getAll();
        return convertListToDTO(schedules);
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> schedules = scheduleService.getSchedulesForPet(petId);
        return convertListToDTO(schedules);
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> schedules = scheduleService.getSchedulesForEmployee(employeeId);
        return convertListToDTO(schedules);
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> schedules = scheduleService.getSchedulesForCustomer(customerId);
        return convertListToDTO(schedules);
    }

    private Schedule convertFromDTO(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);
        schedule.setEmployees(getEmployeesFromIds(scheduleDTO.getEmployeeIds()));
        schedule.setPets(getPetsFromIds(scheduleDTO.getPetIds()));
        return schedule;
    }

    private List<Employee> getEmployeesFromIds(List<Long> empIds) {
        if(empIds == null) return null;
        List<Employee> employees = new ArrayList<>();
        for (Long id : empIds)
            employees.add(employeeService.getEmployeeById(id));
        return employees;
    }

    private List<Pet> getPetsFromIds(List<Long> petIds) {
        if(petIds == null) return null;
        List<Pet> pets = new ArrayList<>();
        for (Long id : petIds)
            pets.add(petService.getPetById(id));
        return pets;
    }



    private ScheduleDTO convertToDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);
        scheduleDTO.setEmployeeIds(getIdsFromEmp(schedule.getEmployees()));
        scheduleDTO.setPetIds(getIdsFromPets(schedule.getPets()));
        return scheduleDTO;
    }

    List<Long> getIdsFromEmp(List<Employee> employees) {
        if(employees == null) return null;
        List<Long> ids = new ArrayList<>();
        for( Employee employee: employees)
            ids.add(employee.getId());
        return ids;
    }

    List<Long> getIdsFromPets(List<Pet> pets) {
        if(pets == null) return null;
        List<Long> ids = new ArrayList<>();
        for( Pet pet: pets)
            ids.add(pet.getId());
        return ids;
    }

    private List<ScheduleDTO> convertListToDTO(List<Schedule> schedules) {
        ArrayList<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        for (Schedule schedule : schedules)
            scheduleDTOs.add(convertToDTO(schedule));
        return scheduleDTOs;
    }
}
