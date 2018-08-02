package pl.jstk.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pl.jstk.constants.ViewNames;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String LOGGED = "You are logged";

    @Test
    public void shouldReturnLoginView() throws Exception {

        // when
        ResultActions resultActions = mockMvc.perform(get("/login"));
        // then
        resultActions.andExpect(status().isOk())
                .andExpect(view().name(ViewNames.LOGIN));

    }

    @Test
    public void shouldReturnWelcomeViewWhenLogged() throws Exception {

        // when
        ResultActions resultActions = mockMvc.perform(post("/loginSuccess"));
        // then
        resultActions.andExpect(status().isOk())
                .andExpect(view().name(ViewNames.WELCOME))
                .andExpect(model().attribute("info", LOGGED));

    }

    @Test
    public void shouldReturnStatusOkWhenLogedAsAdmin() throws Exception {

        // when
        ResultActions resultActions = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", "admin")
                .param("password", "123"));
        // then
        resultActions.andExpect(status().isOk());

    }

    @Test
    public void shouldReturnStatusOkWhenLogedAsUser() throws Exception {

        // when
        ResultActions resultActions = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", "user")
                .param("password", "123"));
        // then
        resultActions.andExpect(status().isOk());

    }

    @Test
    public void shouldReturnStatus302WhenNotLogged() throws Exception {

        // when
        ResultActions resultActions = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", "admin")
                .param("password", "user1"));
        // then
        resultActions.andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/" + ViewNames.LOGIN_ERROR));

    }

    @Test
    public void shouldReturnRedirectLoginWhenNotLogged() throws Exception {

        // when
        ResultActions resultActions = mockMvc.perform(get("/loginError"));
        // then
        resultActions.andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/" + ViewNames.LOGIN));

    }

}
