package com.angelorobson.mailinglist.repositories;

import com.angelorobson.mailinglist.entities.Function;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface FunctionRepository extends JpaRepository<Function, Long> {
}
