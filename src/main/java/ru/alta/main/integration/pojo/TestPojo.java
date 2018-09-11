package ru.alta.main.integration.pojo;

import org.apache.camel.Consume;
import org.apache.camel.Exchange;
import org.apache.camel.RecipientList;
import org.apache.camel.component.file.GenericFileMessage;
import org.springframework.stereotype.Component;
import ru.alta.main.model.Vacancy;

import java.util.Map;

@Component
public class TestPojo {

    public String testIn(Exchange exchange) {
        return "Hello from pojo";
    }

    @Consume(uri = "direct:testOut")
    public void testOut(String body) {
        System.out.println(body);
    }

    @Consume(uri = "direct:testFile")
    public Object list(Vacancy vacancy, Exchange exchange) {
        GenericFileMessage customMessage = (GenericFileMessage) exchange.getIn();
        Map<String, Object> map = customMessage.getHeaders();
        map.put("customUri", "file:" + "/data/output/" + vacancy.getCity() + "?fileName=" + vacancy.getId() + ".xml");
        customMessage.setHeaders(map);
        exchange.setOut(customMessage);
        return exchange.getIn().getBody();
    }

    @Consume(uri = "direct:toFile")
    @RecipientList
    public String toFile(Object body, Exchange exchange) {
        String uri = (String) exchange.getIn().getHeader("customUri");
        return !uri.isEmpty() ? uri : null;
    }
}
