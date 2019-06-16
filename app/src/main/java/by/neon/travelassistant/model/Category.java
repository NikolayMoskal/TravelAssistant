package by.neon.travelassistant.model;

import java.util.Locale;

/**
 * The thing category model.
 */
public class Category extends Entity {
    /**
     * The en-US name of category.
     */
    private String categoryNameEn;
    /**
     * The ru-RU name of category.
     */
    private String categoryNameRu;

    /**
     * Gets the en-US name of category.
     *
     * @return the name.
     */
    public String getCategoryNameEn() {
        return categoryNameEn;
    }

    /**
     * Sets the en-US name of category.
     *
     * @param categoryNameEn the name to set.
     */
    public void setCategoryNameEn(String categoryNameEn) {
        this.categoryNameEn = categoryNameEn;
    }

    /**
     * Gets the ru-RU name of category.
     *
     * @return the name.
     */
    public String getCategoryNameRu() {
        return categoryNameRu;
    }

    /**
     * Sets the ru-RU name of category.
     *
     * @param categoryNameRu the name to set.
     */
    public void setCategoryNameRu(String categoryNameRu) {
        this.categoryNameRu = categoryNameRu;
    }

    /**
     * Gets the name based on current locale. If current locale is not defined in application
     * then returns in en-US locale.
     *
     * @return the name.
     */
    public String getCategoryName() {
        return Locale.getDefault().getLanguage().equals("ru")
                ? categoryNameRu
                : categoryNameEn;
    }
}
