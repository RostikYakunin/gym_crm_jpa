package com.crm.repositories;

import com.crm.models.users.User;

public interface UserRepo<T extends User> extends BaseRepo<T> {
    boolean isUserNameExists(String username);
}
