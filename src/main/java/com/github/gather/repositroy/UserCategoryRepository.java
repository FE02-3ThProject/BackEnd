package com.github.gather.repositroy;

import com.github.gather.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCategoryRepository extends JpaRepository<Category, Long> {


    Optional<Category> findById(Long id);}
