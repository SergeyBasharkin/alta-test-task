package ru.alta.main.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.alta.main.dto.VacancyDTO;
import ru.alta.main.exception.VacancyNotFoundException;
import ru.alta.main.model.Vacancy;
import ru.alta.main.service.VacancyService;

import javax.validation.Valid;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/vacancy", produces = MediaType.APPLICATION_XML_VALUE)
public class VacancyController {

    private final VacancyService vacancyService;

    @GetMapping
    public Set<Vacancy> findVacancyList(){
        return vacancyService.findAllOrderByName();
    }

    @GetMapping("/{id}")
    public Vacancy findVacancyById(@PathVariable Long id){
        return vacancyService.findById(id).orElseThrow(VacancyNotFoundException::new);
    }

    @PutMapping
    public Vacancy saveVacancy(@RequestBody @Valid VacancyDTO vacancyDTO){
        return vacancyService.saveVacancy(vacancyDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteVacancyById(@PathVariable Long id){
        vacancyService.deleteVacancyById(id);
    }

}
