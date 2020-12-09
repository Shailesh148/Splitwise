package com.splitwise;


import com.splitwise.controller.SplitwiseController;
import com.splitwise.model.UserInfo;
import com.splitwise.model.Users;
import com.splitwise.service.SplitwiseService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.security.cert.CertPathBuilder;
import java.util.*;

@RunWith(SpringRunner.class)
public class SplitwiseControllerTest {
    private MockMvc mockMvc;
    private static final String BASE_URL = "http://localhost:8080/";

    @Mock
    private SplitwiseService splitwiseService;

    @InjectMocks
    private SplitwiseController splitwiseController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(splitwiseController).build();
    }

    @Test
    public void testGetUserDetails() throws Exception {

        UserInfo userInfo = new UserInfo();
        List<Users> allusersInvolved = new ArrayList<>();
        Users user1 = new Users(123456788,22.0);
        Users user2 = new Users(132512311, 42.6);
        allusersInvolved.add(user1);
        allusersInvolved.add(user2);
        userInfo.setUsers(allusersInvolved);
        userInfo.setNetBalance(64.6);


        Mockito.when(splitwiseService.getUserData(123456789)).thenReturn(userInfo);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(BASE_URL+"/splitwise/getUser?user_id=123456789").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assert.assertEquals(202, response.getStatus());
    }

}
