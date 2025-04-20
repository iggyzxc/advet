package com.macadev.advet.factory;

import com.macadev.advet.dto.request.UserRegistrationRequest;
import com.macadev.advet.model.User;

public interface UserFactory {
    User createUser(UserRegistrationRequest request);
}
