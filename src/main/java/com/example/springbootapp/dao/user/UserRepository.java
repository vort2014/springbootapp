package com.example.springbootapp.dao.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /*
    Remove users from organizations with organizationIds
    */
    @Modifying
    @Query(value = """
        UPDATE UserEntity e
        SET e.organization = null
        WHERE e.organization.organizationId IN :organizationIds
    """)
    void clearOrganizations(@Param("organizationIds") Collection<Long> organizationIds);

    /*
    Add users with userIds to organization with organizationId
    */
    @Modifying
    @Query(value = """
        UPDATE UserEntity e
        SET e.organization.organizationId = :organizationId
        WHERE e.id IN :userIds
    """)
    void setOrganizationInUsers(@Param("organizationId") Long organizationId, @Param("userIds") Collection<Long> userIds);

    /*
    Find users who belong to organization with organizationId
    */
    Collection<UserEntity> findAllByOrganizationOrganizationId(Long organizationId);
}
