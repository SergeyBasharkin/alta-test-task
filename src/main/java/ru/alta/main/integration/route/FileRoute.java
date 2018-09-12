package ru.alta.main.integration.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.alta.main.model.Vacancy;

public class FileRoute extends RouteBuilder {
    private String to = "sergey.basharkin@bk.ru";

    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;

    @Override
    public void configure() throws Exception {
        from("file:/data/input")
                .doTry()
                    .unmarshal().jacksonxml(Vacancy.class)
                    .to("direct:newFilePathHeader")
                        .marshal()
                        .jacksonxml(Vacancy.class)
                    .recipientList(header("customUri"))
                .endDoTry()
                .doCatch(Exception.class)
                    .to("bean:vacancyConsumer?method=attachFile")
                    .to("smtps://"+host+"?to="+to+"&password="+password+"&username="+username)
                .end();
    }
}
