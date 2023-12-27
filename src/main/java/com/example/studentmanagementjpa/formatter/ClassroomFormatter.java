package com.example.studentmanagementjpa.formatter;

import com.example.studentmanagementjpa.model.Classroom;
import com.example.studentmanagementjpa.service.IClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;
import java.util.Optional;

@Component
public class ClassroomFormatter implements Formatter<Classroom> {

    private final IClassroomService classroomService;
    @Autowired
    public ClassroomFormatter(IClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @Override
    public Classroom parse(String text, Locale locale) throws ParseException {
        Optional<Classroom> classroomOptional = classroomService.findById(Long.parseLong(text));
        return classroomOptional.orElse(null);
    }

    @Override
    public String print(Classroom object, Locale locale) {
        return "[" + object.getId() + ", " +object.getName() + "]";
    }
}
