package ru.alta.main.configurations;

import org.apache.camel.processor.idempotent.FileIdempotentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;

@Configuration
public class FileBasedIdempotentRepositoryConfig {

    @Bean
    public FileIdempotentRepository fileIdempotentRepository() throws IOException {
        FileIdempotentRepository fileIdempotentRepository = new FileIdempotentRepository();
        File store = new File("/data/store.txt");
        if (!store.exists()) store.createNewFile();
        fileIdempotentRepository.setFileStore(store);
        return fileIdempotentRepository;
    }

}
