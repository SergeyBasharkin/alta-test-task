package ru.alta.main.integration.consumer;

import org.apache.camel.Consume;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.RecipientList;
import org.apache.camel.component.file.GenericFileMessage;
import org.springframework.stereotype.Component;
import ru.alta.main.model.Vacancy;

import javax.activation.DataHandler;
import java.util.Map;

@Component
public class VacancyConsumer {
    @RecipientList
    public String detectFilePath(Vacancy vacancy, Exchange exchange){
        return "file:" + "/data/output/" + vacancy.getCity() + "?fileName=" + exchange.getIn().getHeader("CamelFileName", String.class);
    }

    public void attachFile(Exchange exchange, Exception exception){
        Message in = exchange.getIn();
        byte[] file = in.getBody(byte[].class);
        String fileId = in.getHeader("CamelFileName",String.class);
        in.addAttachment(fileId, new DataHandler(file,"application/xml"));
        in.setBody(exception.getMessage());
        exchange.setIn(in);
    }

    @Consume(uri = "direct:newFilePathHeader")
    public Object list(Vacancy vacancy, Exchange exchange) {
        GenericFileMessage customMessage = (GenericFileMessage) exchange.getIn();
        customMessage.getHeaders().put("customUri", "file:" + "/data/output/" + vacancy.getCity() + "?fileName=" + exchange.getIn().getHeader("CamelFileName", String.class));
        exchange.setIn(customMessage);
        return exchange.getIn().getBody();
    }

    @Consume(uri = "direct:toFile")
    @RecipientList
    public String toFile(Exchange exchange) {
        String uri = (String) exchange.getIn().getHeader("customUri");
        return !uri.isEmpty() ? uri : null;
    }
}
