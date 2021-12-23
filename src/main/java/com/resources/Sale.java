package com.resources;

import java.util.Date;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "sales")
public class Sale implements Resource{
	//constants
	public static final String SALE_ID = "saleId";
	public static final String SALE_DATE = "saleDate";
	public static final String SALE_PROPERTY = "saleProperty";
	
	@DatabaseField(generatedId = true, columnName = SALE_ID)
	private int saleId;
	
	@DatabaseField(canBeNull = false, dataType = DataType.DATE)
	private Date saleDate;
	
	@DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true, columnName = SALE_PROPERTY)
	private Property saleProperty;
	
	public Sale() {
		// no-arg constructor
	}

	public int getSaleId() {
		return saleId;
	}

	public void setSaleId(int saleId) {
		this.saleId = saleId;
	}

	public Date getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}

	public Property getSaleProperty() {
		return saleProperty;
	}

	public void setSaleProperty(Property saleProperty) {
		this.saleProperty = saleProperty;
	}

	@Override
	public String toString() {
		return "Sale [saleId=" + getSaleId() + ", saleDate=" + getSaleDate() + ", saleProperty=" + getSaleProperty() + "]";
	}

	@Override
	public Class<? extends Resource> getResouce() {
		return this.getClass();
	}
}
