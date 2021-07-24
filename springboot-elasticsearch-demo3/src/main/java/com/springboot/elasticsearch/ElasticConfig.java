package com.springboot.elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticConfig {
	
	@Value("${elasticsearch.host:localhost}")
	public String host;
	
	@Value("${elasticsearch.port:9200}")
	public int port;

	public ElasticConfig() {
	}

	// Indicates that a method produces a bean to be managed by the Spring container
	@Bean
	public RestHighLevelClient client() {
		System.out.println("host " + host + " port " + port);
		return new RestHighLevelClient(RestClient.builder(new HttpHost(host, port, "http")));
	}
}
