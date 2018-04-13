package com.angelorobson.mailinglist.services;

import com.angelorobson.mailinglist.dtos.UserAppDto;
import com.angelorobson.mailinglist.dtos.UserAppSaveDto;
import com.angelorobson.mailinglist.entities.UserApp;
import com.angelorobson.mailinglist.repositories.UserAppRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserAppServiceTest {

  @MockBean
  private UserAppRepository userAppRepository;

  @InjectMocks
  @Autowired
  private UserAppService userAppService;

  private UserApp userApp;
  private UserAppSaveDto userAppSaveDto;

  private static final String EMAIL = "admin@gmail.com";
  private static final Long ID = 1L;

  @Before
  public void setUp() {
    userApp = new UserApp();
    userAppSaveDto = new UserAppSaveDto();
  }

  @Test
  public void it_should_persist_user() {
    when(userAppRepository.save(any(UserApp.class))).thenReturn(new UserApp());
    UserAppDto userAppReturnedDto = userAppService.persist(this.userApp);

    verify(userAppRepository).save(eq(this.userApp));
    assertNotNull(userAppReturnedDto);
  }

  @Test
  public void it_should_find_by_email() {
    when(userAppRepository.findByEmail(anyString())).thenReturn(new UserApp());
    Optional<UserApp> userAppReturned = userAppService.findByEmail(EMAIL);

    verify(userAppRepository).findByEmail(eq(EMAIL));
    assertTrue(userAppReturned.isPresent());
  }

  @Test
  public void it_should_find_by_id() {
    when(userAppRepository.findOne(anyLong())).thenReturn(new UserApp());
    Optional<UserApp> userAppReturned = userAppService.findById(ID);

    verify(userAppRepository).findOne(eq(ID));
    assertTrue(userAppReturned.isPresent());
  }

  @Test
  public void it_should_get_all_users() {
    when(userAppRepository.findAll(any(PageRequest.class))).thenReturn(new PageImpl<>
            (new ArrayList<>()));

    PageRequest pageRequest = new PageRequest(0, 10);
    Page<UserAppDto> userAppPageReturned = userAppService.findAll(pageRequest);

    verify(userAppRepository).findAll(eq(pageRequest));
    assertNotNull(userAppPageReturned);
  }

  @Test
  public void it_should_edit_user() {
    when(userAppRepository.save(any(UserApp.class))).thenReturn(new UserApp());
    UserAppDto userAppReturnedDto = userAppService.edit(this.userApp);

    verify(userAppRepository).save(eq(this.userApp));
    assertNotNull(userAppReturnedDto);
  }

  @Test
  public void it_should_remove_user() {
    userAppService.remove(ID);

    verify(userAppRepository).delete(eq(ID));
  }

}
