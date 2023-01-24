package com.jdbc.web;

import com.jdbc.dao.UserDAOImpl;
import com.jdbc.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustControllerTest {
    @InjectMocks
    private CustController custController;

    @Mock
    private UserDAOImpl userDAOImpl;

    @Test
    void testHello() {
        ModelAndView modelAndView = new ModelAndView("View Name");
        ModelAndView actualHelloResult = custController.hello(modelAndView);
        assertSame(modelAndView, actualHelloResult);
        assertTrue(actualHelloResult.isReference());
        assertSame(actualHelloResult.getModel(), actualHelloResult.getModelMap());
    }


    @Test
    void testHello3() {
        ModelAndView modelAndView = mock(ModelAndView.class);
        when(modelAndView.addObject(any())).thenReturn(new ModelAndView("View Name"));
        doNothing().when(modelAndView).setViewName(any());
        custController.hello(modelAndView);
        verify(modelAndView).addObject(any());
        verify(modelAndView).setViewName(any());
    }


    @Test
    void testAddUser() {
        doNothing().when(userDAOImpl).save(any());
        ModelAndView modelAndView = new ModelAndView("View Name");
        ModelAndView actualAddUserResult = custController.addUser("Jane", "Doe", modelAndView);
        assertSame(modelAndView, actualAddUserResult);
        assertTrue(actualAddUserResult.isReference());
        Map<String, Object> model = actualAddUserResult.getModel();
        assertSame(model, actualAddUserResult.getModelMap());
        assertEquals("Jane", ((User) model.get("user")).getFirstName());
        assertEquals("Doe", ((User) model.get("user")).getLastName());
        verify(userDAOImpl).save((User) any());
    }


    @Test
    void testGetAllUsers() {
        when(userDAOImpl.getAll()).thenReturn(new ArrayList<>());
        ModelAndView modelAndView = new ModelAndView("View Name");
        ModelAndView actualAllUsers = custController.getAllUsers(modelAndView);
        assertSame(modelAndView, actualAllUsers);
        assertTrue(actualAllUsers.isReference());
        assertSame(actualAllUsers.getModel(), actualAllUsers.getModelMap());
        verify(userDAOImpl).getAll();
    }
}

