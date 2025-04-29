package com.where_car.emulator.device.application;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.where_car.emulator.device.application.dto.TokenRequest;
import com.where_car.emulator.device.application.dto.TokenResponse;
import com.where_car.emulator.global.constants.DeviceConstant;
import com.where_car.emulator.global.utill.TokenUtils;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TokenService {

	private final RestTemplate restTemplate;

	public TokenService(@Qualifier("restTemplate") RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	@Value("${emulator.endpoints.rest.token-path}")
	private String tokenEndpoint;
	
	// 토큰 캐시 (MDN을 키로 사용)
	private final Map<String, TokenInfo> tokenCache = new ConcurrentHashMap<>();

	/**
	 * MDN에 해당하는 토큰을 가져옵니다. 만료되었거나 없으면 새로 요청합니다.
	 */
	public String getToken(String mdn) {
		TokenInfo tokenInfo = tokenCache.get(mdn);

		if (tokenInfo == null || tokenInfo.isExpired()) {
			synchronized (this) {
				tokenInfo = tokenCache.get(mdn);
				if (tokenInfo == null || tokenInfo.isExpired()) {
					log.info("MDN {}에 대한 토큰이 없거나 만료되어 새로운 토큰을 요청합니다.", mdn);
					tokenInfo = requestNewToken(mdn);
					tokenCache.put(mdn, tokenInfo);
					log.info("MDN {}에 대한 새 토큰이 발급되었습니다. 토큰: {}, 만료 시간: {}", 
							mdn, TokenUtils.maskToken(tokenInfo.getToken()), tokenInfo.expiryTime);
				}
			}
		} else {
			log.debug("MDN {}에 대한 캐시된 토큰을 사용합니다. 토큰: {}, 만료 시간: {}", 
					mdn, TokenUtils.maskToken(tokenInfo.getToken()), tokenInfo.expiryTime);
		}

		return tokenInfo.getToken();
	}
	
	/**
	 * MDN에 해당하는 토큰을 캐시에서 제거하고 새로 발급받습니다.
	 * 토큰 오류 발생 시 호출됩니다.
	 */
	public String invalidateAndGetNewToken(String mdn) {
		synchronized (this) {
			log.info("MDN {}에 대한 토큰을 무효화하고 새로운 토큰을 요청합니다.", mdn);
			tokenCache.remove(mdn);
			TokenInfo tokenInfo = requestNewToken(mdn);
			tokenCache.put(mdn, tokenInfo);
			log.info("MDN {}에 대한 새 토큰이 재발급되었습니다. 토큰: {}, 만료 시간: {}", 
					mdn, TokenUtils.maskToken(tokenInfo.getToken()), tokenInfo.expiryTime);
			return tokenInfo.getToken();
		}
	}

	/**
	 * 새 토큰을 요청합니다.
	 */
	private TokenInfo requestNewToken(String mdn) {
		TokenRequest request = TokenRequest.builder()
			.mdn(mdn)
			.tid(DeviceConstant.TERMINAL_ID)
			.mid(DeviceConstant.MAKE_ID)
			.pv(DeviceConstant.PACKET_ID)
			.did(DeviceConstant.DEVICE_ID)
			.dFWVer(DeviceConstant.FIRMWARE_VERSION)
			.build();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<TokenRequest> entity = new HttpEntity<>(request, headers);

		String tokenUrl = tokenEndpoint;
		log.debug("토큰 요청 URL: {}, MDN: {}", tokenUrl, mdn);

		try {
			ResponseEntity<TokenResponse> response =
				restTemplate.exchange(tokenUrl, HttpMethod.POST, entity, TokenResponse.class);

			if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
				TokenResponse responseDto = response.getBody();

				if ("000".equals(responseDto.getRstCd())) {
					int expiryHours = Integer.parseInt(responseDto.getExPeriod());
					LocalDateTime expiryTime = LocalDateTime.now().plusHours(expiryHours);
					
					log.info("토큰 발급 성공 - MDN: {}, 토큰: {}, 만료 시간: {}", 
							mdn, TokenUtils.maskToken(responseDto.getToken()), expiryTime);
					return new TokenInfo(responseDto.getToken(), expiryTime);
				}

				log.error("토큰 요청 실패: {}", responseDto.getRstMsg());
			}
		} catch (Exception e) {
			log.error("토큰 요청 중 오류 발생: {}", e.getMessage(), e);
		}

		throw new IllegalArgumentException("토큰을 가져올 수 없습니다.");
	}

	/**
	 * 토큰 정보를 저장하는 내부 클래스
	 */
	private static class TokenInfo {
		@Getter
		private final String token;
		private final LocalDateTime expiryTime;

		public TokenInfo(String token, LocalDateTime expiryTime) {
			this.token = token;
			this.expiryTime = expiryTime;
		}

		public boolean isExpired() {
			return LocalDateTime.now().isAfter(expiryTime);
		}
	}
}
