package ru.gb.gbshopmart.web.dto.mapper;

import org.mapstruct.Mapper;
import ru.gb.gbshopmart.entity.Category;
import ru.gb.gbshopmart.web.dto.CategoryDto;

@Mapper
public interface CategoryMapper {

    Category toCategory(CategoryDto categoryDto);

    CategoryDto toCategoryDto(Category category);
}
