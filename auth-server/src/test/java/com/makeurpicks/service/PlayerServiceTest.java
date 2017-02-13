package com.makeurpicks.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.makeurpicks.dao.ClientDetailsDAO;
import com.makeurpicks.domain.OAuthClientDetails;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = AuthServerApplication.class)
@RunWith(MockitoJUnitRunner.class)
@DataJpaTest
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
	
	@Test
	public void testGetAllClients(){
		if(oAuthClientService==null){
			fail();
		}
		Iterable<OAuthClientDetails > clients=oAuthClientService.findAllClients();
		List<OAuthClientDetails> oauthclientdetailslist=new ArrayList<OAuthClientDetails>();
		if(oauthclientdetailslist!=null){
			clients.forEach(oAuthClientDetails -> oauthclientdetailslist.add(oAuthClientDetails));	
		}
		
	//	assertNotEquals(0, oauthclientdetailslist.size());
		assertEquals(2, oauthclientdetailslist.size());
		
		
	}
	
}
