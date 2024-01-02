package com.demoJunit.demo;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@ExtendWith(SpringExtension.class)
public class BookControllerTest {
	private MockMvc mvc;
	
	ObjectMapper op=new ObjectMapper();
	ObjectWriter writer=op.writer();
	
	@Mock
	private BookRepo repo;
	
	@InjectMocks
	private BookController controller;
	
	Book r1=new Book(1,"java",1000);
	Book r2=new Book(2,"spring",2000);
	Book r3=new Book(3,"MySQL",3000);
	Book[] r=new Book[] {r1,r2,r3};
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		this.mvc= MockMvcBuilders.standaloneSetup(controller).build();
		
	}
	
	@Test
	public void getAllSuccess() throws Exception {
		List<Book> records = new ArrayList<>();
		records.add(r1);
		records.add(r2);
		records.add(r3);
		Mockito.when(repo.findAll()).thenReturn(records);
		mvc.perform(MockMvcRequestBuilders.get("/book").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(3))
		.andExpect(MockMvcResultMatchers.jsonPath("$[2].name").value("MySQL"));
	}


}
