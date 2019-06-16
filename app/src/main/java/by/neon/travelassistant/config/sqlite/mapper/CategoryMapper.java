package by.neon.travelassistant.config.sqlite.mapper;

import java.util.ArrayList;
import java.util.List;

import by.neon.travelassistant.config.sqlite.model.CategoryDb;
import by.neon.travelassistant.model.Category;

/**
 * Converts the category objects from db entity to model and back.
 */
public final class CategoryMapper extends BaseMapper<Category, CategoryDb> {
    /**
     * Translates the category title from english to russian.
     *
     * @param en the english title.
     * @return the russian translation.
     */
    public String toRu(String en) {
        switch (en) {
            case "need":
                return "самое необходимое";
            case "tourism":
                return "туризм";
            case "cycle":
                return "велотуризм";
            case "winter":
                return "зима";
            case "beach":
                return "пляж";
            case "business":
                return "командировка";
            case "swim":
                return "дайвинг";
            case "photo":
                return "фотография";
            case "fishing":
                return "рыбалка";
            case "hike":
                return "поход";
            case "wc":
                return "туалет";
            case "kids":
                return "дети";
            case "restaurant":
                return "ресторан";
            case "travel":
                return "путешествие";
            default:
                return null;
        }
    }

    /**
     * Translates the list of english titles to russian.
     *
     * @param en the list of english titles.
     * @return the list of russian translated titles.
     */
    public List<String> toRu(List<String> en) {
        List<String> list = new ArrayList<>();
        for (String s : en) {
            list.add(toRu(s));
        }
        return list;
    }

    /**
     * Converts the object from type {@link Category} to type {@link CategoryDb}.
     *
     * @param source the object to convert.
     * @return the converted object.
     */
    @Override
    public CategoryDb from(Category source) {
        CategoryDb categoryDb = new CategoryDb();
        categoryDb.setId(source.getId());
        categoryDb.setCategoryNameEn(source.getCategoryNameEn());
        categoryDb.setCategoryNameRu(source.getCategoryNameRu());
        return categoryDb;
    }

    /**
     * Converts the object from type {@link CategoryDb} to type {@link Category}.
     *
     * @param source the object to convert.
     * @return the converted object.
     */
    @Override
    public Category to(CategoryDb source) {
        Category category = new Category();
        category.setId(source.getId());
        category.setCategoryNameEn(source.getCategoryNameEn());
        category.setCategoryNameRu(source.getCategoryNameRu());
        return category;
    }
}
