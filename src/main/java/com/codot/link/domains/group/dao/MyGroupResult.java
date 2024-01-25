package com.codot.link.domains.group.dao;

import com.codot.link.domains.group.domain.Role;

public interface MyGroupResult {

	Long getGroupId();

	String getGroupName();

	String getDescription();

	Role getRole();
}
