package com.example.studentmanagementjpa.controller;

import com.example.studentmanagementjpa.model.Classroom;
import com.example.studentmanagementjpa.model.Student;
import com.example.studentmanagementjpa.service.IClassroomService;
import com.example.studentmanagementjpa.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private IStudentService studentService;

    @Autowired
    private IClassroomService classroomService;

    @ModelAttribute("classrooms")
    public Iterable<Classroom> listClassrooms() {
        return classroomService.findAll();
    }

    @GetMapping
    public ModelAndView listStudents(@CookieValue(name = "counter", defaultValue = "0") Long counter,
                                     HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("/student_list");
        Iterable<Student> students = studentService.findAll();
        modelAndView.addObject("students", students);

        //Sử dụng cookie để theo dõi lượt truy cập request của 1 user
        counter++;
        Cookie cookie = new Cookie("counter", counter.toString());
        cookie.setMaxAge(20);
        response.addCookie(cookie);
        modelAndView.addObject("cookie", cookie);

        return modelAndView;
    }
    @GetMapping("/create")
    public ModelAndView createForm() {
        ModelAndView modelAndView = new ModelAndView("/student_add");
        modelAndView.addObject("student", new Student());
        return modelAndView;
    }
    @PostMapping("/create")
    public ModelAndView create(@Validated @ModelAttribute Student student,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("/student_add");
            modelAndView.addObject("student", student);
            return modelAndView;
        } else {
            studentService.save(student);
            return new ModelAndView("/student_list");
        }

    }
    @GetMapping("/update/{id}")
    public ModelAndView updateForm(@PathVariable Long id) {
        Optional<Student> student = studentService.findById(id);
        if (student.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/student_update");
            modelAndView.addObject("student", student.get());
            return modelAndView;
        } else {
            return new ModelAndView("/error-404");
        }
    }
    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("student") Student student,
                         RedirectAttributes redirect) {
        studentService.save(student);
        redirect.addFlashAttribute("message", "Update student successfully");
        return "redirect:/students";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                         RedirectAttributes redirect) {
        studentService.remove(id);
        redirect.addFlashAttribute("message", "Delete student successfully");
        return "redirect:/students";
    }
    @GetMapping("/filter")
    public ModelAndView genderFilter(@RequestParam String gender) {
        Iterable<Student> students = studentService.findAll();
        ModelAndView modelAndView = new ModelAndView("/student_list");
        if (Objects.equals(gender, "")) {
            modelAndView.addObject("students", students);
            return modelAndView;
        } else {
            students = studentService.findAllByGender(gender);
            modelAndView.addObject("students", students);
            return modelAndView;
        }
    }

    @GetMapping("/sort")
    public ModelAndView sortByAge() {
        List<Student> studentList = (List<Student>) studentService.findAll();
        studentList.sort(studentService);
        ModelAndView modelAndView = new ModelAndView("/student_list");
        modelAndView.addObject("students", studentList);
        return modelAndView;
    }
}
