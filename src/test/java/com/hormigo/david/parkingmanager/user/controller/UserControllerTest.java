package com.hormigo.david.parkingmanager.user.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.MediaType;
import com.hormigo.david.parkingmanager.core.exceptions.UserExistsException;
import com.hormigo.david.parkingmanager.user.domain.Role;
import com.hormigo.david.parkingmanager.user.domain.User;
import com.hormigo.david.parkingmanager.user.domain.UserDao;
import com.hormigo.david.parkingmanager.user.service.UserService;
import com.hormigo.david.parkingmanager.user.service.UserServiceImpl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Test
    public void testPositive() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        UserDao dao = new UserDao("aprobamos@ahora.com", "Alejandro", "Moreno", "Garrido", Role.PROFESSOR);
        String json = mapper.writeValueAsString(dao);
        when(this.userService.register(any(UserDao.class))).thenReturn(new User("aprobamos@ahora.com", "Alejandro", "Moreno", "Garrido", Role.PROFESSOR));
        this.mockMvc.perform(post("/api/users")
                    .contentType("application/json").content(json))
                    .andDo(print())
                    .andExpect(status().isCreated());
    }

    @Test
    public void testAllUserRead() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        User user = new User("aprobamos@ahora.com", "Alejandro", "Moreno", "Garrido", Role.PROFESSOR);
        ArrayList<User> usuariso = new ArrayList<>();
        usuariso.add(user);
        String json = mapper.writeValueAsString(usuariso);
        json = "{ \"_embedded\": {\"userList\":" + json + "}}";
        when(userService.getAll()).thenReturn(usuariso);
        this.mockMvc.perform(get("/api/users"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json(json));
    }



    @Test
    public void testDeleteUser() throws Exception{
        User user = new User("aprobamos@ahora.com", "Alejandro", "Moreno", "Garrido", Role.PROFESSOR);
        when(userService.getUser(1)).thenReturn(Optional.of(user));
        this.mockMvc.perform(delete("/api/users/1"))
                    .andDo(print())
                    .andExpect(status().is(204));
    }


    
    @Test
    public void testSingleUser() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        User user = new User("aprobamos@ahora.com", "Alejandro", "Moreno", "Garrido", Role.PROFESSOR);
        when(userService.getUser(1)).thenReturn(Optional.of(user));
        String json = mapper.writeValueAsString(user);
        this.mockMvc.perform(get("/api/users/1"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json(json));
    }

    @Test
    public void testNameNoValid() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        UserDao dao = new UserDao("aprobamos@ahora.com", "", "Moreno", "Garrido", Role.PROFESSOR);
        String json = mapper.writeValueAsString(dao);
        when(this.userService.register(any(UserDao.class))).thenReturn(null);
        this.mockMvc.perform(post("/api/users")
                    .contentType("application/json").content(json))
                    .andDo(print())
                    .andExpect(status().is(422))
                    .andExpect(content().string("El nombre es obligatorio\n"));             
    }

    @Test
    public void testEmailNoValid() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        UserDao dao = new UserDao("", "Alejandro", "Moreno", "Garrido", Role.PROFESSOR);
        String json = mapper.writeValueAsString(dao);
        when(this.userService.register(any(UserDao.class))).thenReturn(null);
        this.mockMvc.perform(post("/api/users")
                    .contentType("application/json").content(json))
                    .andDo(print())
                    .andExpect(status().is(422))
                    .andExpect(content().string("El correo es obligatorio\n"));
    }

    @Test
    public void testApellido1NoValid() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        UserDao dao = new UserDao("aprobamos@ahora.com", "Alejandro", "", "Garrido", Role.PROFESSOR);
        String json = mapper.writeValueAsString(dao);
        when(this.userService.register(any(UserDao.class))).thenReturn(null);
        this.mockMvc.perform(post("/api/users")
                    .contentType("application/json").content(json))
                    .andDo(print())
                    .andExpect(status().is(422))
                    .andExpect(content().string("El primer apellido es obligatorio\n"));
    }
    
    @Test
    public void testEmailAlreadyExist() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        UserDao dao = new UserDao("aprobamos@ahora.com", "Alejandro", "Moreno", "Garrido", Role.PROFESSOR);
        String json = mapper.writeValueAsString(dao);
        when(this.userService.register(any(UserDao.class))).thenThrow(UserExistsException.class);
        this.mockMvc.perform(post("/api/users")
                    .contentType("application/json").content(json))
                    .andDo(print())
                    .andExpect(status().is(406))
                    .andExpect(content().string("Ya existe un usuario con el correo"));
    }

    @Test
    public void testUserPatch() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> mapita = new HashMap<>();
        mapita.put("name", "Alejandro");
        mapita.put("lastname1", "Moreno");       
        User user = new User("aprobamos@ahora.com", "Alejandro", "Moreno", "Garrido", Role.PROFESSOR);
        String guason = mapper.writeValueAsString(user);
        String json = mapper.writeValueAsString(mapita);        
        when(userService.updateUser(1, mapita)).thenReturn(user);       
        this.mockMvc.perform(patch("/api/users/1")
                .contentType("application/json")
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(guason));
    }
    

}
