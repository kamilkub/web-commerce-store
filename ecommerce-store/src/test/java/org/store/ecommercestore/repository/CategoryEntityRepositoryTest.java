package org.store.ecommercestore.repository;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.store.ecommercestore.model.CategoryEntity;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CategoryEntityRepositoryTest {

    @Autowired
    private CategoryEntityRepository categoryEntityRepository;

    @Autowired
    private ProductEntityRepository productEntityRepository;

    @BeforeAll
    public void init(){
        CategoryEntity beforeCategory = new CategoryEntity();
        beforeCategory.setCategoryName("beforeCategory");
        categoryEntityRepository.save(beforeCategory);
        productEntityRepository.deleteAll();
    }

    @Test
    @DisplayName("TEST :: adding category")
    void shouldReturnTrueIfCategoryHasBeenAddedToDatabase(){
        CategoryEntity testCategory = new CategoryEntity();
        testCategory.setCategoryName("testCategory");
        assertNotEquals(null, categoryEntityRepository.save(testCategory));
    }

    @Test
    @DisplayName("TEST :: get categories")
    void shouldReturnTrueIfCategoriesAmountIsEqualToOne(){
        List<CategoryEntity> categories = categoryEntityRepository.findAll();
        assertThat(categories.size(), equalTo(5));
    }

    @Test
    @DisplayName("TEST :: deleting category")
    void shouldReturnTrueIfCategoryHasBeenDeleted(){
        categoryEntityRepository.deleteById(3L);
        assertThat(categoryEntityRepository.findAll().size(), equalTo(5));
    }

    @Test
    @DisplayName("TEST :: update category")
    void shouldReturnTrueIfCategoryHasBeenUpdated(){
        CategoryEntity category = categoryEntityRepository.findById(2L).get();
        category.setCategoryName("updatedCategory");
        categoryEntityRepository.save(category);
        assertThat(categoryEntityRepository.findById(2L).get().getCategoryName(), equalTo("updatedCategory"));
    }




}
