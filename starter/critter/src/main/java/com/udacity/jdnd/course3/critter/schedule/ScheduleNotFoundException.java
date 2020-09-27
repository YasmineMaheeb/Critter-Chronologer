package com.udacity.jdnd.course3.critter.schedule;

import javax.persistence.EntityNotFoundException;

public class ScheduleNotFoundException extends EntityNotFoundException {

    public ScheduleNotFoundException() {
    }

    public ScheduleNotFoundException(String message) {
        super(message);
    }
}
