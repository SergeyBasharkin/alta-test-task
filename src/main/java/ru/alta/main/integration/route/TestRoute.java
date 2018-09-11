package ru.alta.main.integration.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JacksonXMLDataFormat;
import org.springframework.stereotype.Component;
import ru.alta.main.model.Vacancy;

@Component
public class TestRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        JacksonXMLDataFormat format = new JacksonXMLDataFormat();
        format.setUnmarshalType(Vacancy.class);
        from("file:/data/input?delay=1000&noop=true").unmarshal(format).to("direct:testFile").marshal(format).to("direct:toFile");
    }
}
