package com.example.studentmanagementjpa.controller;

import com.example.studentmanagementjpa.model.Classroom;
import com.example.studentmanagementjpa.model.Student;
import com.example.studentmanagementjpa.service.IClassroomService;
import com.example.studentmanagementjpa.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/classrooms")
public class ClassroomController {
    @Autowired
    private IClassroomService classroomService;

    @Autowired
    private IStudentService studentService;

    @Autowired
    private HttpSession httpSession;

    @GetMapping
    public ModelAndView listClassrooms() {
        ModelAndView modelAndView = new ModelAndView("/classroom_list");
        Iterable<Classroom> classrooms = classroomService.findAll();
        modelAndView.addObject("classrooms", classrooms);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createForm() {
        ModelAndView modelAndView = new ModelAndView("/classroom_create");
        modelAndView.addObject("classroom", new Classroom());
        return modelAndView;
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("classroom") Classroom classroom,
                         RedirectAttributes redirectAttributes) {
        classroomService.save(classroom);
        redirectAttributes.addFlashAttribute("message", "Create new classroom successfully");

        httpSession.setAttribute("classroom", classroom);
        return "redirect:/classrooms";
    }

    @GetMapping("/update/{id}")
    public ModelAndView updateForm(@PathVariable Long id) {
        Optional<Classroom> classroom = classroomService.findById(id);
        if (classroom.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/classroom_update");
            modelAndView.addObject("classroom", classroom.get());
            return modelAndView;
        } else {
            return new ModelAndView("/error-404");
        }
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("classroom") Classroom classroom,
                         RedirectAttributes redirectAttributes) {
        classroomService.save(classroom);
        redirectAttributes.addFlashAttribute("message", "Update classroom successfully");
        return "redirect:/classrooms";
    }

    @GetMapping("/view-classroom/{id}")
    public ModelAndView viewClassroom(@PathVariable("id") Long id) {
        Optional<Classroom> classroomOptional = classroomService.findById(id);
        if (classroomOptional.isPresent()) {
            Iterable<Student> students = studentService.findAllByClassroom(classroomOptional.get());

            ModelAndView modelAndView = new ModelAndView("/student_list");
            modelAndView.addObject("students", students);
            return modelAndView;
        } else {
            return new ModelAndView("/error-404");
        }
    }

    @GetMapping("/viewSession")
    public ModelAndView viewSession() {
        ModelAndView modelAndView = new ModelAndView("/session");
        Classroom classroom = (Classroom) httpSession.getAttribute("classroom");
        modelAndView.addObject("classroom",classroom);
        return modelAndView;
    }
}
