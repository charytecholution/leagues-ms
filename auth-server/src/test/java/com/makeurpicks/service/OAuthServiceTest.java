package com.makeurpicks.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.security.oauth2.provider.ClientDetails;

import com.makeurpicks.dao.ClientDetailsDAO;
import com.makeurpicks.domain.OAuthClientDetails;
import com.makeurpicks.domain.OAuthClientDetailsBuilder;
import com.makeurpicks.exception.OAuthclientValidationException;
import com.makeurpicks.exception.OAuthclientValidationException.OAuthClientExceptions;
import com.makeurpicks.exception.PlayerValidationException;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = AuthServerApplication.class)
@RunWith(MockitoJUnitRunner.class)

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OAuthServiceTest {

	
	@Autowired
	private OAuthClientsService oAuthClientService;
	
	
	
	@Mock
	public ClientDetailsDAO clientDetailsDAO;
	

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
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
	
	
	@Test
	public void testCreateClientsInValidDataNoClientId() throws OAuthclientValidationException{
		if(oAuthClientService==null){
			fail();
		}
		OAuthClientDetails oAuthClientDetails=new OAuthClientDetailsBuilder(null, "testsecret", "test","admin", "local","http://localhost", Boolean.FALSE.toString()).build();
		OAuthClientDetails output=oAuthClientService.createOAuthClientDetails(oAuthClientDetails);
		
	//	when(leagueService.getLeagueByName(league.getLeagueName())).thenReturn(league);
		//oAuthClientService.
		expectedEx.expect(OAuthclientValidationException.class);
		expectedEx.expectMessage(OAuthClientExceptions.CLIENT_ID_NULL.toString());
		assertNull(output);
		
		
	}
	
	@Test
	public void testCreateClientsInValidDataNoGrantType() throws OAuthclientValidationException{
		if(oAuthClientService==null){
			fail();
		}
		OAuthClientDetails oAuthClientDetails=new OAuthClientDetailsBuilder("123", "testsecret", "test",null, "local","http://localhost", Boolean.FALSE.toString()).build();
		OAuthClientDetails output=oAuthClientService.createOAuthClientDetails(oAuthClientDetails);
		
	//	when(leagueService.getLeagueByName(league.getLeagueName())).thenReturn(league);
		//oAuthClientService.
		expectedEx.expect(OAuthclientValidationException.class);
		expectedEx.expectMessage(OAuthClientExceptions.AUTH_GRANT_TYPE_NULL_OR_EMPTY.toString());
		assertNull(output);
		
		
	}
	
	@Test
	public void testCreateClientsInValidDataNoURI() throws OAuthclientValidationException{
		if(oAuthClientService==null){
			fail();
		}
		OAuthClientDetails oAuthClientDetails=new OAuthClientDetailsBuilder("123", "testsecret", "test","admin", "local",null, Boolean.FALSE.toString()).build();
		OAuthClientDetails output=oAuthClientService.createOAuthClientDetails(oAuthClientDetails);
		
	//	when(leagueService.getLeagueByName(league.getLeagueName())).thenReturn(league);
		//oAuthClientService.
		expectedEx.expect(OAuthclientValidationException.class);
		expectedEx.expectMessage(OAuthClientExceptions.REDIRECT_URI_NULL_OR_EMPTY.toString());
		assertNull(output);
		
		
	}
	
	@Test
	public void testCreateClientsInValidDataNoScope() throws OAuthclientValidationException{
		if(oAuthClientService==null){
			fail();
		}
		OAuthClientDetails oAuthClientDetails=new OAuthClientDetailsBuilder("123", "testsecret", "test","admin", null,"http://localhost", Boolean.FALSE.toString()).build();
		OAuthClientDetails output=oAuthClientService.createOAuthClientDetails(oAuthClientDetails);
		
	//	when(leagueService.getLeagueByName(league.getLeagueName())).thenReturn(league);
		//oAuthClientService.
		expectedEx.expect(OAuthclientValidationException.class);
		expectedEx.expectMessage(OAuthClientExceptions.SCOPE_NULL_OR_EMPTY.toString());
		assertNull(output);

		
		
	}
	
	@Test
	public void testCreateClientsValidData() throws OAuthclientValidationException{
		if(oAuthClientService==null){
			fail();
		}
		OAuthClientDetails oAuthClientDetails=new OAuthClientDetailsBuilder("123", "testsecret", "test","admin", "testscope","http://localhost", Boolean.FALSE.toString()).build();
		OAuthClientDetails output=oAuthClientService.createOAuthClientDetails(oAuthClientDetails);
		ClientDetails clientDetails=oAuthClientService.loadClientByClientId(oAuthClientDetails.getClientId());
		when(oAuthClientService.loadClientByClientId(oAuthClientDetails.getClientId())).thenReturn(clientDetails);
		
		assertNotNull(output);
		assertNotNull(clientDetails.getClientSecret());

		
		
	}
	
}
