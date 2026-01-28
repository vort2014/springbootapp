package com.example.springbootapp.service.user;

import com.example.springbootapp.dao.organization.OrganizationEntity;
import com.example.springbootapp.dao.organization.OrganizationRepository;
import com.example.springbootapp.dao.user.UserEntity;
import com.example.springbootapp.dao.user.UserRepository;
import com.example.springbootapp.web.controller.user.NewUserJson;
import com.example.springbootapp.web.controller.user.UpdateUserJson;
import com.example.springbootapp.web.controller.user.UserJson;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;

    /*
    Save new user
    */
    @Override
    @Transactional
    public UserJson save(NewUserJson newUserJson) {
        Long organizationId = newUserJson.getOrganizationId();
        UserEntity userEntity = UserEntity.from(newUserJson);
        if (organizationId != null) {
            OrganizationEntity organizationEntity = organizationRepository.findById(organizationId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "organizationId=" + organizationId + " not found"));
            userEntity.setOrganization(organizationEntity);
        }
        UserEntity entity = userRepository.save(userEntity);
        return UserJson.from(entity);
    }

    /*
    Update user's information including organization
    */
    @Override
    @Transactional
    public UserJson update(Long userId, UpdateUserJson json) {
        // check parameters
        Objects.requireNonNull(userId, "userId shouldn't be null");
        if (!userId.equals(json.getId())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "userId in url should be equal to id in json body");

        // check if user exists
        UserEntity entity = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid user id in json body"));

        // update user in database
        entity.setName(json.getName());
        entity.setEmail(json.getEmail());
        Long organizationId = json.getOrganizationId();
        if (organizationId != null) {
            OrganizationEntity organizationEntity = organizationRepository.findById(organizationId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "organizationId=" + organizationId + " not found"));
            entity.setOrganization(organizationEntity);
        } else {
            entity.setOrganization(null);
        }
        return UserJson.from(userRepository.save(entity));
    }

    /*
    Update users in organization
    */
    @Override
    @Transactional
    public Collection<UserJson> updateUsersInOrganization(Long organizationId, Collection<Long> userIds) {
        userRepository.clearOrganizations(List.of(organizationId));

        if (CollectionUtils.isEmpty(userIds)) return List.of();

        userRepository.setOrganizationInUsers(organizationId, userIds);
        return userRepository.findAllById(userIds)
                .stream()
                .map(UserJson::from)
                .toList();
    }

    /*
    Return all users from database including his organization
    */
    @Override
    @Transactional(readOnly = true)
    public Collection<UserJson> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserJson::from)
                .toList();
    }

    /*
    Find user by id
    */
    @Override
    @Transactional(readOnly = true)
    public UserJson findById(Long id) {
        return userRepository.findById(id)
                .map(UserJson::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    /*
    Find all users that belongs to organization with the organizationId
    */
    @Override
    @Transactional(readOnly = true)
    public Collection<UserJson> findByOrganizationId(Long organizationId) {
        return userRepository.findAllByOrganizationOrganizationId(organizationId)
                .stream()
                .map(UserJson::from)
                .toList();
    }

    /*
    Delete all users with the ids
    */
    @Override
    @Transactional
    public void deleteAllById(Collection<Long> ids) {
        userRepository.deleteAllById(ids);
    }
}
