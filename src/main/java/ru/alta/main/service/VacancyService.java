package ru.alta.main.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.alta.main.dto.VacancyDTO;
import ru.alta.main.exception.VacancyNotFoundException;
import ru.alta.main.model.Vacancy;
import ru.alta.main.repository.VacancyRepository;

import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class VacancyService {
    private final VacancyRepository vacancyRepository;

    public Set<Vacancy> findAllOrderByName(){
        return vacancyRepository.findAllByOrderByName();
    }

    public Optional<Vacancy> findById(Long id){
        return vacancyRepository.findById(id);
    }

    public Vacancy saveVacancy(VacancyDTO vacancyDTO){
        return vacancyRepository.save(Vacancy.builder()
                .name(vacancyDTO.getName())
                .salaryLevel(vacancyDTO.getSalaryLevel())
                .city(vacancyDTO.getCity())
                .experience(vacancyDTO.getExperience())
                .build()
        );
    }

    public void deleteVacancyById(Long id){
        vacancyRepository.delete(
                vacancyRepository.findById(id)
                        .orElseThrow(VacancyNotFoundException::new)
        );
    }
}
