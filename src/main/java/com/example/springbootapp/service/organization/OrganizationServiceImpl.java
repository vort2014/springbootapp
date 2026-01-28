package com.example.springbootapp.service.organization;

import com.example.springbootapp.dao.organization.OrganizationEntity;
import com.example.springbootapp.dao.organization.OrganizationRepository;
import com.example.springbootapp.dao.user.UserRepository;
import com.example.springbootapp.web.controller.oranization.NewOrganizationJson;
import com.example.springbootapp.web.controller.oranization.OrganizationJson;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.Objects;

@AllArgsConstructor
@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;

    /*
    Save Organization and return the same with organizationId
     */
    @Override
    @Transactional
    public OrganizationJson save(NewOrganizationJson json) {
        OrganizationEntity entity = organizationRepository.save(OrganizationEntity.from(json));
        return OrganizationJson.from(entity);
    }

    /*
    Update organization
    */
    @Override
    @Transactional
    public OrganizationJson update(Long organizationId, OrganizationJson json) {
        // check parameters
        Objects.requireNonNull(organizationId, "organizationId shouldn't be null");
        if (!organizationId.equals(json.getOrganizationId())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "organizationId in url should be equal to organizationId in json body");

        OrganizationEntity entity = organizationRepository.findById(organizationId)
                .map(e -> e.updateFrom(json))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid organizationId in json body"));
        return OrganizationJson.from(organizationRepository.save(entity));
    }

    /*
    Return all Organizations from database
    */
    @Override
    @Transactional(readOnly = true)
    public Collection<OrganizationJson> findAll() {
        return organizationRepository.findAll()
                .stream()
                .map(OrganizationJson::from)
                .toList();
    }

    /*
    Find organization by id
     */
    @Override
    @Transactional(readOnly = true)
    public OrganizationJson findById(Long organizationId) {
        return organizationRepository.findById(organizationId)
                .map(OrganizationJson::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    /*
    Delete organizations with the given ids
    */
    @Override
    @Transactional
    public void deleteAllById(Collection<Long> ids) {
        userRepository.clearOrganizations(ids);
        organizationRepository.deleteAllById(ids);
    }
}
