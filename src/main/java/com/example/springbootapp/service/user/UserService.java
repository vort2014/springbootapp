package com.example.springbootapp.service.user;

import com.example.springbootapp.web.controller.user.NewUserJson;
import com.example.springbootapp.web.controller.user.UpdateUserJson;
import com.example.springbootapp.web.controller.user.UserJson;

import java.util.Collection;

public interface UserService {

    Collection<UserJson> findAll();

    UserJson findById(Long id);

    Collection<UserJson> findByOrganizationId(Long organizationId);

    UserJson save(NewUserJson newUserJson);

    void deleteAllById(Collection<Long> ids);

    UserJson update(Long userId, UpdateUserJson updateUserJson);

    Collection<UserJson> updateUsersInOrganization(Long organizationId, Collection<Long> userIds);
}
