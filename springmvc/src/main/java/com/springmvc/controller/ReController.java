package com.smartsky.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.isNull;
import static org.mockito.Mockito.eq;

import com.smartsky.dto.ChangeStatusRequestVo;
import com.smartsky.dto.CurrentAbrProvisionDto;
import com.smartsky.dto.ScopeDto;
import com.smartsky.dto.UserDto;
import com.smartsky.entity.AbrProvisionHistEntity;
import com.smartsky.entity.CurrentAbrProvisionEntity;
import com.smartsky.exception.ProvisioningException;
import com.smartsky.gateway.SoapClientGateway;
import com.smartsky.model.graphql.AbrInfo;
import com.smartsky.model.xml.pcrf.ChangeSubscriberStatusRequest;
import com.smartsky.model.xml.pcrf.ChangeSubscriberStatusResponse;
import com.smartsky.model.xml.pcrf.StatusType;

import com.smartsky.model.xml.pcrf.UpdateServiceResponse;
import com.smartsky.repository.AbrProvisionHistRepository;
import com.smartsky.repository.CurrentAbrProvisionRepository;
import com.smartsky.service.ResourceHssUiccService;

import com.smartsky.utils.Utils;
import com.smartsky.utils.TestUtilities;
import org.junit.jupiter.api.Test;

import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Collections;
import java.util.Optional;

@SpringBootTest
public class DisableActivationServiceImplTest {

	private static final String SUCCESSFUL = "SUCCESSFUL";
	private static final String IMSI = "1234567890";
	private static final String PLANE_TAIL_NUMBER = "224";
	private static final String ABR_SERIAL_NUMBER = "32424";
	private static final StatusType STATUS_TYPE = StatusType.ACTIVE;
	private static final Long BULK_PROV_ID = 27L;

	@Mock
	SoapClientGateway soapClientGateway;

	@Mock
	CurrentAbrProvisionRepository currentAbrProvisionRepository;

	@Mock
	ResourceHssUiccService hssUiccService;

	@Mock
	AbrProvisionHistRepository abrProvisionHistRepository;

	@Mock
	Utils utils;

	@Mock
	GraphqlClientServiceImpl graphqlClientServiceImpl;

	@Mock
	LookupServiceImpl lookupServiceImpl;

	@InjectMocks
	DisableActivationServiceImpl disableActivationServiceImpl;

	@Mock
	UpdateServiceResponse updateServiceResponse;

	@Mock
	ChangeSubscriberStatusResponse changeSubscriberStatusResponse;

	@Test
	public void test_disableActivationServiceImpl_Success() throws ProvisioningException {

		ChangeStatusRequestVo changeStatusRequestVo = createChangeStatusRequestVo();
		UserDto userDto = TestUtilities.createUserDto();
		ScopeDto scopeDto = TestUtilities.createScopeDto();
		List<ScopeDto> scopeDtos = Collections.singletonList(scopeDto);
		userDto.setScopes(scopeDtos);
		CurrentAbrProvisionEntity currentAbrProvisionEntity = TestUtilities.createCurrentAbrProvisionEntity();
		AbrProvisionHistEntity histEntity = new AbrProvisionHistEntity();
		histEntity.setAbrSerialNumber(currentAbrProvisionEntity.getAbrSerialNumber());
		histEntity.setPlaneTailNumber(currentAbrProvisionEntity.getPlaneTailNumber());
		Optional<AbrProvisionHistEntity> abrHistOptional = Optional.of(histEntity);
		AbrInfo abrInfo = new AbrInfo();

		when(utils.getUserFromToken()).thenReturn(userDto);
		when(currentAbrProvisionRepository.save(any(CurrentAbrProvisionEntity.class)))
				.thenReturn(currentAbrProvisionEntity);
		when(lookupServiceImpl.lookUp(anyString(), anyString(), anyString())).thenReturn(histEntity);
		when(graphqlClientServiceImpl.getImsiByAbrSerialNumberAndPlaneTailNumber(anyString(), anyString()))
				.thenReturn(abrInfo);
		when(soapClientGateway.changeSubscriberStatus(any(ChangeSubscriberStatusRequest.class), eq(false), isNull()))
				.thenReturn(new ChangeSubscriberStatusResponse());
		when(abrProvisionHistRepository.findById(anyString())).thenReturn(abrHistOptional);

		CurrentAbrProvisionDto result = disableActivationServiceImpl.changeStatus(changeStatusRequestVo);
		assertEquals(SUCCESSFUL, result.getStatus());
	}

