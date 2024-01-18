package com.codot.link.domains.user.dao;

import com.codot.link.domains.link.domain.ConnectionPoint;

public interface ThreeHopDirectSearchResult extends TwoHopDirectSearchResult {

	Long getThirdHopId();

	ConnectionPoint getSecondThirdCp();
}
