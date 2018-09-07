package ru.alta.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alta.main.model.Vacancy;

import java.util.Set;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
    Set<Vacancy> findAllByOrderByName();
}
