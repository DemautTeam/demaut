package ch.vd.demaut.poc.microbiz;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultExchange;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

@ContextConfiguration({"classpath*:microbizTest-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MainProcessorTest {

    @Autowired
    private MainProcessor mainProcessor;

    @Autowired
    protected CamelContext camelContext;

    @Before
    public void setUp() throws Exception {
        assertNotNull(mainProcessor);
        assertNotNull(camelContext);
    }

    @Test
    public void shouldProcessExchange() throws Exception {
        Exchange exchange = new DefaultExchange(camelContext);
        assertNotNull(exchange);

        mainProcessor.process(exchange);

        assertNotNull(exchange.getOut());

        assertNotNull(exchange.getOut().getBody());
        assertNotNull(exchange.getOut().getBody().toString().contains(MainProcessor.ENVIRONMENT_KEY));
    }
}