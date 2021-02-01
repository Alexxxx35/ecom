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
public class ControllerTests{
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    public void testAuthenticate() throws Exception {
        mockMvc.perform(post("/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"username\": \"Alex\",\"password\": \"trololo\"}"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isCreated()); 
                    
        mockMvc.perform(post("/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"username\": \"Alex\",\"password\": \"trololo\"}"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isConflict());   
                    
        MvcResult res = mockMvc.perform(post("/authenticate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"username\": \"Alex\",\"password\": \"trololo\"}"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("token")))
                    .andReturn();   

        String content = res.getResponse().getContentAsString();
        String token = content.substring(10, content.length()-2);
        mockMvc.perform(get("/me")
            .header("Authorization", "Bearer " + token))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("username")))
            .andExpect(content().string(containsString("role")));

        //System.out.println(token);      
    } 

    @Test
    public void testUserToken() throws Exception {
        mockMvc.perform(get("/user/"))
                    .andExpect(status().isUnauthorized()); 
        
        mockMvc.perform(post("/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"username\": \"Alex\",\"password\": \"trololo\"}"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isCreated()); 


        MvcResult res = mockMvc.perform(post("/authenticate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"username\": \"Alex\",\"password\": \"trololo\"}"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("token")))
                    .andReturn();   
        String content = res.getResponse().getContentAsString();
        String token = content.substring(10, content.length()-2);
        mockMvc.perform(get("/user/")
            .header("Authorization", "Bearer " + token))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("username")))
            .andExpect(content().string(containsString("role")));


    }

    @Test
    //@WithMockUser(roles = "USER")
    public void checkDeleteUserAsUser() throws Exception{
        mockMvc.perform(post("/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"username\": \"Alex\",\"password\": \"trololo\"}"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isCreated()); 


        MvcResult res = mockMvc.perform(post("/authenticate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"username\": \"Alex\",\"password\": \"trololo\"}"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("token")))
                    .andReturn();   
        String content = res.getResponse().getContentAsString();
        String token = content.substring(10, content.length()-2);
        mockMvc.perform(delete("/user/5")
        .header("Authorization", "Bearer " + token))
                    .andExpect(status().isUnauthorized());
        mockMvc.perform(delete("/user/1")
        .header("Authorization", "Bearer " + token))
                    .andExpect(status().isOk());
            
    }

    @Test
    public void checkDeleteUserAsAdmin() throws Exception{
        mockMvc.perform(post("/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"username\": \"Alex\",\"password\": \"trololo\"}"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isCreated()); 
        mockMvc.perform(post("/register/admin")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isCreated()); 

        MvcResult res = mockMvc.perform(post("/authenticate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"username\": \"admin\",\"password\": \"admin\"}"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("token")))
                    .andReturn();   
        String content = res.getResponse().getContentAsString();
        String token = content.substring(10, content.length()-2);
        
        mockMvc.perform(delete("/user/1")
        .header("Authorization", "Bearer " + token)) 
            .andExpect(status().isOk());
    }

    @Test
    public void checkAddressToken() throws Exception{
        mockMvc.perform(get("/address/"))
                    .andExpect(status().isUnauthorized()); 
        
        mockMvc.perform(post("/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"username\": \"Alex\",\"password\": \"trololo\"}"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isCreated()); 


        MvcResult res = mockMvc.perform(post("/authenticate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"username\": \"Alex\",\"password\": \"trololo\"}"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("token")))
                    .andReturn();   
        String content = res.getResponse().getContentAsString();
        String token = content.substring(10, content.length()-2);
        mockMvc.perform(get("/address/")
            .header("Authorization", "Bearer " + token))
            .andExpect(status().isOk());

    }

    @Test
    public void checkCreateAddress()throws Exception {
        mockMvc.perform(post("/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"username\": \"Alex\",\"password\": \"trololo\"}"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isCreated()); 


        MvcResult res = mockMvc.perform(post("/authenticate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"username\": \"Alex\",\"password\": \"trololo\"}"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("token")))
                    .andReturn();   
        String content = res.getResponse().getContentAsString();
        String token = content.substring(10, content.length()-2);
        mockMvc.perform(post("/address")
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"road\": \"Gerhard\",\"postalCode\": \"92800\",\"city\": \"Puteaux\",\"country\": \"France\"}"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isCreated()); 
            
    }


    @Test
    public void checkDeleteAddressAsUser()throws Exception {
        mockMvc.perform(post("/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"username\": \"Alex\",\"password\": \"trololo\"}"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isCreated());

        mockMvc.perform(post("/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"username\": \"Bernard\",\"password\": \"trololo\"}"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isCreated());

        MvcResult res = mockMvc.perform(post("/authenticate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"username\": \"Alex\",\"password\": \"trololo\"}"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("token")))
                    .andReturn();   
        String content = res.getResponse().getContentAsString();
        String token = content.substring(10, content.length()-2);

        mockMvc.perform(post("/address")
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"road\": \"Gerhard\",\"postalCode\": \"92800\",\"city\": \"Puteaux\",\"country\": \"France\"}"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isCreated()); 
        
        res = mockMvc.perform(post("/authenticate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"username\": \"Bernard\",\"password\": \"trololo\"}"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("token")))
                    .andReturn();   
        content = res.getResponse().getContentAsString();
        token = content.substring(10, content.length()-2);

        
        mockMvc.perform(delete("/address/1")
        .header("Authorization", "Bearer " + token)) 
            .andExpect(status().isUnauthorized());

        

    }


    @Test
    public void checkDeleteAddressAsAdmin()throws Exception {
        mockMvc.perform(post("/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"username\": \"Alex\",\"password\": \"trololo\"}"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isCreated());

        MvcResult res = mockMvc.perform(post("/authenticate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"username\": \"Alex\",\"password\": \"trololo\"}"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("token")))
                    .andReturn();   
        String content = res.getResponse().getContentAsString();
        String token = content.substring(10, content.length()-2);

        mockMvc.perform(post("/address")
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"road\": \"Gerhard\",\"postalCode\": \"92800\",\"city\": \"Puteaux\",\"country\": \"France\"}"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isCreated()); 



        mockMvc.perform(post("/register/admin")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isCreated()); 


        res = mockMvc.perform(post("/authenticate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"username\": \"admin\",\"password\": \"admin\"}"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("token")))
                    .andReturn();   
        content = res.getResponse().getContentAsString();
        token = content.substring(10, content.length()-2);
        
        
        mockMvc.perform(delete("/address/1")
        .header("Authorization", "Bearer " + token)) 
            .andExpect(status().isOk());

    }





}

