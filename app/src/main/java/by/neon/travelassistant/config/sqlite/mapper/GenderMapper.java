package by.neon.travelassistant.config.sqlite.mapper;

import java.util.ArrayList;
import java.util.List;

import by.neon.travelassistant.config.sqlite.model.GenderDb;
import by.neon.travelassistant.model.Gender;

/**
 * Converts the gender objects from db entity to model and back.
 */
public final class GenderMapper extends BaseMapper<Gender, GenderDb> {
    /**
     * Translates the gender title from english to russian.
     *
     * @param en the english title.
     * @return the russian translation.
     */
    public String toRu(String en) {
        switch (en) {
            case "man":
                return "мужской";
            case "woman":
                return "женский";
            case "neutral":
                return "нейтральный";
            default:
                return null;
        }
    }

    /**
     * Translates the gender title from english to russian.
     *
     * @param en the english title.
     * @return the russian translation.
     */
    public List<String> toRu(List<String> en) {
        List<String> list = new ArrayList<>();
        for (String s : en) {
            list.add(toRu(s));
        }
        return list;
    }

    /**
     * Converts the object from type {@link Gender} to type {@link GenderDb}.
     *
     * @param source the object to convert.
     * @return the converted object.
     */
    @Override
    public GenderDb from(Gender source) {
        GenderDb genderDb = new GenderDb();
        genderDb.setId(source.getId());
        genderDb.setTypeEn(source.getGenderEn());
        genderDb.setTypeRu(source.getGenderRu());
        return genderDb;
    }

    /**
     * Converts the object from type {@link GenderDb} to type {@link Gender}.
     *
     * @param source the object to convert.
     * @return the converted object.
     */
    @Override
    public Gender to(GenderDb source) {
        Gender gender = new Gender();
        gender.setId(source.getId());
        gender.setGenderEn(source.getTypeEn());
        gender.setGenderRu(source.getTypeRu());
        return gender;
    }
}
