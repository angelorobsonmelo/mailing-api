package com.angelorobson.mailinglist.services.impl;

import com.angelorobson.mailinglist.dtos.UserAppDto;
import com.angelorobson.mailinglist.entities.UserApp;
import com.angelorobson.mailinglist.repositories.UserAppRepository;
import com.angelorobson.mailinglist.services.UserAppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAppAppServiceImpl implements UserAppService {

  private static final Logger log = LoggerFactory.getLogger(UserAppAppServiceImpl.class);

  @Autowired
  private UserAppRepository userAppRepository;

  @Override
  public UserAppDto persist(UserApp userApp) {
    log.info("Persisting a UserApp {}", userApp);
    UserApp userAppReturned = userAppRepository.save(userApp);
    return convertUserAppToDto(userAppReturned);
  }

  @Override
  public Optional<UserApp> findByEmail(String email) {
    log.info("Finding a UserApp by Email: {}", email);
    return Optional.ofNullable(userAppRepository.findByEmail(email));
  }

  @Override
  public Optional<UserApp> findById(Long id) {
    log.info("Finding a UserApp by ID: {}", id);
    return Optional.ofNullable(userAppRepository.findOne(id));
  }

  @Override
  public Page<UserAppDto> findAll(PageRequest pageRequest) {
    log.info("Get all users {}", pageRequest);
    Page<UserApp> userAppPage = userAppRepository.findAll(pageRequest);

    return userAppPage.map(this::convertUserAppToDto);
  }

  @Override
  public UserAppDto edit(UserApp userApp) {
    log.info("Editing a UserApp {}", userApp);
    UserApp userAppReturned = userAppRepository.save(userApp);
    return convertUserAppToDto(userAppReturned);
  }

    @Override
    public void remove(Long id) {
        log.info("Removing a UserApp with {}", id);
        userAppRepository.delete(id);
    }

    private UserAppDto convertUserAppToDto(UserApp userApp) {
    UserAppDto userAppDto = new UserAppDto();
    userAppDto.setId(userApp.getId());
    userAppDto.setFirstName(userApp.getFirstName());
    userAppDto.setLastName(userApp.getLastName());
    userAppDto.setEmail(userApp.getEmail());

    return userAppDto;
  }
}
