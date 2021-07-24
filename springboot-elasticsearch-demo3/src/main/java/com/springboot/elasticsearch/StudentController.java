package com.springboot.elasticsearch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
//import java.util.HashMap;
import java.util.List;
//import java.util.Map;
import java.util.Map;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.xcontent.XContentBuilder;
//import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class StudentController {

	@Autowired
	private RestHighLevelClient client;
	
//	@Autowired
//	private DefineMapping defineMapping;
	
	//Create light index with mapping from other class
//	@PostMapping("/create/{indexName}")
//	public String createIndexLight(@PathVariable String indexName) throws IOException {
//		CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
//		//settings
//		createIndexRequest.settings(Settings.builder()
//				.put("index.number_of_shards", 1)
//				.put("index.number_of_replicas", 1));
//		//mapping
//		createIndexRequest.mapping(defineMapping.metadataToElasticIndex());		
//		//creation
//		CreateIndexResponse createIndexResponse = client.indices().create(createIndexRequest, RequestOptions.DEFAULT);		
//		return "response id: " + createIndexResponse.index();
//	}
	
	//Create an index
	@PostMapping("/index")
	public String createIndex() throws IOException {
		CreateIndexRequest createIndexRequest = new CreateIndexRequest("student");
		//provide settings
		createIndexRequest.settings(Settings.builder()
				.put("index.number_of_shards", 1)
				.put("index.number_of_replicas", 1));
		//provide index mapping in form of string
		createIndexRequest.mapping(
				"{\n"+
				"	\"properties\":	{\n"+
				"		\"id\":  {\n"+
				"			\"type\":  \"text\"\n"+
				"		},\n" +
				"		\"name\":  {\n"+
				"			\"type\":  \"text\"\n"+
				"		}\n" +
				"	}\n"+
				"}",
				XContentType.JSON);
		
		//provide mapping as a MAP
		Map<String, Object> id = new HashMap<>();
		id.put("type", "text");
		
		Map<String, Object> name = new HashMap<>();
		name.put("type", "text");
		
		Map<String, Object> properties = new HashMap<>();
		properties.put("id", id);
		properties.put("name", name);
		
		Map<String, Object> mapping = new HashMap<>();
		mapping.put("properties", properties);
		
		System.out.println(mapping.toString());
		
		createIndexRequest.mapping(mapping);
		
		
//		//mappings as XContentBuilder object
//		XContentBuilder builder = XContentFactory.jsonBuilder();
//		builder.startObject();
//		{
//			builder.startObject("properties");
//			{
//				builder.startObject("id");
//				{
//					builder.field("type", "text");
//				}
//				builder.endObject();
//				builder.startObject("name");
//				{
//					builder.field("type", "text");
//				}
//				builder.endObject();
//			}
//			builder.endObject();
//		}
//		builder.endObject();
//		request.mapping(builder);
		
		CreateIndexResponse createIndexResponse = client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
		
//		GetIndexRequest getIndexRequest = new GetIndexRequest("users");
//		//returns true if index exists
//		boolean exists = client.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
//		if (!exists) {
//			CreateIndexResponse indexResponse = client.indices().create(request, RequestOptions.DEFAULT);
//			return "response id: " + indexResponse.index();
//		}
		
		return "response id: " + createIndexResponse.index();
	}
		
	//Post a student in the index
	@PostMapping("/student")
	public String saveStudent(@RequestBody Student student) throws IOException {
		IndexRequest request = new IndexRequest("student");
		request.id(student.getId());
		request.source(new ObjectMapper().writeValueAsString(student), XContentType.JSON);
		//use requestOptions as false if nothing is to be customized
		IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
		return indexResponse.getResult().name();
	}
	
	//Get all students
	@GetMapping("/students")
	public List<Student> getStudents() throws IOException{
		List<Student> students = new ArrayList<>();
		
		//Making a match all query to get all documents in index		
		SearchRequest searchRequest = new SearchRequest("student");
		//SearchSourceBuilder contains the query which we want to execute
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchAllQuery());
		searchRequest.source(searchSourceBuilder);
		//searchSourceBuilder.size(5);
		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		for(SearchHit searchHit: searchResponse.getHits().getHits()) {
			Student student = new ObjectMapper().readValue(searchHit.getSourceAsString(), Student.class);
			students.add(student);
		}
		return students;
	}
	
	//Get student with given id
	@GetMapping("/student/{id}")
	public Student getStudent(@PathVariable String id) throws IOException {
		GetRequest getRequest = new GetRequest("student", id);
		//SYNCHRONOUS EXECUTION
		//When executing a GetRequest in the following manner, the client waits for the 
		//GetResponse to be returned before continuing with code execution:
		GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
		Student student = new ObjectMapper().readValue(getResponse.getSourceAsString(), Student.class);
		return student;
	}
	
//	//Not Working
//	//Update student or post if it does not exists 
//	@PutMapping("/student/{id}")
//	public String upsertStudent(@PathVariable String id, @RequestBody Student student) {
//		UpdateRequest updateRequest = new UpdateRequest("student", id);
//		try {
//			updateRequest.upsert(new ObjectMapper().writeValueAsString(student), XContentType.JSON);
//			return "Upserted Successfully";
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}
//		return "Upsertion Unsuccessful";
//	}
	
	@PutMapping("/student/{id}")
	public String updateStudent(@PathVariable String id, @RequestBody Student student) throws IOException {
		UpdateRequest updateRequest = new UpdateRequest("student", id);
		updateRequest.doc(new ObjectMapper().writeValueAsString(student), XContentType.JSON);
		UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
		return updateResponse.status().name();		
	}
	
	// Delete a single student
	@DeleteMapping("/student/{id}")
	public String deleteStudent(@PathVariable String id) throws IOException {
		DeleteRequest deleteRequest = new DeleteRequest("student", id);
		DeleteResponse deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);
		return deleteResponse.getResult().name();

	}
	
	//Delete the index
	@DeleteMapping("/delete/{indexName}")
	public String deleteStudentIndex(@PathVariable String indexName) throws IOException {
		DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(indexName);
		client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
		return "Index Found and Deleted";
	}
}
