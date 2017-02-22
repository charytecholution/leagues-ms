package com.makeurpicks.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
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



@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OAuthServiceTest {

	
	@Autowired
	@InjectMocks
	private OAuthClientsService oAuthClientService;
	
	
	
	
	//@InjectMocks
	//@DataJpaTest
	@Mock
	public ClientDetailsDAO clientDetailsDAOMock;
	

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		/*OAuthClientDetails authClientDetails=new OAuthClientDetails();
		authClientDetails.setClientId("1");
		authClientDetails.setClient_secret("111111");
		
		OAuthClientDetails authClientDetails2=new OAuthClientDetails();
		authClientDetails.setClientId("2");
		authClientDetails.setClient_secret("22222");
		clientDetailsDAO.save(authClientDetails);
		clientDetailsDAO.save(authClientDetails2);*/
	}
	
	@Test
	public void testGetAllClients(){
		if(oAuthClientService==null){
			fail();
		}
		OAuthClientDetails oAuthClientDetails1=new OAuthClientDetailsBuilder("123123", "testsecret", "ROLE_TRUSTED_CLIENT","client_credentials,password,authorization_code,refresh_token", "read,write","http://localhost", Boolean.FALSE.toString()).build();
		OAuthClientDetails oAuthClientDetails2=new OAuthClientDetailsBuilder("123123", "testsecret", "ROLE_TRUSTED_CLIENT","client_credentials,password,authorization_code,refresh_token", "read,write","http://localhost", Boolean.FALSE.toString()).build();
		List<OAuthClientDetails> stubReturnList=new ArrayList<OAuthClientDetails>();
		
		stubReturnList.add(oAuthClientDetails1);
		stubReturnList.add(oAuthClientDetails2);
		when(clientDetailsDAOMock.findAll()).thenReturn(stubReturnList);
		Iterable<OAuthClientDetails > clients=oAuthClientService.findAllClients();
		List<OAuthClientDetails> oauthclientdetailslist=new ArrayList<OAuthClientDetails>();
		if(clients!=null){
			clients.forEach(oAuthClientDetails -> oauthclientdetailslist.add(oAuthClientDetails));	
		}
		
		//assertNotEquals(Long.valueOf(0).longValue(), Long.valueOf(oauthclientdetailslist.size()).longValue());
		//if()
		assertThat(oauthclientdetailslist.size()==2);
		
		
	}
	
	
	@Test
	public void testCreateClientsInValidDataNoClientId() throws OAuthclientValidationException {
		if(oAuthClientService==null){
			fail();
		}
		expectedEx.expect(OAuthclientValidationException.class);
		expectedEx.expectMessage(OAuthClientExceptions.CLIENT_ID_NULL.toString());
		//assertThat(expectedEx.e)
		//SINCE THERE COULD BE MANY REASONS WITHINA SINGLE EXCEPTION BLOCK, GET MESSAGES RETURN A ARRAY. COMPARING IT WITH A 
		//SINGLE STRING ALWAYS MAKES IT FAIL. HENCE USING MATCHER

		
		//OAuthClientDetails oAuthClientDetails=new OAuthClientDetailsBuilder(null, "testsecret", "ROLE_TRUSTED_CLIENT","client_credentials,password,authorization_code,refresh_token", "read,write","http://localhost", Boolean.FALSE.toString()).build();
		OAuthClientDetails oAuthClientDetails=new OAuthClientDetailsBuilder(null, "testsecret", "ROLE_TRUSTED_CLIENT","client_credentials,password,authorization_code,refresh_token", "read,write",null, Boolean.FALSE.toString()).build();

		OAuthClientDetails output=null;
		
		output = oAuthClientService.createOAuthClientDetails(oAuthClientDetails);
			
	//	assertNull(output);
	
		
		
	}
	
	@Test
	public void testCreateClientsInValidDataNoGrantType() throws OAuthclientValidationException {
		if(oAuthClientService==null){
			fail();
		}
		expectedEx.expect(OAuthclientValidationException.class);
		expectedEx.expectMessage(OAuthClientExceptions.AUTH_GRANT_TYPE_NULL_OR_EMPTY.toString());
		
		OAuthClientDetails oAuthClientDetails=new OAuthClientDetailsBuilder("123", "testsecret", "ROLE_TRUSTED_CLIENT",null, "read,write","http://localhost", Boolean.FALSE.toString()).build();

		//OAuthClientDetails output=null;
		
		
		OAuthClientDetails	output = oAuthClientService.createOAuthClientDetails(oAuthClientDetails);
		
		//assertNull(output);
		
		
	}
	
	@Test
	public void testCreateClientsInValidDataNoURI() throws OAuthclientValidationException {
		if(oAuthClientService==null){
			fail();
		}
		
		
		expectedEx.expect(OAuthclientValidationException.class);
		expectedEx.expectMessage(OAuthClientExceptions.REDIRECT_URI_NULL_OR_EMPTY.toString());
		
		OAuthClientDetails oAuthClientDetails=new OAuthClientDetailsBuilder("123", "testsecret", "ROLE_TRUSTED_CLIENT","client_credentials,password,authorization_code,refresh_token", "read,write",null, Boolean.FALSE.toString()).build();

		//OAuthClientDetails output=null;
	
		OAuthClientDetails	output = oAuthClientService.createOAuthClientDetails(oAuthClientDetails);
		
	//	when(leagueService.getLeagueByName(league.getLeagueName())).thenReturn(league);
		//oAuthClientService.
		
		//assertNull(output);
		
		
	}
	
	@Test
	public void testCreateClientsInValidDataNoScope() throws OAuthclientValidationException {
		if(oAuthClientService==null){
			fail();
		}
		expectedEx.expect(OAuthclientValidationException.class);
		expectedEx.expectMessage(OAuthClientExceptions.SCOPE_NULL_OR_EMPTY.toString());
		OAuthClientDetails oAuthClientDetails=new OAuthClientDetailsBuilder("1234", "testsecret", "ROLE_TRUSTED_CLIENT","client_credentials,password,authorization_code,refresh_token", null,"http://localhost", Boolean.FALSE.toString()).build();

		//OAuthClientDetails output=null;
		OAuthClientDetails output = oAuthClientService.createOAuthClientDetails(oAuthClientDetails);
		
		
	//	when(leagueService.getLeagueByName(league.getLeagueName())).thenReturn(league);
		//oAuthClientService.
		
		//assertNull(output);

		
		
	}
	
	@Test
	public void testCreateClientsValidData() throws OAuthclientValidationException {
		if(oAuthClientService==null){
			fail();
		}
		OAuthClientDetails oAuthClientDetails=new OAuthClientDetailsBuilder("123", "testsecret", "ROLE_TRUSTED_CLIENT","client_credentials,password,authorization_code,refresh_token", "read,write","http://localhost", Boolean.FALSE.toString()).build();
		//OAuthClientDetails output=null;
		
		OAuthClientDetails output = oAuthClientService.createOAuthClientDetails(oAuthClientDetails);
		
	//	ClientDetails clientDetails=oAuthClientService.loadClientByClientId(oAuthClientDetails.getClientId());
		//when(oAuthClientService.loadClientByClientId(oAuthClientDetails.getClientId())).thenReturn(clientDetails);
		
		assertNotNull(output);
		assertNotNull(output.getClientSecret());

		
		
	}
	
}
