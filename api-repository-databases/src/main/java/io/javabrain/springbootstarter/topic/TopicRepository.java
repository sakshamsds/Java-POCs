package io.javabrain.springbootstarter.topic;

import org.springframework.data.repository.CrudRepository;

//Here we use embedded database i.e., using apache derby
//Extending CrudRepository to use its methods in topicService
public interface TopicRepository extends CrudRepository<Topic, String> {
	
}
