package ru.gb.gbshopmart.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.gb.gbshopmart.config.ShopConfig;
import ru.gb.gbshopmart.entity.Category;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@Import(ShopConfig.class)
public class CategoryDaoTest {

    private static String CATEGORY_TITLE = "SSD";

    @Autowired
    CategoryDao categoryDao;

    @Test
    public void saveTest() {
        Category category = Category.builder().title(CATEGORY_TITLE).build();

        Category savedCategory = categoryDao.save(category);

        assertAll(
                () -> assertEquals(1L, savedCategory.getId()),
                () -> assertEquals(CATEGORY_TITLE, savedCategory.getTitle()),
                () -> assertEquals(0, savedCategory.getVersion()),
                () -> assertEquals("User", savedCategory.getCreatedBy()),
                () -> assertEquals("User", savedCategory.getLastModifiedBy()),
                () -> assertNotNull(savedCategory.getCreatedDate()),
                () -> assertNotNull(savedCategory.getLastModifiedDate())
        );
    }
}
