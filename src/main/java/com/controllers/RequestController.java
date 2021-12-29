package com.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.resources.Agent;
import com.resources.Property;
import com.resources.Resource;
import com.resources.ResourceFactory;
import com.resources.Sale;

import spark.Request;

public class RequestController {
	
	public static final String CONNECTION_TYPE = "PRODUCTION"; // (production/test)
	private DatabaseController databaseController = DatabaseController.getInstance();
	private ResourceFactory resourceFactory = new ResourceFactory();
	
	public void connectDatabase() {
		databaseController.connect(CONNECTION_TYPE);
	}

	public List<Resource> getAgentRequest(Request req) throws SQLException {
		List<Resource> list = new ArrayList<>();
		int paramaters = req.queryMap().toMap().size();

		if(paramaters == 0) {
			// no parameters (return all agents)
			List<Agent> agents = databaseController.getAgentDao().queryForAll();
			for(Agent a : agents)
				list.add(a);
		}
		else if(paramaters == 1) {
			//Query Builder
			QueryBuilder<Agent, String> agentQuery = databaseController.getAgentDao().queryBuilder();
			Where<Agent, String> where = agentQuery.where();

			// query parameters
			if(req.queryParams().toString().contains("id"))
				where.eq(Agent.AGENT_ID, req.queryMap().get("id").value());
			else if(req.queryParams().toString().contains("name"))
				where.eq(Agent.AGENT_NAME, req.queryMap().get("name").value());
			else if(req.queryParams().toString().contains("commission"))
				where.le(Agent.AGENT_COMMISSION, req.queryMap().get("commission").value());

			//prepared statement
			PreparedQuery<Agent> prepQuery = agentQuery.prepare();
			List<Agent> agents = databaseController.getAgentDao().query(prepQuery);
			for(Agent a : agents)
				list.add(a);
		}

		return list;	
	}

	public List<Resource> getPropertyRequest(Request req) throws SQLException{
		List<Resource> list = new ArrayList<>();
		int paramaters = req.queryMap().toMap().size();

		if(paramaters == 0) {
			// no parameters (return all properties)
			List<Property> properties = databaseController.getPropertyDao().queryForAll();
			for(Property p : properties)
				list.add(p);
		}
		else if(paramaters == 1) {
			//Query Builder
			QueryBuilder<Property, String> propertyQuery = databaseController.getPropertyDao().queryBuilder();
			Where<Property, String> where = propertyQuery.where();

			// query parameters
			if(req.queryParams().toString().contains("id"))
				where.eq(Property.PROPERTY_ID, req.queryMap().get("id").value());
			else if(req.queryParams().toString().contains("type"))
				where.eq(Property.PROPERTY_TYPE, req.queryMap().get("type").value());
			else if(req.queryParams().toString().contains("value"))
				where.le(Property.PROPERTY_VALUE, req.queryMap().get("value").value());

			//prepared statement
			PreparedQuery<Property> prepQuery = propertyQuery.prepare();
			List<Property> properties = databaseController.getPropertyDao().query(prepQuery);
			for(Property p : properties)
				list.add(p);
		}

		return list;
	}

	public List<Resource> getSaleRequest(Request req) throws SQLException{
		List<Resource> list = new ArrayList<>();
		int paramaters = req.queryMap().toMap().size();

		if(paramaters == 0) {
			// no parameters (return all properties)
			List<Sale> sales = databaseController.getSaleDao().queryForAll();
			for(Sale s : sales)
				list.add(s);
		}
		else if(paramaters == 1) {
			//Query Builder
			QueryBuilder<Sale, String> saleQuery = databaseController.getSaleDao().queryBuilder();
			Where<Sale, String> where = saleQuery.where();

			// query parameters
			if(req.queryParams().toString().contains("id"))
				where.eq(Sale.SALE_ID, req.queryMap().get("id").value());
			else if(req.queryParams().toString().contains("date")) {
				// get current time/date
				Date date = new Date();
				Calendar calender = Calendar.getInstance();
				calender.setTime(date);
				
				//check for: day, week, month or year
				String paramater = req.queryMap().get("date").value().toUpperCase();
				if(paramater.equals("DAY"))
					calender.add(Calendar.DAY_OF_YEAR, -1);
				else if(paramater.equals("WEEK"))
					calender.add(Calendar.WEEK_OF_YEAR, -1);
				else if(paramater.equals("MONTH"))
					calender.add(Calendar.MONTH, -1);
				else if(paramater.equals("YEAR"))
					calender.add(Calendar.YEAR, -1);
				
				// set target date and query
				date = calender.getTime();
				where.between(Sale.SALE_DATE, date, new Date());
			}
			else if(req.queryParams().toString().contains("agent")) {
				List<Sale> sales = databaseController.getSaleDao().queryForAll();
				int agentId = Integer.parseInt(req.queryMap().get("agent").value());
				for(Sale s : sales) {
					if(s.getSaleProperty().getPropertyAgent().getAgentId() == agentId) {
						list.add(s);
					}
				}
				return list;
			}

			//prepared statement
			PreparedQuery<Sale> prepQuery = saleQuery.prepare();
			List<Sale> sales = databaseController.getSaleDao().query(prepQuery);
			for(Sale s : sales)
				list.add(s);
		}

		return list;
	}
	
	public void postAgentRequest(Request req) throws SQLException {
		// create new agent
		Agent agent = (Agent)resourceFactory.getResource("agent");
		agent.setAgentName(req.queryParams("name"));
		agent.setAgentCommission(Float.valueOf(req.queryParams("commission")).floatValue());
		
		// add to database
		databaseController.getAgentDao().create(agent);
	}
	
	public void postPropertyRequest(Request req) throws SQLException {
		// create new property
		Property property = (Property)resourceFactory.getResource("property");
		property.setPropertyType(req.queryParams("type"));
		property.setPropertyAddress(req.queryParams("address"));
		property.setPropertyValue(Float.valueOf(req.queryParams("value")).floatValue());
		property.setPropertyAgent(databaseController.getAgentDao().queryForId(req.queryParams("agentId")));
		
		// add to database
		databaseController.getPropertyDao().create(property);
	}
	
	public void postSaleRequest(Request req) throws SQLException {
		// create new sale
		Sale sale = (Sale)resourceFactory.getResource("sale");
		sale.setSaleDate(new Date());
		sale.setSaleProperty(databaseController.getPropertyDao().queryForId(req.queryParams("propertyId")));
		
		// add to database
		databaseController.getSaleDao().create(sale);
	}
	
	public void deleteAgentRequest(Request req) throws SQLException {
		databaseController.getAgentDao().deleteById(req.queryParams("id"));
	}
	
	public void deletePropertyRequest(Request req) throws SQLException {
		databaseController.getPropertyDao().deleteById(req.queryParams("id"));
	}
	
	public void deleteSaleRequest(Request req) throws SQLException {
		databaseController.getSaleDao().deleteById(req.queryParams("id"));
	}
}
