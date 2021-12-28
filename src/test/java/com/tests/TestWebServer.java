package com.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import com.backend.WebServer;
import com.google.gson.Gson;
import com.resources.Agent;

public class TestWebServer {

	private static WebServer testServer;

	@BeforeAll
	public static void startTestConnection() {
		testServer = new WebServer();
		testServer.startWebServer();
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
	void testGetAgentRequest() throws UnsupportedOperationException, IOException {
		HttpUriRequest request = new HttpGet("http://localhost:4567/openproperty/status");
		HttpResponse httpResponse = httpResponseHelper(request);
		assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
	}
	
	@Test
	@Order(3)
	void testGetAgentRequestById() throws UnsupportedOperationException, IOException {
		String response = httpRequestHelper("http://localhost:4567/openproperty/agents/?id=1");
		Gson gson = new Gson();
		Agent[] agent = gson.fromJson(response, Agent[].class);
		assertEquals(1, agent[0].getAgentId());
	}
	
	@Test
	@Order(4)
	void testGetAgentRequestByName() throws UnsupportedOperationException, IOException {
		String response = httpRequestHelper("http://localhost:4567/openproperty/agents/?name=TestAgent");
		Gson gson = new Gson();
		Agent[] agent = gson.fromJson(response, Agent[].class);
		assertEquals("TestAgent", agent[0].getAgentName());
	}
	
	@Test
	@Order(5)
	void testGetAgentRequestByCommission() throws UnsupportedOperationException, IOException {
		String response = httpRequestHelper("http://localhost:4567/openproperty/agents/?commission=0.5");
		Gson gson = new Gson();
		Agent[] agent = gson.fromJson(response, Agent[].class);
		assertEquals(0.5f, agent[0].getAgentCommission());
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

	private HttpPost httpPostHelper(String url, String data) throws UnsupportedEncodingException {
		HttpPost request = new HttpPost(url);
		request.setEntity(new StringEntity(data));
		return request;
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

	private HttpResponse httpResponseHelper(HttpPost request) {
		HttpResponse httpResponse = null;

		try {
			httpResponse = HttpClientBuilder.create().build().execute(request);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return httpResponse;
	}

	@AfterAll
	public static void shutDown() {
		testServer.stopWebServer();
	}
}
