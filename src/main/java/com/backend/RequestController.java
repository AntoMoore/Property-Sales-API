package com.backend;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.data.DatabaseController;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.resources.Agent;
import com.resources.Property;
import com.resources.Resource;
import com.resources.Sale;

import spark.Request;
import spark.Response;

public class RequestController {

	public List<Resource> getAgentRequest(DatabaseController db, Request req, Response res) throws SQLException {
		List<Resource> list = new ArrayList<>();
		int paramaters = req.queryMap().toMap().size();

		if(paramaters == 0) {
			// no parameters (return all agents)
			List<Agent> agents = db.getAgentDao().queryForAll();
			for(Agent a : agents)
				list.add(a);
		}
		else if(paramaters == 1) {
			//Query Builder
			QueryBuilder<Agent, String> agentQuery = db.getAgentDao().queryBuilder();
			Where<Agent, String> where = agentQuery.where();

			// query parameters
			if(req.queryParams().toString().contains("id"))
				where.eq(Agent.AGENT_ID, req.queryMap().get("id").value());
			else if(req.queryParams().toString().contains("name"))
				where.eq(Agent.AGENT_NAME, req.queryMap().get("name").value());
			else if(req.queryParams().toString().contains("commission"))
				where.eq(Agent.AGENT_COMMISSION, req.queryMap().get("commission").value());

			//prepared statement
			PreparedQuery<Agent> prepQuery = agentQuery.prepare();
			List<Agent> agents = db.getAgentDao().query(prepQuery);
			for(Agent a : agents)
				list.add(a);
		}

		return list;	
	}

	public List<Resource> getPropertyRequest(DatabaseController db, Request req, Response res) throws SQLException{
		List<Resource> list = new ArrayList<>();
		int paramaters = req.queryMap().toMap().size();

		if(paramaters == 0) {
			// no parameters (return all properties)
			List<Property> properties = db.getPropertyDao().queryForAll();
			for(Property p : properties)
				list.add(p);
		}
		else if(paramaters == 1) {
			//Query Builder
			QueryBuilder<Property, String> propertyQuery = db.getPropertyDao().queryBuilder();
			Where<Property, String> where = propertyQuery.where();

			// query parameters
			if(req.queryParams().toString().contains("id"))
				where.eq(Property.PROPERTY_ID, req.queryMap().get("id").value());
			else if(req.queryParams().toString().contains("name"))
				where.eq(Property.PROPERTY_TYPE, req.queryMap().get("type").value());
			else if(req.queryParams().toString().contains("value"))
				where.eq(Property.PROPERTY_VALUE, req.queryMap().get("value").value());

			//prepared statement
			PreparedQuery<Property> prepQuery = propertyQuery.prepare();
			List<Property> properties = db.getPropertyDao().query(prepQuery);
			for(Property p : properties)
				list.add(p);
		}

		return list;
	}

	public List<Resource> getSaleRequest(DatabaseController db, Request req, Response res) throws SQLException{
		List<Resource> list = new ArrayList<>();
		int paramaters = req.queryMap().toMap().size();

		if(paramaters == 0) {
			// no parameters (return all properties)
			List<Sale> sales = db.getSaleDao().queryForAll();
			for(Sale s : sales)
				list.add(s);
		}
		else if(paramaters == 1) {
			//Query Builder
			QueryBuilder<Sale, String> propertyQuery = db.getSaleDao().queryBuilder();
			Where<Sale, String> where = propertyQuery.where();

			// query parameters
			if(req.queryParams().toString().contains("id"))
				where.eq(Sale.SALE_ID, req.queryMap().get("id").value());
			else if(req.queryParams().toString().contains("date")) {
				// Need to add date checking
				// where.eq(Sale.SALE_DATE, req.queryMap().get("date").value());
			}

			//prepared statement
			PreparedQuery<Sale> prepQuery = propertyQuery.prepare();
			List<Sale> sales = db.getSaleDao().query(prepQuery);
			for(Sale s : sales)
				list.add(s);
		}

		return list;
	}

}
