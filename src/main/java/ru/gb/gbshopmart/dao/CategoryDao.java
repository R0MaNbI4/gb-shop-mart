package ru.gb.gbshopmart.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.gbshopmart.entity.Category;

public interface CategoryDao extends JpaRepository<Category, Long> {
}
