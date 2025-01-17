package com.vmware.portfolio.repositories;

import com.vmware.portfolio.models.TestConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface TestConfigRepository extends JpaRepository<TestConfig, Long> {

    @Query("Select o from TestConfig o where o.name=?1")
    TestConfig findByName(String name);
}
