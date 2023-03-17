package com.example.demooo.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "student")
public class Student {

	@DynamoDBHashKey(attributeName = "stdId")
	private String stdId;

	@DynamoDBAttribute(attributeName = "name")
	private String name;

	@DynamoDBAttribute(attributeName = "email")
	private String email;

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStdId() {
		return stdId;
	}

	public void setStdId(String stdId) {
		this.stdId = stdId;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}
}
