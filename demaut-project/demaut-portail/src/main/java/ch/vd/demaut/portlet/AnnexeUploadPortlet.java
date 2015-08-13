package ch.vd.demaut.portlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.ProcessAction;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.FormBodyPartBuilder;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class AnnexeUploadPortlet
 */
public class AnnexeUploadPortlet extends MVCPortlet {

	private static final String uploadMultipartUrl = "http://10.240.19.130:8083/poc-demaut/services/annexe/multipart";

	private static final String progeSOATiersQuery = "http://localhost:40002/outils/progreSOA/tiers/query";

	private final Log log = LogFactoryUtil.getLog(getClass());

	// private final static File folder = new File("/tmp/demaut");

	private final static String fileFieldName = "annexeFile";

	// @ProcessAction(name = "uploadAnnexe")
	// public void processUploadAnnexe(ActionRequest request,
	// ActionResponse response) throws PortletException {
	// log.info("getting the file");
	// log.info(request.getContentType());
	// log.info("data length " + request.getContentLength());
	// if (request.getContentType().contains("json")) {
	// log.info("mode json");
	// int contentlength = request.getContentLength();
	// try {
	// InputStream is = new
	// BufferedInputStream(request.getPortletInputStream());
	// byte[] content = new byte[contentlength];
	// is.read(content);
	// String contentStr = new String(content);
	// log.info("content : \n" + contentStr);
	// OutputStream writer = new FileOutputStream(
	// "/home/mourad/temp/test.json");
	// writer.write(content);
	// writer.flush();
	// writer.close();
	// HttpClient httpClient = HttpClientBuilder.create().build();
	// HttpPost httpPost = new HttpPost(
	// "http://10.240.19.130:8082/poc-demaut/services/annexe/store");
	// FileEntity entity = new FileEntity(new File(
	// "/home/mourad/temp/test.json"),
	// ContentType.APPLICATION_JSON);
	// log.info("content length : " + entity.getContentLength());
	// httpPost.setEntity(entity);
	// httpPost.setHeader("Content-Type", request.getContentType());
	// //httpPost.setHeader("Content-Length",
	// // Integer.toString(request.getContentLength()));
	// httpPost.setHeader("Accept", request.getContentType());
	// HttpResponse httpResponse = httpClient.execute(httpPost);
	// StatusLine replyStatus = httpResponse.getStatusLine();
	// log.info("Response code : " + replyStatus.getStatusCode()
	// + " : " + replyStatus.getReasonPhrase());
	// HttpEntity responseEntity = httpResponse.getEntity();
	// InputStream replyIs = responseEntity.getContent();
	// byte[] replybuffer = new byte[(int) responseEntity
	// .getContentLength()];
	// replyIs.read(replybuffer);
	// log.info("Reply content : " + new String(replybuffer));
	// } catch (IOException e) {
	// log.info("request NOK", e);
	// }
	//
	// } else {
	// log.info("mode http upload");
	// UploadPortletRequest uploadRequest = PortalUtil
	// .getUploadPortletRequest(request);
	// log.info("File size : " + uploadRequest.getSize(fileFieldName));
	// if (uploadRequest.getSize("annexeFile") == 0) {
	// SessionErrors.add(request, "error");
	// } else {
	// String realFileName = uploadRequest.getFileName(fileFieldName);
	// // File theFile = uploadRequest.getFile(fileFieldName);
	// if (!folder.exists()) {
	// log.info("Création du répertoire");
	// folder.mkdirs();
	// }
	// File outFile = new File(folder, realFileName);
	// try (InputStream fileIn = new BufferedInputStream(
	// uploadRequest.getFileAsStream(fileFieldName, true));
	// OutputStream fileOut = new FileOutputStream(outFile);) {
	// byte[] content = FileUtil.getBytes(fileIn);
	// fileOut.write(content);
	//
	// } catch (IOException e) {
	// SessionErrors.add(request, "error");
	// log.error(e);
	// }
	//
	// }
	// }
	// }

