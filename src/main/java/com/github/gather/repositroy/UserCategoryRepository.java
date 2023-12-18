package com.github.gather.repositroy;

import com.github.gather.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCategoryRepository extends JpaRepository<Category, Long> {
}
