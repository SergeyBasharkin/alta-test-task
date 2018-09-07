package ru.alta.main.controller;

import lombok.Data;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import ru.alta.main.dto.VacancyDTO;
import ru.alta.main.model.Vacancy;
import ru.alta.main.repository.VacancyRepository;

import java.net.URI;
import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class VacancyControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private VacancyRepository vacancyRepository;

    @Value("http://localhost:${local.server.port}/vacancy")
    private String appPath;

    private List<Vacancy> vacanciesTest;

    private HttpHeaders httpHeaders = new HttpHeaders();

    @Before
    public void setUp() {
        initVacancies();
        vacancyRepository.saveAll(vacanciesTest);
        httpHeaders.put("Accept", Collections.singletonList(MediaType.APPLICATION_XML_VALUE));
    }

    @After
    public void destroy(){
        vacanciesTest = null;
        vacancyRepository.deleteAll();
    }

    @Test
    public void findVacancyList() throws Exception {
        ResponseEntity<Set<Vacancy>> responseEntity = restTemplate.exchange(appPath, HttpMethod.GET, new HttpEntity<>(httpHeaders), new ParameterizedTypeReference<Set<Vacancy>>(){});
        Set<Vacancy> vacancySet = responseEntity.getBody();
        assertNotNull(vacancySet);
        assertArrayEquals(vacancySet.toArray() , vacanciesTest.toArray());
    }

    @Test
    public void findVacancyById() throws Exception {
        ResponseEntity<Vacancy> responseVacancy1 = restTemplate.exchange(appPath + "/1", HttpMethod.GET, new HttpEntity<>(httpHeaders), Vacancy.class);
        ResponseEntity<Vacancy> responseVacancy2 = restTemplate.exchange(appPath + "/2", HttpMethod.GET, new HttpEntity<>(httpHeaders), Vacancy.class);
        ResponseEntity<Vacancy> responseVacancy3 = restTemplate.exchange(appPath + "/3", HttpMethod.GET, new HttpEntity<>(httpHeaders), Vacancy.class);
        assertEquals(responseVacancy1.getBody(), vacanciesTest.get(0));
        assertEquals(responseVacancy2.getBody(), vacanciesTest.get(1));
        assertEquals(responseVacancy3.getBody(), vacanciesTest.get(2));

    }

    @Test
    public void saveVacancy() throws Exception {
        VacancyDTO vacancyDTO = VacancyDTO.builder()
                .city("Kazan")
                .experience("Middle")
                .name("Middle-end developer")
                .salaryLevel(50_000L)
                .build();
        RequestEntity<VacancyDTO> requestEntity = new RequestEntity<>(
                vacancyDTO,
                httpHeaders,
                HttpMethod.PUT,
                URI.create(appPath)
        );
        ResponseEntity<Vacancy> responseVacancy = restTemplate.exchange(requestEntity, Vacancy.class);
        assertNotNull(responseVacancy.getBody());
        assertEquals(responseVacancy.getBody().getName(), vacancyDTO.getName());
        assertTrue(vacancyRepository.findAllByOrderByName().stream().anyMatch(vacancy -> vacancy.getName().equals(vacancyDTO.getName())));

    }

    @Test
    public void deleteVacancyById() throws Exception {
        ResponseEntity response = restTemplate.exchange(appPath + "/1", HttpMethod.DELETE, new HttpEntity<>(httpHeaders), String.class);
        assertTrue(response.getStatusCode().equals(HttpStatus.OK));
        assertTrue(vacancyRepository.findAllByOrderByName().stream().noneMatch(vacancy -> vacancy.getId().equals(1L)));
    }

    private void initVacancies() {
        Vacancy v1 = Vacancy.builder()
                .name("QA Engeneer")
                .salaryLevel(50_000L)
                .experience("Middle")
                .city("Kazan")
                .build();
        Vacancy v2 = Vacancy.builder()
                .name("Back-end developer")
                .salaryLevel(50_000L)
                .experience("Middle")
                .city("Kazan")
                .build();
        Vacancy v3 = Vacancy.builder()
                .name("Front-end developer")
                .salaryLevel(50_000L)
                .experience("Middle")
                .city("Kazan")
                .build();
        this.vacanciesTest = Arrays.asList(v1, v2, v3);
    }

    @Data
    static class VacancySet{
        private Set<Vacancy> vacancySet = new HashSet<>();
    }
}