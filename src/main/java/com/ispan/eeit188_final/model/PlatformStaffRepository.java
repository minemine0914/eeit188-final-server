package com.ispan.eeit188_final.model;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PlatformStaffRepository extends JpaRepository<PlatformStaff, UUID> {

}
