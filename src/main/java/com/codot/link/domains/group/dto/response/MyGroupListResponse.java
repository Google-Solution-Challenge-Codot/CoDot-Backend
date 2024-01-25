package com.codot.link.domains.group.dto.response;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MyGroupListResponse {

	private Integer hostCount;
	private List<MyGroupResponse> myGroupAsHost;
	private Integer participantCount;
	private List<MyGroupResponse> myGroupAsParticipant;

	@Builder(access = AccessLevel.PRIVATE)
	private MyGroupListResponse(List<MyGroupResponse> hosts, List<MyGroupResponse> participants) {
		this.hostCount = hosts.size();
		this.participantCount = participants.size();
		this.myGroupAsHost = hosts;
		this.myGroupAsParticipant = participants;
	}

	public static MyGroupListResponse of(List<MyGroupResponse> hosts, List<MyGroupResponse> participants) {
		return MyGroupListResponse.builder()
			.hosts(hosts)
			.participants(participants)
			.build();
	}
}
