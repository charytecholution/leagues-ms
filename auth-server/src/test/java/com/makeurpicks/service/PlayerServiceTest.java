package com.makeurpicks.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.makeurpicks.dao.ClientDetailsDAO;
import com.makeurpicks.domain.OAuthClientDetails;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = AuthServerApplication.class)
@RunWith(MockitoJUnitRunner.class)
//@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PlayerServiceTest {

	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private OAuthClientsService oAuthClientService;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Mock
	public ClientDetailsDAO clientDetailsDAO;
	
	@Test
	public void createPlayer()
	{
//		Player user = new PlayerBuilder("tim", "tdelesio@gmail.com", "123456").adAdmin().build();
//		user = playerService.createPlayer(user);
//		
//		UserDetails player = playerService.loadUserByUsername(user.getUsername());
//		
//		Assert.assertEquals(user.getUsername(), player.getUsername());
//		
//		Assert.assertTrue(encoder.matches("123456", player.getPassword()));
		
	}
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		OAuthClientDetails authClientDetails=new OAuthClientDetails();
		authClientDetails.setClientId("1");
		authClientDetails.setClient_secret("11111");
		
		OAuthClientDetails authClientDetails2=new OAuthClientDetails();
		authClientDetails.setClientId("2");
		authClientDetails.setClient_secret("22222");
		clientDetailsDAO.save(authClientDetails);
		clientDetailsDAO.save(authClientDetails2);
	}
	

	
}