	@Override
	public void serveResource(ResourceRequest resourceRequest,
			ResourceResponse resourceResponse) throws IOException,
			PortletException {

		String action = resourceRequest.getParameter("ajaxAction");

		switch (action) {
		case "upload":
			uploadAction(resourceRequest, resourceResponse);
			break;
		case "progreSOATiers":
			progreSOATiersAction(resourceRequest, resourceResponse);
			break;
		default:
			log.info("action non trouvée" + action);
		}

	}

	private void progreSOATiersAction(ResourceRequest resourceRequest,
			ResourceResponse resourceResponse) throws IOException,
			ClientProtocolException {
		String nom = resourceRequest.getParameter("nom");
		try {
			URIBuilder uriBuilder = new URIBuilder(progeSOATiersQuery);
			uriBuilder.setParameter("nom", nom);
			HttpGet queryGet = new HttpGet(uriBuilder.build());
			queryGet.setHeader("Accept",
					ContentType.APPLICATION_JSON.getMimeType());
			HttpResponse httpResponse = getHttpClient().execute(queryGet);
			log.info(httpResponse.getStatusLine().getStatusCode() + " : " + httpResponse.getStatusLine().getReasonPhrase());
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity responseEntity = httpResponse.getEntity();
				InputStream is = new BufferedInputStream(
						responseEntity.getContent());
				OutputStream out = resourceResponse.getPortletOutputStream();
				byte[] content = new byte[1024];
				int sizeRead;
				while((sizeRead = is.read(content) ) == 1024){
					out.write(content);
				}
				if(sizeRead != 1024){
					out.write(content, 0, sizeRead);
				}
				is.close();
				out.close();
			}
		} catch (URISyntaxException ue) {
			log.error("Problème d'URI ! ", ue);
		}

	}

	private void uploadAction(ResourceRequest resourceRequest,
			ResourceResponse resourceResponse) throws IOException,
			ClientProtocolException {
		log.info("Serving resources...");
		log.info("Type de données : " + resourceRequest.getContentType());
		UploadPortletRequest uploadRequest = PortalUtil
				.getUploadPortletRequest(resourceRequest);
		if (uploadRequest.getSize("annexeFile") == 0) {
			SessionErrors.add(resourceRequest, "error");
		} else {
			String realFileName = uploadRequest.getFileName(fileFieldName);
			log.info("Fichier : " + realFileName);
			HttpPost httpPost = new HttpPost(uploadMultipartUrl);
			MultipartEntityBuilder mpbuilder = MultipartEntityBuilder.create();
			mpbuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			FormBodyPart annexeFileName = FormBodyPartBuilder.create("name",
					new StringBody(realFileName, ContentType.DEFAULT_TEXT))
					.build();
			FormBodyPart annexeFileSize = FormBodyPartBuilder.create(
					"size",
					new StringBody(Long.toString(uploadRequest
							.getSize("annexeFile")), ContentType.DEFAULT_TEXT))
					.build();
			FormBodyPart annexeFileType = FormBodyPartBuilder.create(
					"type",
					new StringBody(uploadRequest.getContentType("annexeFile"),
							ContentType.DEFAULT_TEXT)).build();
			mpbuilder.addPart(annexeFileName);
			mpbuilder.addPart(annexeFileType);
			mpbuilder.addPart(annexeFileSize);
			mpbuilder.addBinaryBody("file", new BufferedInputStream(
					uploadRequest.getFileAsStream(fileFieldName, true)),
					ContentType.APPLICATION_OCTET_STREAM, realFileName);

			httpPost.setEntity(mpbuilder.build());

			HttpResponse httpResponse = getHttpClient().execute(httpPost);
			StatusLine status = httpResponse.getStatusLine();
			log.info("Réponse du serveur : " + status.getStatusCode() + " "
					+ status.getReasonPhrase());

		}
		resourceResponse.getPortletOutputStream().write("OK !".getBytes());
	}

	private HttpClient getHttpClient() {
		return HttpClientBuilder.create().build();
	}

}