	@Test
	public void test_invalidRequest() throws ProvisioningException {

		ChangeStatusRequestVo changeStatusRequestVo = createChangeStatusRequestVo();
		UserDto userDto = TestUtilities.createUserDto();
		ScopeDto scopeDto = TestUtilities.createScopeDto();
		List<ScopeDto> scopeDtos = Collections.singletonList(scopeDto);
		userDto.setScopes(scopeDtos);
		ChangeSubscriberStatusResponse response = new ChangeSubscriberStatusResponse();
		response.setErrorCode(2);
		CurrentAbrProvisionEntity currentAbrProvisionEntity = TestUtilities.createCurrentAbrProvisionEntity();
		AbrProvisionHistEntity histEntity = createAbrProvisionHistEntity();
		histEntity.setImsi(changeStatusRequestVo.getImsi());
		histEntity.setAbrSerialNumber(changeStatusRequestVo.getPlaneTailNumber());
		histEntity.setPlaneTailNumber(changeStatusRequestVo.getAbrSerialNumber());
		Optional<AbrProvisionHistEntity> abrHistOptional = Optional.of(histEntity);

		when(soapClientGateway.changeSubscriberStatus(any(ChangeSubscriberStatusRequest.class), anyBoolean(),
				ArgumentMatchers.nullable(Long.class))).thenReturn(response);
		when(utils.getUserFromToken()).thenReturn(userDto);
		when(abrProvisionHistRepository.findById(anyString())).thenReturn(Optional.empty());
		when(lookupServiceImpl.lookUp(anyString(), anyString(), anyString())).thenReturn(histEntity);
		when(abrProvisionHistRepository.findById(anyString())).thenReturn(abrHistOptional);
		when(currentAbrProvisionRepository.save(any())).thenReturn(currentAbrProvisionEntity);

		assertThrows(ProvisioningException.class, () -> {
			disableActivationServiceImpl.changeStatus(changeStatusRequestVo);
		});
	}

	@Test
	public void failure_testCase() throws ProvisioningException {

		ChangeStatusRequestVo changeStatusRequestVo = createChangeStatusRequestVo();
		UserDto userDto = TestUtilities.createUserDto();
		ScopeDto scopeDto = TestUtilities.createScopeDto();
		List<ScopeDto> scopeDtos = Collections.singletonList(scopeDto);
		userDto.setScopes(scopeDtos);
		ChangeSubscriberStatusResponse response = new ChangeSubscriberStatusResponse();
		response.setErrorCode(2);
		CurrentAbrProvisionEntity currentAbrProvisionEntity = TestUtilities.createCurrentAbrProvisionEntity();
		AbrProvisionHistEntity histEntity = new AbrProvisionHistEntity();
		Optional<AbrProvisionHistEntity> abrHistOptional = Optional.of(histEntity);

		when(soapClientGateway.changeSubscriberStatus(any(ChangeSubscriberStatusRequest.class), anyBoolean(),
				ArgumentMatchers.nullable(Long.class))).thenReturn(response);
		when(utils.getUserFromToken()).thenReturn(userDto);
		when(abrProvisionHistRepository.findById(anyString())).thenReturn(Optional.empty());
		when(lookupServiceImpl.lookUp(anyString(), anyString(), anyString())).thenReturn(null);
		when(abrProvisionHistRepository.findById(anyString())).thenReturn(abrHistOptional);
		when(currentAbrProvisionRepository.save(any())).thenReturn(currentAbrProvisionEntity);

		assertThrows(IllegalArgumentException.class, () -> {
			disableActivationServiceImpl.changeStatus(changeStatusRequestVo);
		});
	}

	private ChangeStatusRequestVo createChangeStatusRequestVo() {
		ChangeStatusRequestVo requestVo = new ChangeStatusRequestVo();
		requestVo.setImsi(IMSI);
		requestVo.setAbrSerialNumber(ABR_SERIAL_NUMBER);
		requestVo.setPlaneTailNumber(PLANE_TAIL_NUMBER);
		requestVo.setStatusType(STATUS_TYPE);
		requestVo.setBulkProvId(BULK_PROV_ID);
		return requestVo;
	}

	private AbrProvisionHistEntity createAbrProvisionHistEntity() {
		AbrProvisionHistEntity abrProvisionHistEntity = AbrProvisionHistEntity.builder().imsi(IMSI).build();
		return abrProvisionHistEntity;
	}
}