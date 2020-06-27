package com.kpay.moneyspraying.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kpay.common.Constants.ResponseCode;
import com.kpay.common.domain.Response;
import com.kpay.moneyspraying.domain.MoneySpraying;
import com.kpay.moneyspraying.service.MoneySprayService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/v1/kpay")
public class MoneySprayController {

	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(MoneySprayController.class);

	@Autowired
	private MoneySprayService moneySprayService;

	/**
	 * 뿌릴 금액과 뿌릴 인원을 요청값으로 받아 대화방 참여자에 뿌립니다.
	 * 
	 * @param userId 사용자 아이디
	 * @param roomId 대화방 아이디
	 * @param moneySpraying 뿌리기 관련 정보
	 * @return
	 */
	@ApiOperation(value = "뿌리기 API", notes = "뿌릴 금액과 뿌릴 인원을 요청값으로 받아 대화방 참여자에 뿌립니다.")
	@PostMapping("/moneyspraying")
	public ResponseEntity<Response> sprayMoney(
			@RequestHeader(value = "X-USER-ID") long userId,
			@RequestHeader(value = "X-ROOM-ID") String roomId, 
			@Valid @RequestBody MoneySpraying moneySpraying) {
		
		moneySpraying.setUserId(userId);
		moneySpraying.setRoomId(roomId);
		
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("token", moneySprayService.sprayMoney(moneySpraying));
		
		return ResponseEntity.ok(new Response(ResponseCode.SUCCESS, null, resultMap));
	}


	@ApiOperation(value = "받기 API", notes = "참여한 대화방에 뿌려진 돈을 받습니다.")
	@PostMapping("/takeMoney")
	public ResponseEntity<Response> takeMoney(
			@RequestHeader(value = "X-USER-ID") long userId,
			@RequestHeader(value = "X-ROOM-ID") String roomId, 
			@RequestHeader(value = "X-TOKEN") String token) {
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("amount", moneySprayService.takeMoney(userId, roomId, token));
		
		return ResponseEntity.ok(new Response(ResponseCode.SUCCESS, resultMap));
	}


	@ApiOperation(value = "조회 API", notes = "뿌리기 건을 조회합니다.")
	@GetMapping("/describe")
	public ResponseEntity<Response> describeMoneySpraying(
			@RequestHeader(value = "X-USER-ID") long userId,
			@RequestHeader(value = "X-ROOM-ID") String roomId, 
			@RequestHeader(value = "X-TOKEN") String token) {
		
		return ResponseEntity.ok(new Response(ResponseCode.SUCCESS, moneySprayService.describeMoneySpraying(userId, roomId, token)));
	}


	/*
	
	@GetMapping("/highest-usage-rate-devices")
	public ResponseEntity<Response> getHighestUsageRates() {
		return ResponseEntity.ok(new Response(ResponseCode.SUCCESS, moneySprayService.getHighestUsageRateDevices()));
	}

	@GetMapping("/highest-usage-rate-devices/year/{year}")
	public ResponseEntity<Response> getHighestUsageRatesByYear(@PathVariable int year) {
		return ResponseEntity.ok(new Response(ResponseCode.SUCCESS, moneySprayService.getHighestUsageRateDevices(year)));
	}

	@GetMapping("/highest-usage-rate-devices/device/{deviceId}")
	public ResponseEntity<Response> getHighestUsageRatesByDevice(@PathVariable String deviceId) {
		return ResponseEntity.ok(new Response(ResponseCode.SUCCESS, moneySprayService.getHighestUsageRateYearByDeviceId(deviceId)));
	}

	@GetMapping("/forecast/device/{deviceId}")
	public ResponseEntity<Response> forecast(@PathVariable String deviceId) {

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		Device paramDevice = new Device();
		paramDevice.setDeviceId(deviceId);
		HttpEntity<Device> param = new HttpEntity<Device>(paramDevice, headers);

		Response response = new Response(ResponseCode.SUCCESS);
		response.setResult(restTemplate.exchange("https://5ai7lsxvk1.execute-api.ap-northeast-2.amazonaws.com/dev/forecast/device",
						HttpMethod.POST, param, String.class).getBody());
		
		return ResponseEntity.ok(response);
	}

	*/
}
