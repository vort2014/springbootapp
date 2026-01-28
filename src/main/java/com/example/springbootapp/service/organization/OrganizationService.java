package com.example.springbootapp.service.organization;

import com.example.springbootapp.web.controller.oranization.NewOrganizationJson;
import com.example.springbootapp.web.controller.oranization.OrganizationJson;

import java.util.Collection;

public interface OrganizationService {

    Collection<OrganizationJson> findAll();

    OrganizationJson findById(Long organizationId);

    OrganizationJson update(Long organizationId, OrganizationJson json);

    OrganizationJson save(NewOrganizationJson json);

    void deleteAllById(Collection<Long> ids);
}
