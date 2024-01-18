package com.codot.link.domains.user.dao;

/*
Entity 객체로 결과를 얻어오는 것이 아니다.
네이티브 쿼리를 이용하여 여러 테이블을 join하고 그 중에 특정 컬럼만 선택해서 가져오므로
프로젝션을 위한 위한 DAO 역할의 인터페이스 선언
*/

import com.codot.link.domains.link.domain.ConnectionPoint;

public interface TwoHopDirectSearchResult extends OneHopDirectSearchResult {

	Long getSecondHopId();

	ConnectionPoint getFirstSecondCp();
}
