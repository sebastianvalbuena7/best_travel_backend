package com.sebastian.bestTravel.infrastructure.services;

import com.sebastian.bestTravel.domain.repositories.mongo.AppUserRepository;
import com.sebastian.bestTravel.infrastructure.abstract_services.ModifiedUserService;
import com.sebastian.bestTravel.util.exceptions.IdNotFoundException;
import com.sebastian.bestTravel.util.exceptions.UsernameNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class AddUserService implements ModifiedUserService {
    private final AppUserRepository appUserRepository;

    @Override
    public Map<String, Boolean> enabled(String username) {
        var user = appUserRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(COLLECTION_NAME));
        user.setEnabled(!user.isEnabled());
        var userSaved = appUserRepository.save(user);
        return Collections.singletonMap(userSaved.getUsername(), userSaved.isEnabled());
    }

    @Override
    public Map<String, Set<String>> addRole(String username, String role) {
        var user = appUserRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(COLLECTION_NAME));
        user.getRole().getGrantedAuthorities().add(role);
        var userSaved = appUserRepository.save(user);
        var authorities = userSaved.getRole().getGrantedAuthorities();
        return Collections.singletonMap(userSaved.getUsername(), authorities);
    }

    @Override
    public Map<String, Set<String>> removeRole(String username, String role) {
        var user = appUserRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(COLLECTION_NAME));
        user.getRole().getGrantedAuthorities().remove(role);
        var userSaved = appUserRepository.save(user);
        var authorities = userSaved.getRole().getGrantedAuthorities();
        return Collections.singletonMap(userSaved.getUsername(), authorities);
    }

    private static final String COLLECTION_NAME = "app_user";

    @Transactional(readOnly = true)
    public void loadUserByUsername(String username) {
        var user = appUserRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(COLLECTION_NAME));
    }
}