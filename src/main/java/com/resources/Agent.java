package com.resources;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "agents")
public class Agent {
	//constants
	public static final String AGENT_ID = "agentId";
	public static final String AGENT_NAME = "agentName";
	public static final String AGENT_COMMISSION = "agentCommission";
	
	@DatabaseField(generatedId = true, columnName = AGENT_ID)
	private int agentId;
	
	@DatabaseField(columnName = AGENT_NAME)
	private String agentName;
	
	@DatabaseField(columnName = AGENT_COMMISSION)
	private float agentCommission;

	public Agent() {
		// no-arg constructor
	}

	public int getAgentId() {
		return agentId;
	}

	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public float getAgentCommission() {
		return agentCommission;
	}

	public void setAgentCommission(float agentCommission) {
		this.agentCommission = agentCommission;
	}

	@Override
	public String toString() {
		return "Agent [agentId=" + agentId + ", agentName=" + agentName + ", agentCommission=" + agentCommission + "]";
	}
}
