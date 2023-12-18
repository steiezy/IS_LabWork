package com.lab.airbnb.service;

import com.lab.airbnb.model.User;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {

    public boolean userHasPermission(User user, String userId) {
        // ||user.getRoles().contains(Role.ADMIN)
        return user.getUserId().equals(userId);
    }

    public boolean userHasCreatOrderPermission(User user) {
        return user.getRole().equals("1") || user.getRole().equals("0");
    }

    public boolean userHasLeaseOrderPermission(User user) {
        return user.getRole().equals("1") || user.getRole().equals("0");
    }
}