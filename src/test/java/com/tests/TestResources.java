package com.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import com.resources.Agent;
import com.resources.Property;
import com.resources.Sale;

public class TestResources {
	
	@Test
	@Order(1)
	void testAgent() {
		//create Agent
		Agent agent = new Agent();
		agent.setAgentId(1);
		agent.setAgentName("TestAgent1");
		agent.setAgentCommission(0.5f);
		
		//test
		String expected = "Agent [agentId=1, agentName=TestAgent1, agentCommission=0.5]";
		String actual = agent.toString();
		assertEquals(expected, actual);
	}
	
	@Test
	@Order(2)
	void testProperty() {
		//create Agent
		Agent agent = new Agent();
		agent.setAgentId(2);
		agent.setAgentName("TestAgent2");
		agent.setAgentCommission(0.6f);
		
		//create Property
		Property property = new Property();
		property.setPropertyId(1);
		property.setPropertyType("Test Lab");
		property.setPropertyAddress("001 Test");
		property.setPropertyValue(100000.00f);
		property.setPropertyAgent(agent);
		
		//test
		String expected = "Property [propertyId=1, propertyType=Test Lab, propertyAddress=001 Test, "
				+ "propertyValue=100000.0, propertyAgent=Agent [agentId=2, agentName=TestAgent2, agentCommission=0.6]]";
		String actual = property.toString();
		assertEquals(expected, actual);
	}
	
	@Test
	@Order(3)
	void testSale() {
		//create Agent
		Agent agent = new Agent();
		agent.setAgentId(3);
		agent.setAgentName("TestAgent3");
		agent.setAgentCommission(0.7f);
		
		//create Property
		Property property = new Property();
		property.setPropertyId(2);
		property.setPropertyType("Test Lab");
		property.setPropertyAddress("002 Test");
		property.setPropertyValue(80000.00f);
		property.setPropertyAgent(agent);
		
		//create Sale
		Date date = new Date();
		Sale sale = new Sale();
		sale.setSaleId(1);
		sale.setSaleDate(date);
		sale.setSaleProperty(property);
		
		//test
		String expected = "Sale [saleId=1, saleDate=" + date + ", saleProperty=Property [propertyId=2, "
				+ "propertyType=Test Lab, propertyAddress=002 Test, propertyValue=80000.0, propertyAgent=Agent "
				+ "[agentId=3, agentName=TestAgent3, agentCommission=0.7]]]";
		String actual = sale.toString();
		assertEquals(expected, actual);
	}

}
