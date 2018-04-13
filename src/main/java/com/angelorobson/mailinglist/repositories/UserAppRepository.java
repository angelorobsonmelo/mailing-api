package com.angelorobson.mailinglist.repositories;


import com.angelorobson.mailinglist.entities.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface UserAppRepository extends JpaRepository<UserApp, Long> {

    UserApp findByEmail(String email);
}
