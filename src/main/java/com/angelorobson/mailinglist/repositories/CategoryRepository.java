package com.angelorobson.mailinglist.repositories;

import com.angelorobson.mailinglist.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
