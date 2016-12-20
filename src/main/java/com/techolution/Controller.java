package com.techolution;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * 
 * @author saikiran
 *
 */
@RestController
public class Controller {

	@Autowired
	RestService restTemplateService;
	
	/**
	 * rest api ppt request to add data
	 * @param key
	 * @param value
	 * @return
	 */
	@RequestMapping(value = "/TechoHash/{key}", method = RequestMethod.PUT)
	public ResponseEntity<String> put( @PathVariable("key") String key,
			@RequestBody String value) {
		restTemplateService.put( key, value);
		return new ResponseEntity<>("{}", HttpStatus.CREATED);
	}

	@RequestMapping(value = "/TechoHash/{key}", method = RequestMethod.GET)
	public ResponseEntity<Object> get( @PathVariable("key") String key) {
		Object result = restTemplateService.get( key);
		if (result != null) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>("{}", HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/TechoHash/{key}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(
			@PathVariable("key") String key) {
		Object result = restTemplateService.delete( key);
		return new ResponseEntity<>("{}", HttpStatus.GONE);
	}
}
