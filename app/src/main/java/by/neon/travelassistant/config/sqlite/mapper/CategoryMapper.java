package by.neon.travelassistant.config.sqlite.mapper;

import java.util.ArrayList;
import java.util.List;

import by.neon.travelassistant.config.sqlite.model.CategoryDb;
import by.neon.travelassistant.model.Category;

public final class CategoryMapper extends BaseMapper<Category, CategoryDb> {
    public String toRu(String en) {
        switch (en) {
            case "need": return "самое необходимое";
            case "tourism": return "туризм";
            case "cycle": return "велотуризм";
            case "winter": return "зима";
            case "beach": return "пляж";
            case "business": return "командировка";
            case "swim": return "дайвинг";
            case "photo": return "фотография";
            case "fishing": return "рыбалка";
            case "hike": return "поход";
            case "wc": return "туалет";
            case "kids": return "дети";
            case "restaurant": return "ресторан";
            case "travel": return "путешествие";
            default: return null;
        }
    }

    public List<String> toRu(List<String> en) {
        List<String> list = new ArrayList<>();
        for (String s : en) {
            list.add(toRu(s));
        }
        return list;
    }

    @Override
    public CategoryDb from(Category source) {
        CategoryDb categoryDb = new CategoryDb();
        categoryDb.setId(source.getId());
        categoryDb.setCategoryNameEn(source.getCategoryNameEn());
        categoryDb.setCategoryNameRu(source.getCategoryNameRu());
        return categoryDb;
    }

    @Override
    public Category to(CategoryDb source) {
        Category category = new Category();
        category.setId(source.getId());
        category.setCategoryNameEn(source.getCategoryNameEn());
        category.setCategoryNameRu(source.getCategoryNameRu());
        return category;
    }
}
