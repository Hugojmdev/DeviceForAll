package com.hgo_soft.device_for_all.service;

import com.hgo_soft.device_for_all.entity.Role;
import java.util.List;

public interface RoleService {
    List<Role> findAll();
    Role findById(Long id);
    Role save(Role entity);
    void deleteById(Long id);
}
