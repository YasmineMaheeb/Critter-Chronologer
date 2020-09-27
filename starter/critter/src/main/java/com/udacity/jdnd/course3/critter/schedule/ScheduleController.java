package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        throw new UnsupportedOperationException();
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        throw new UnsupportedOperationException();
    }

//    private Schedule convertFromDTO (ScheduleDTO scheduleDTO) {
//        Schedule schedule = new Schedule();
//        BeanUtils.copyProperties(scheduleDTO, schedule);
//        schedule.setOwner(ownerService.getById(scheduleDTO.getOwnerId()));
//        return schedule;
//    }
//
//    private ScheduleDTO convertToDTO (Schedule schedule) {
//        ScheduleDTO scheduleDTO = new ScheduleDTO();
//        BeanUtils.copyProperties(schedule, scheduleDTO);
//        scheduleDTO.setOwnerId(schedule.getOwner().getId());
//        return scheduleDTO;
//    }
//
//    private List<ScheduleDTO> convertListToDTO (List<Schedule> schedules) {
//        ArrayList<ScheduleDTO> scheduleDTOs = new ArrayList<>();
//        for( Schedule schedule: schedules)
//            scheduleDTOs.add(convertToDTO(schedule));
//        return scheduleDTOs;
//    }
}
