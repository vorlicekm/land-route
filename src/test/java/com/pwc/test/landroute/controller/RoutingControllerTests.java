package com.pwc.test.landroute.controller;

import com.pwc.test.landroute.service.RoutingService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RoutingController.class)
public class RoutingControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    RoutingService routingService;

    @Test
    void getRoutingReturn200() throws Exception {
        when(routingService.getRoute("CZE", "ITA")).thenReturn(List.of(new String[]{"CZE", "AUT", "ITA"}));

        mockMvc.perform(get("/routing/CZE/ITA"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.route").value(Matchers.hasSize(3)))
                .andExpect(jsonPath("$.route[0]").value(Matchers.is("CZE")))
                .andExpect(jsonPath("$.route[1]").value(Matchers.is("AUT")))
                .andExpect(jsonPath("$.route[2]").value(Matchers.is("ITA")))
                .andReturn();
    }

    //TODO: add more tests for more methods
}
