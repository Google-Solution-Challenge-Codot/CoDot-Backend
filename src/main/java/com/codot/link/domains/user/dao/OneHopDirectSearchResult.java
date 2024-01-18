package com.codot.link.domains.user.dao;

import com.codot.link.domains.link.domain.ConnectionPoint;

public interface OneHopDirectSearchResult {

	Long getFirstHopId();

	ConnectionPoint getSourceFirstCp();
}
