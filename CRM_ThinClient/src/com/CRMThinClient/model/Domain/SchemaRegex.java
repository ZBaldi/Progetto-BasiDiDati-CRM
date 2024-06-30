package com.CRMThinClient.model.Domain;

public enum SchemaRegex {
	EMAIL("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"),
	TELEFONO("^\\+\\d{1,3}\\s\\d{10}$"),
	CF("^[A-Za-z]{6}\\d{2}[A-Za-z]\\d{2}[A-Za-z]\\d{3}[A-Za-z]$"),
	CODICEOFFERTA("^[A-Za-z0-9]{6}"),
	CAP("^[0-9]{5}"),
	PROVINCIA("^[A-Za-z]{2}$");
	
	private final String schema;
	
	private SchemaRegex(String schema) {
		this.schema=schema;
	}
	
	public String getSchema() {
		return schema;
	}
}