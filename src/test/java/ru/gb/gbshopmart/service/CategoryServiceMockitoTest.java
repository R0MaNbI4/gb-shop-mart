package ru.gb.gbshopmart.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import ru.gb.gbshopmart.dao.CategoryDao;
import ru.gb.gbshopmart.entity.Category;
import ru.gb.gbshopmart.web.dto.CategoryDto;
import ru.gb.gbshopmart.web.dto.mapper.CategoryMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceMockitoTest {

    @Mock
    CategoryDao categoryDao;

    @Mock
    CategoryMapper categoryMapper;

    @InjectMocks
    CategoryService categoryService;

    @Test
    void findAllCategoriesTest() {
        List<Category> categories = new ArrayList<>();
        categories.add(Category.builder()
                .id(1L)
                .title("SSD")
                .createdBy("user1")
                .createdDate(LocalDateTime.now())
                .lastModifiedBy("user1")
                .lastModifiedDate(LocalDateTime.now())
                .version(1)
                .build());
        categories.add(Category.builder()
                .id(1L)
                .title("HDD")
                .createdBy("user1")
                .createdDate(LocalDateTime.now())
                .lastModifiedBy("user1")
                .lastModifiedDate(LocalDateTime.now())
                .version(1)
                .build());

        given(categoryDao.findAll()).willReturn(categories);

        List<CategoryDto> categoryDtos = categoryService.findAll();

        then(categoryDao).should().findAll();
        assertEquals(categories.size(), categoryDtos.size());
    }

    @Test
    void saveCategoryTest() {
        Category categoryFromDao = Category.builder()
                .id(1L)
                .title("SSD")
                .createdBy("user1")
                .createdDate(LocalDateTime.now())
                .lastModifiedBy("user1")
                .lastModifiedDate(LocalDateTime.now())
                .version(1)
                .build();
        CategoryDto ssd = new CategoryDto(null, "SSD");
        given(categoryDao.save(any(Category.class))).willReturn(categoryFromDao);
        // Можно использовать willReturn(), но решили глянуть на will()
        given(categoryMapper.toCategory(any())).will(new ToCategory());
        given(categoryMapper.toCategoryDto(any())).will(new ToCategoryDto());

        CategoryDto returnedCategoryDto = categoryService.save(ssd);

        // Должен быть вызван метод save() с любым объектом типа Category
        then(categoryDao).should().save(any(Category.class));
        assertEquals(1L, returnedCategoryDto.getId());
    }
}

class ToCategory implements Answer<Category> {

    @Override
    public Category answer(InvocationOnMock invocation) throws Throwable {
        CategoryDto categoryDto = (CategoryDto) invocation.getArgument(0);
        if (categoryDto == null) {
            return null;
        }

        Category.CategoryBuilder category = Category.builder();

        category.id( categoryDto.getId());
        category.title(categoryDto.getTitle());

        return category.build();
    }
}

class ToCategoryDto implements Answer<CategoryDto> {

    @Override
    public CategoryDto answer(InvocationOnMock invocation) throws Throwable {
        Category category = (Category) invocation.getArgument(0);
        if (category == null) {
            return null;
        }

        CategoryDto.CategoryDtoBuilder categoryDto = CategoryDto.builder();

        categoryDto.id( category.getId());
        categoryDto.title(category.getTitle());

        return categoryDto.build();
    }
}