package com.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import com.data.DatabaseController;
import com.resources.Agent;
import com.resources.Property;
import com.resources.ResourceFactory;
import com.resources.Sale;

public class TestDatabase {
	
	private static final String CONNECTION_TYPE = "TEST"; // (production/test)
	private static DatabaseController databaseController = DatabaseController.getInstance();
	private static ResourceFactory resourceFactory = new ResourceFactory();

	@BeforeAll
	public static void startTestConnection() {
		databaseController.connect(CONNECTION_TYPE);
	}

	@Test
	@Order(1)
	void testCreateAgent() throws SQLException {
		//create Agent
		Agent agent = (Agent)resourceFactory.getResource("agent");
		agent.setAgentName("TestAgent1");
		agent.setAgentCommission(0.5f);
		databaseController.getAgentDao().create(agent);

		//get agent info from DB
		String agentId = String.valueOf(agent.getAgentId());
		Agent queryResult = databaseController.getAgentDao().queryForId(agentId);
		
		//test
		String expected = "Agent [agentId=" + agentId + ", agentName=TestAgent1, agentCommission=0.5]";
		String actual = queryResult.toString();
		assertEquals(expected, actual);
	}

	@Test
	@Order(2)
	void testCreateProperty() throws SQLException {
		//create Agent
		Agent agent = (Agent)resourceFactory.getResource("agent");
		agent.setAgentName("TestAgent2");
		agent.setAgentCommission(0.6f);
		databaseController.getAgentDao().create(agent);

		//create Property
		Property property = (Property)resourceFactory.getResource("property");
		property.setPropertyType("Test Lab");
		property.setPropertyAddress("002 Test");
		property.setPropertyValue(100000.00f);
		property.setPropertyAgent(agent);
		databaseController.getPropertyDao().create(property);

		//get property info from DB
		String agentId = String.valueOf(agent.getAgentId());
		String propertyId = String.valueOf(property.getPropertyId());
		Property queryResult = databaseController.getPropertyDao().queryForId(propertyId);
		
		//test
		String expected = "Property [propertyId=" + propertyId + ", propertyType=Test Lab, propertyAddress=002 Test, "
				+ "propertyValue=100000.0, propertyAgent=Agent [agentId=" + agentId + ", agentName=TestAgent2,"
				+ " agentCommission=0.6]]";
		String actual = queryResult.toString();
		assertEquals(expected, actual);
	}

	@Test
	void testCreateSale() throws SQLException {
		//create Agent
		Agent agent = (Agent)resourceFactory.getResource("agent");
		agent.setAgentName("TestAgent3");
		agent.setAgentCommission(0.7f);
		databaseController.getAgentDao().create(agent);

		//create Property
		Property property = (Property)resourceFactory.getResource("property");
		property.setPropertyType("Test Lab");
		property.setPropertyAddress("003 Test");
		property.setPropertyValue(80000.00f);
		property.setPropertyAgent(agent);
		databaseController.getPropertyDao().create(property);
		
		//create sale
		Date date = new Date();
		Sale sale = (Sale)resourceFactory.getResource("sale");
		sale.setSaleDate(date);
		sale.setSaleProperty(property);
		databaseController.getSaleDao().create(sale);
		
		//get sale info from DB
		String agentId = String.valueOf(agent.getAgentId());
		String propertyId = String.valueOf(property.getPropertyId());
		String saleId = String.valueOf(sale.getSaleId());
		Sale queryResult = databaseController.getSaleDao().queryForId(saleId);
		
		//test
		String expected = "Sale [saleId=" + saleId + ", saleDate=" + date + ", saleProperty=Property [propertyId=" + propertyId + ", "
				+ "propertyType=Test Lab, propertyAddress=003 Test, propertyValue=80000.0, propertyAgent=Agent [agentId=" + agentId + ", "
				+ "agentName=TestAgent3, agentCommission=0.7]]]";
		String actual = queryResult.toString();
		assertEquals(expected, actual);

	}
}
