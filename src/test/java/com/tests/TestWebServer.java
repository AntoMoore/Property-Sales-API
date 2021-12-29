package com.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.backend.WebServer;
import com.controllers.DatabaseController;
import com.google.gson.Gson;
import com.resources.Agent;
import com.resources.Property;
import com.resources.Sale;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestWebServer {
	// member variables
	private static WebServer testServer;
	private static final String CONNECTION_TYPE = "TEST"; // (production/test)
	private static DatabaseController databaseController = DatabaseController.getInstance();

	@BeforeAll
	public static void startTestConnection() {
		testServer = new WebServer();
		testServer.startWebServer();
		databaseController.connect(CONNECTION_TYPE);
	}
	
	@Test
	@Order(1)
	void testStatusCheck() {
		HttpUriRequest request = new HttpGet("http://localhost:4567/openproperty/status");
		HttpResponse httpResponse = httpResponseHelper(request);
		assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
	}
	
	@Test
	@Order(2)
	void testPostAgent() throws ClientProtocolException, IOException {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost  httpPost = new HttpPost("http://localhost:4567/openproperty/agents");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("name", "TestName"));
		params.add(new BasicNameValuePair("commission", "1"));
		httpPost.setEntity(new UrlEncodedFormEntity(params));
		
		CloseableHttpResponse response = client.execute(httpPost);
		assertEquals(HttpStatus.SC_CREATED, response.getStatusLine().getStatusCode());
		client.close();
	}
	
	@Test
	@Order(3)
	void testPostProperty() throws ClientProtocolException, IOException {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost  httpPost = new HttpPost("http://localhost:4567/openproperty/properties");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("type", "TestLab"));
		params.add(new BasicNameValuePair("address", "123 Test Street"));
		params.add(new BasicNameValuePair("value", "123456"));
		params.add(new BasicNameValuePair("agentId", "1"));
		httpPost.setEntity(new UrlEncodedFormEntity(params));
		
		CloseableHttpResponse response = client.execute(httpPost);
		assertEquals(HttpStatus.SC_CREATED, response.getStatusLine().getStatusCode());
		client.close();
	}
	
	@Test
	@Order(4)
	void testPostSale() throws ClientProtocolException, IOException {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost  httpPost = new HttpPost("http://localhost:4567/openproperty/sales");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("propertyId", "1"));
		httpPost.setEntity(new UrlEncodedFormEntity(params));
		
		CloseableHttpResponse response = client.execute(httpPost);
		assertEquals(HttpStatus.SC_CREATED, response.getStatusLine().getStatusCode());
		client.close();
	}
	
	@Test
	@Order(5)
	void testGetAgentRequest() throws UnsupportedOperationException, IOException {
		HttpUriRequest request = new HttpGet("http://localhost:4567/openproperty/agents/");
		HttpResponse httpResponse = httpResponseHelper(request);
		assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
	}
	
	@Test
	@Order(6)
	void testGetAgentRequestById() throws UnsupportedOperationException, IOException {
		String response = httpRequestHelper("http://localhost:4567/openproperty/agents/?id=1");
		Gson gson = new Gson();
		Agent[] agents = gson.fromJson(response, Agent[].class);
		assertEquals(1, agents[0].getAgentId());
	}
	
	@Test
	@Order(7)
	void testGetAgentRequestByName() throws UnsupportedOperationException, IOException {
		String response = httpRequestHelper("http://localhost:4567/openproperty/agents/?name=TestName");
		Gson gson = new Gson();
		Agent[] agents = gson.fromJson(response, Agent[].class);
		assertEquals("TestName", agents[0].getAgentName());
	}
	
	@Test
	@Order(8)
	void testGetAgentRequestByCommission() throws UnsupportedOperationException, IOException {
		String response = httpRequestHelper("http://localhost:4567/openproperty/agents/?commission=1");
		Gson gson = new Gson();
		Agent[] agents = gson.fromJson(response, Agent[].class);
		assertEquals(1f, agents[0].getAgentCommission());
	}
	
	@Test
	@Order(9)
	void testGetPropertyRequest() throws UnsupportedOperationException, IOException {
		HttpUriRequest request = new HttpGet("http://localhost:4567/openproperty/properties/");
		HttpResponse httpResponse = httpResponseHelper(request);
		assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
	}
	
	@Test
	@Order(10)
	void testGetPropertyRequestById() throws UnsupportedOperationException, IOException {
		String response = httpRequestHelper("http://localhost:4567/openproperty/properties/?id=1");
		Gson gson = new Gson();
		Property[] properties = gson.fromJson(response, Property[].class);
		assertEquals(1, properties[0].getPropertyId());
	}
	
	@Test
	@Order(11)
	void testGetPropertyRequestByType() throws UnsupportedOperationException, IOException {
		String response = httpRequestHelper("http://localhost:4567/openproperty/properties/?type=TestLab");
		Gson gson = new Gson();
		Property[] properties = gson.fromJson(response, Property[].class);
		assertEquals("TestLab", properties[0].getPropertyType());
	}
	
	@Test
	@Order(12)
	void testGetPropertyRequestByValue() throws UnsupportedOperationException, IOException {
		String response = httpRequestHelper("http://localhost:4567/openproperty/properties/?value=123456");
		Gson gson = new Gson();
		Property[] properties = gson.fromJson(response, Property[].class);
		assertEquals(123456, properties[0].getPropertyValue());
	}
	
	@Test
	@Order(13)
	void testGetSaleRequest() throws UnsupportedOperationException, IOException {
		HttpUriRequest request = new HttpGet("http://localhost:4567/openproperty/sales/");
		HttpResponse httpResponse = httpResponseHelper(request);
		assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
	}
	
	@Test
	@Order(14)
	void testGetSaleRequestById() throws UnsupportedOperationException, IOException {
		String response = httpRequestHelper("http://localhost:4567/openproperty/sales/?id=1");
		Gson gson = new Gson();
		Sale[] sales = gson.fromJson(response, Sale[].class);
		assertEquals(1, sales[0].getSaleId());
	}
	
	@Test
	@Order(15)
	void testGetSaleRequestByDate() throws UnsupportedOperationException, IOException {
		HttpUriRequest request = new HttpGet("http://localhost:4567/openproperty/sales/?date=year");
		HttpResponse httpResponse = httpResponseHelper(request);
		assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
	}
	
	@Test
	@Order(16)
	void testGetSaleRequestByAgent() throws UnsupportedOperationException, IOException {
		String response = httpRequestHelper("http://localhost:4567/openproperty/sales/?agent=1");
		Gson gson = new Gson();
		Sale[] sales = gson.fromJson(response, Sale[].class);
		assertEquals(1, sales[0].getSaleProperty().getPropertyAgent().getAgentId());
	}
	
	@Test
	@Order(17)
	void testDeleteAgent() throws UnsupportedOperationException, IOException {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpDelete  delete = new HttpDelete("http://localhost:4567/openproperty/agents/remove/?id=1");
		HttpResponse response = client.execute(delete);
		assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
		client.close();
	}
	
	@Test
	@Order(18)
	void testDeleteProperty() throws UnsupportedOperationException, IOException {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpDelete  delete = new HttpDelete("http://localhost:4567/openproperty/properties/remove/?id=1");
		HttpResponse response = client.execute(delete);
		assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
		client.close();
	}
	
	@Test
	@Order(19)
	void testDeleteSale() throws UnsupportedOperationException, IOException {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpDelete  delete = new HttpDelete("http://localhost:4567/openproperty/sales/remove/?id=1");
		HttpResponse response = client.execute(delete);
		assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
		client.close();
	}
	
	// === helper methods ===
	private String httpRequestHelper(String url) throws UnsupportedOperationException, IOException {
		HttpUriRequest request = new HttpGet(url);
		HttpResponse httpResponse = null;

		try {
			httpResponse = HttpClientBuilder.create().build().execute(request);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new String(httpResponse.getEntity().getContent().readAllBytes());
	}

	private HttpResponse httpResponseHelper(HttpUriRequest request) {
		HttpResponse httpResponse = null;

		try {
			httpResponse = HttpClientBuilder.create().build().execute(request);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return httpResponse;
	}

	@AfterAll
	public static void shutDown() throws SQLException {
		databaseController.clearTables();
		testServer.stopWebServer();
	}
}
