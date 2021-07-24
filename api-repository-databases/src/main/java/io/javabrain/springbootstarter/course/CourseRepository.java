package io.javabrain.springbootstarter.course;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

//Extending CrudRepository to use its methods in courseService
public interface CourseRepository extends CrudRepository<Course, String> {
	
	/*Just declare the method with the findByPROPERTY name format and Spring Data JPA will implement the
	 *method for you*/
	
	//custom find method should start by find
	//public List<Course> findByName(String name);
	//public List<Course> findByDescription(String description);
	public List<Course> findByTopicId(String topicId);
}
