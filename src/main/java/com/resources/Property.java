package com.resources;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "properties")
public class Property {
	//constants
	public static final String PROPERTY_ID = "propertyId";
	public static final String PROPERTY_TYPE = "propertyType";
	public static final String PROPERTY_ADDRESS = "propertyAddress";
	public static final String PROPERTY_VALUE = "propertyValue";
	public static final String PROPERTY_AGENT = "propertyAgent";

	@DatabaseField(generatedId = true, columnName = PROPERTY_ID)
	private int propertyId;
	
	@DatabaseField(canBeNull = false, columnName = PROPERTY_TYPE)
	private String propertyType;
	
	@DatabaseField(canBeNull = false, columnName = PROPERTY_ADDRESS)
	private String propertyAddress;
	
	@DatabaseField(canBeNull = false, columnName = PROPERTY_VALUE)
	private float propertyValue;
	
	@DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true, columnName = PROPERTY_AGENT)
	private Agent propertyAgent;
	
	public Property() {
		// no-arg constructor
	}

	public int getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public String getPropertyAddress() {
		return propertyAddress;
	}

	public void setPropertyAddress(String propertyAddress) {
		this.propertyAddress = propertyAddress;
	}

	public float getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(float propertyValue) {
		this.propertyValue = propertyValue;
	}

	public Agent getPropertyAgent() {
		return propertyAgent;
	}

	public void setPropertyAgent(Agent propertyAgent) {
		this.propertyAgent = propertyAgent;
	}

	@Override
	public String toString() {
		return "Property [propertyId=" + getPropertyId() + ", propertyType=" + getPropertyType() + ", propertyAddress="
				+ getPropertyAddress() + ", propertyValue=" + getPropertyValue() + ", propertyAgent=" + getPropertyAgent() + "]";
	}
}
