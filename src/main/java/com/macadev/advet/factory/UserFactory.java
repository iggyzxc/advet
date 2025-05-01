package com.macadev.advet.factory;

import com.macadev.advet.dto.request.user.UserRegistrationRequestDto;
import com.macadev.advet.model.User;

public interface UserFactory {
    User createUser(UserRegistrationRequestDto request);
}
