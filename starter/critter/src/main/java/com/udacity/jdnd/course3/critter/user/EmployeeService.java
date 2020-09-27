package com.udacity.jdnd.course3.critter.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long empId) {
        try {
            return employeeRepository.getOne(empId);
        } catch (Exception e) {
            throw new UserNotFoundException("There is no Employee with this ID !");
        }
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        try {
            Employee employee = employeeRepository.getOne(employeeId);
            employee.setDaysAvailable(daysAvailable);
        } catch (Exception e) {
            throw new UserNotFoundException("There is no Employee with this ID !");
        }
    }

    public List<Employee> getAvailableEmployeesWithSkills(Set<EmployeeSkill> neededSkills, LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        List<Employee> possEmployees = employeeRepository.findByDaysAvailableContaining(dayOfWeek);
        HashSet<Long> possIds = getIdSet(possEmployees);

        for (EmployeeSkill skill : neededSkills) {
            List<Employee> emps = employeeRepository.findBySkillsContaining(skill);
            HashSet<Long> newPossIds = new HashSet<>();
            for (Employee employee : emps)
                if (possIds.contains(employee.getId()))
                    newPossIds.add(employee.getId());
            possIds = newPossIds;
        }

        List<Employee> result = new ArrayList<>();
        for (Employee employee : possEmployees)
            if (possIds.contains(employee.getId()))
                result.add(employee);
        return result;
    }

    HashSet<Long> getIdSet(List<Employee> employees) {
        HashSet<Long> set = new HashSet<>();
        for (Employee employee : employees) {
            set.add(employee.getId());
        }
        return set;
    }


}
