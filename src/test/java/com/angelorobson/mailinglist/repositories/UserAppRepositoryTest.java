package com.angelorobson.mailinglist.repositories;

import com.angelorobson.mailinglist.entities.UserApp;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.angelorobson.mailinglist.builders.UserAppBuilder.oneUserWithNameJoao;
import static com.angelorobson.mailinglist.builders.UserAppBuilder.oneUserWithNameManoel;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserAppRepositoryTest {


    @Autowired
    private UserAppRepository repository;

    private UserApp manoel;
    private UserApp joao;

    @Before
    public void setUp() {
         joao = oneUserWithNameJoao().build();
         manoel = oneUserWithNameManoel().build();

        this.repository.save(asList(joao, manoel));
    }

    @After
    public void tearDown() {
        this.repository.deleteAll();
    }

    @Test
    public void it_should_return_users(){
        List<UserApp> userAppListReturned = repository.findAll();

        assertThat(userAppListReturned.size(), is(equalTo(2)));
    }

    @Test
    public void it_should_find_by_email(){
         UserApp userAppReturned = repository.findByEmail(manoel.getEmail());

        assertEquals(manoel, userAppReturned);
    }

    @Test
    public void it_should_find_by_id(){
         UserApp userAppReturned = repository.findOne(joao.getId());

        assertEquals(userAppReturned, joao);
    }


}
