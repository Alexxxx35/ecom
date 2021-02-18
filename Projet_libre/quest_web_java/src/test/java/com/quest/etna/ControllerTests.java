package com.quest.etna;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.*;


@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTests {
    @Autowired
    private MockMvc mockMvc;


    private void createTestusers() throws Exception {
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"Alex_User\",\"password\": \"trololo\"}"))
                .andDo(MockMvcResultHandlers.print());

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"AlexToDelete\",\"password\": \"trololo\"}"))
                .andDo(MockMvcResultHandlers.print());

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"AlexAddress\",\"password\": \"trololo\"}"))
                .andDo(MockMvcResultHandlers.print());

        MvcResult res = mockMvc.perform(post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"AlexAddress\",\"password\": \"trololo\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("token")))
                .andReturn();
        String content = res.getResponse().getContentAsString();
        String s = content.split(",")[2];
        String token = s.substring(9, s.length() - 2);

        mockMvc.perform(post("/address")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"street\": \"Gerhard\",\"postalCode\": \"92800\",\"city\": \"AlexAddress\",\"country\": \"France\"}"))
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    public void testCreateTestUsers()throws Exception{
        createTestusers();
    }


    @Test
    public void testAuthenticate() throws Exception {

        createTestusers();

        //La route /register répond bien en 201
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"AlexAuth\",\"password\": \"trololo\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());

        //Que si vous rappelez /register avec les même paramètres, vous devez avoir une réponse 409 car l'utilisateur existe déjà.
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"AlexAuth\",\"password\": \"trololo2\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isConflict());
// La route /authenticate renvoie un statut 200 et retourne bien votre token.
        MvcResult res = mockMvc.perform(post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"AlexAuth\",\"password\": \"trololo\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("token")))
                .andReturn();

        String content = res.getResponse().getContentAsString();
        String s = content.split(",")[2];
        String token = s.substring(9, s.length() - 2);

//        La route /me retour un statut 200 avec les informations de l'utilisateur, attention à bien penser au token Bearer.
        
        mockMvc.perform(get("/me")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("username")))
                .andExpect(content().string(containsString("role")));
//CleanUp

        mockMvc.perform(delete("/user/5")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

                
        //System.out.println(token);      
    }


    @Test
    public void testUser() throws Exception {
        createTestusers();
        //La route /user retourne bien un statut 401 sans token Bearer

        mockMvc.perform(get("/user/"))
                .andExpect(status().isUnauthorized());


        //La route /user retourne bien un statut 200 avec token Bearer valide.
        MvcResult res = mockMvc.perform(post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"Alex_User\",\"password\": \"trololo\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().string(containsString("token")))
                .andReturn();

        String content = res.getResponse().getContentAsString();
        String s = content.split(",")[2];
        String token = s.substring(9, s.length() - 2);

        mockMvc.perform(get("/user/")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("username")))
                .andExpect(content().string(containsString("role")));


        //avec un ROLE_USER, la suppression retourne un statut 401
        
        mockMvc.perform(delete("/user/3")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isUnauthorized());

        //avec un ROLE_ADMIN, la suppression retourne bien un statut 200.

        MvcResult res2 = mockMvc.perform(post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"admin\",\"password\": \"admin\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("token")))
                .andReturn();
        
        content = res2.getResponse().getContentAsString();
        s = content.split(",")[2];
        token = s.substring(9, s.length() - 2);

        mockMvc.perform(delete("/user/3")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddress() throws Exception {

        createTestusers();


        //La route /address/ retourne bien un statut 401 sans token Bearer.
        mockMvc.perform(get("/address/"))
                .andExpect(status().isUnauthorized());

        //La route /address/ retourne bien un statut 200 avec un token Bearer


        MvcResult res = mockMvc.perform(post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"Alex_User\",\"password\": \"trololo\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("token")))
                .andReturn();
        String content = res.getResponse().getContentAsString();
        String s = content.split(",")[2];
        String token = s.substring(9, s.length() - 2);
        mockMvc.perform(get("/address/")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

        //L'ajout d'une adresse retourne bien un statut 201

        mockMvc.perform(post("/address")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"street\": \"Gerhard\",\"postalCode\": \"92800\",\"city\": \"Puteaux\",\"country\": \"France\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());

        // avec un ROLE_USER, la suppression d'une adresse qui n'est pas la sienne retourne un statut 401.
        mockMvc.perform(delete("/address/1")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isUnauthorized());

        // avec un ROLE_ADMIN, la suppression d'une adresse qui n'est pas la sienne retourne un status 200.
        MvcResult res2 = mockMvc.perform(post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"admin\",\"password\": \"admin\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("token")))
                .andReturn();
        content = res2.getResponse().getContentAsString();
        s = content.split(",")[2];
        token = s.substring(9, s.length() - 2);
        mockMvc.perform(delete("/address/1")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

    }

}

