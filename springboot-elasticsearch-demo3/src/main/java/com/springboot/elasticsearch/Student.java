package com.springboot.elasticsearch;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonInclude;
//
//@JsonIgnoreProperties(ignoreUnknown = true)
//Annotation that can be used to either suppress serialization of properties (during serialization), 
//or ignore processing of JSON properties read (during deserialization).
/*@JsonIgnoreProperties(ignoreUnknown=true) is applicable at deserialization of JSON to Java object (POJO) only. 
 * If your POJO does not contain certain properties which JSON does contain,
 * they are ignored and no error is thrown.
 */

//@JsonInclude(JsonInclude.Include.NON_NULL)		//ignore all null fields
public class Student {
	private String id;
	private String name;

	public Student() {
	}

	public Student(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
