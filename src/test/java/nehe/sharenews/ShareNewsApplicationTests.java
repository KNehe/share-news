package nehe.sharenews;

import com.google.gson.Gson;
import nehe.sharenews.models.User;
import nehe.sharenews.security.UserPrincipal;
import nehe.sharenews.services.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ShareNewsApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AuthService authService;

	Gson gson = new Gson();

	User regUser = new User("name","name","password","fff@f.com");

	public static UserDetails testUserDetails() {

		var user = new User("ff","fff","Fff","fff@f.com");
		user.setRole("User");
		user.setIsEnabled(1);

		return new UserPrincipal(user);
	}

	@Test
	void shouldReturnFirstPageWhenNotLoggedIn() throws Exception {

		mockMvc.perform( get("/"))
				.andExpect( status().isOk() )
				.andExpect( view().name("login"));
	}

	@Test
	void shouldNotReturnFirstPageWhenLoggedIn() throws Exception {

//		mockMvc.perform(formLogin("/login").user("ere").password("444"))
//				.andExpect( status().isOk() )
//				.andExpect( view().name("posts"));

		mockMvc.perform( get("/").with( user( testUserDetails() )))
				.andExpect( status().isFound() )
				.andExpect( view().name("redirect:/posts"))
				.andExpect( authenticated().withRoles("User"))
				.andExpect( authenticated().withUsername("fff@f.com"));
	}

	@Test
	void shouldReturnLoginPageWhenNotLoggedIn() throws Exception {

		mockMvc.perform( get("/login"))
				.andExpect( status().isOk() )
				.andExpect( view().name("login"));

	}

	@Test
	void shouldNotReturnLoginPageWhenLoggedIn() throws Exception {

		mockMvc.perform( get("/login").with( user( testUserDetails() )))
				.andExpect( status().isFound() )
				.andExpect( view().name("redirect:/posts"))
				.andExpect( authenticated().withRoles("User"))
				.andExpect( authenticated().withUsername("fff@f.com"));

	}

	@Test
	void shouldReturnRegisterPageWhenNotLoggedIn() throws Exception {

		mockMvc.perform( get("/register"))
				.andExpect( status().isOk() )
				.andExpect( view().name("register"));

	}

	@Test
	void shouldNotReturnRegisterPageWhenLoggedIn() throws Exception {

		mockMvc.perform( get("/register").with( user( testUserDetails() )))
				.andExpect( status().isFound() )
				.andExpect( view().name("redirect:/posts"))
				.andExpect( authenticated().withRoles("User"))
				.andExpect( authenticated().withUsername("fff@f.com"));

	}

	@Test
	void shouldNotRegisterUserWhenEmailExists() throws  Exception {

		when( authService.isEmailExists( anyString() )).thenReturn( true );

		RequestBuilder requestBuilder =  post("/register")
				.param("firstName",regUser.getFirstName())
				.param("lastName", regUser.getLastName())
				.param("email", regUser.getEmail())
				.param( "password", regUser.getPassword())
				.with( csrf());

		mockMvc.perform( requestBuilder)
				.andDo(MockMvcResultHandlers.print())
				.andExpect( status().isFound() )
				.andExpect( view().name("redirect:/register"));

	}

	@Test
	void shouldRegisterUser() throws  Exception {

		when( authService.isEmailExists( anyString() )).thenReturn( false );

		when( authService.registerUser( any(User.class))).thenReturn( regUser );

		RequestBuilder requestBuilder =  post("/register")
				.param("firstName",regUser.getFirstName())
				.param("lastName", regUser.getLastName())
				.param("email", regUser.getEmail())
				.param( "password", regUser.getPassword())
				.with( csrf());

		mockMvc.perform( requestBuilder)
				.andDo(MockMvcResultHandlers.print())
				.andExpect( status().isFound() )
				.andExpect( view().name("redirect:/posts"));

	}

	@Test
	void shouldNotRegisterUser() throws  Exception {

		when( authService.isEmailExists( anyString() )).thenReturn( false );

		when( authService.registerUser( any(User.class))).thenReturn( null );

		RequestBuilder requestBuilder =  post("/register")
				.param("firstName",regUser.getFirstName())
				.param("lastName", regUser.getLastName())
				.param("email", regUser.getEmail())
				.param( "password", regUser.getPassword())
				.with( csrf());

		mockMvc.perform( requestBuilder)
				.andDo(MockMvcResultHandlers.print())
				.andExpect( status().isFound() )
				.andExpect( view().name("redirect:/register"));

	}





}
