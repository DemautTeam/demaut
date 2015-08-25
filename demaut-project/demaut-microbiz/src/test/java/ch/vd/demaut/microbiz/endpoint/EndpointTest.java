package ch.vd.demaut.microbiz.endpoint;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.ContentDisposition;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Ignore
public class EndpointTest {

    protected String webEndpoint = null;

    protected String projectDir = System.getProperty("user.dir");

    private ClassPathXmlApplicationContext xmlApplicationContext;

    @Before
    public void setUp() {
        xmlApplicationContext = new ClassPathXmlApplicationContext(new String[]{"/microbizTest-context.xml"});
        assertNotNull(xmlApplicationContext);

        ConfigurableListableBeanFactory beanFactory = xmlApplicationContext.getBeanFactory();
        assertNotNull(beanFactory);

        webEndpoint = beanFactory.resolveEmbeddedValue("${demaut.rs.base.endpoint}");
        assertNotNull(webEndpoint);

        webEndpoint = webEndpoint.replaceAll("0.0.0.0", "localhost");
        assertNotNull(webEndpoint);

        assertNotNull(projectDir);
    }

    @After
    public void tearDown() throws Exception {
        xmlApplicationContext.close();
    }

    @Test
    public void shouldFetchAnnexes() throws Exception {
        WebClient client = WebClient.create(webEndpoint + "/services/annexes/all", true);
        assertNotNull(client);

        Response response = client.get();
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }

    @Test
    public void shouldStoreAnnexe() throws Exception {
        WebClient client = WebClient.create(webEndpoint + "/services/annexe/store", true);
        assertNotNull(client);
        client.type(MediaType.MULTIPART_FORM_DATA_TYPE);

        List<Attachment> attachments = new LinkedList<>();

        ContentDisposition contentDisposition = new ContentDisposition("attachment;filename=demaut-microbizTest.cfg");

        FileInputStream fileInputStream = new FileInputStream("src/test/resources/demaut-microbizTest.cfg");
        assertNotNull(fileInputStream);

        Attachment attachment = new Attachment("file", fileInputStream, contentDisposition);
        assertNotNull(attachment);

        attachments.add(attachment);

        Response response = client.post(new MultipartBody(attachments, true));
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }
}