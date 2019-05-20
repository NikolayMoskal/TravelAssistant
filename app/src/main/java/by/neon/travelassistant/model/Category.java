package by.neon.travelassistant.model;

import java.util.Locale;

public class Category extends Entity {
    private String categoryNameEn;
    private String categoryNameRu;

    public String getCategoryNameEn() {
        return categoryNameEn;
    }

    public void setCategoryNameEn(String categoryNameEn) {
        this.categoryNameEn = categoryNameEn;
    }

    public String getCategoryNameRu() {
        return categoryNameRu;
    }

    public void setCategoryNameRu(String categoryNameRu) {
        this.categoryNameRu = categoryNameRu;
    }

    public String getCategoryName() {
        return Locale.getDefault().getLanguage().equals("ru")
                ? categoryNameRu
                : categoryNameEn;
    }
}
