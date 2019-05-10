package by.neon.travelassistant.config.sqlite.mapper;

import java.util.ArrayList;
import java.util.List;

import by.neon.travelassistant.config.sqlite.model.GenderDb;
import by.neon.travelassistant.model.Gender;

public final class GenderMapper extends BaseMapper<Gender, GenderDb> {
    public String toRu(String en) {
        switch (en) {
            case "man": return "мужской";
            case "woman": return "женский";
            case "neutral": return "неизвестно";
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
    public GenderDb from(Gender source) {
        GenderDb genderDb = new GenderDb();
        genderDb.setId(source.getId());
        genderDb.setTypeEn(source.getGenderEn());
        genderDb.setTypeRu(source.getGenderRu());
        return genderDb;
    }

    @Override
    public Gender to(GenderDb source) {
        Gender gender = new Gender();
        gender.setId(source.getId());
        gender.setGenderEn(source.getTypeEn());
        gender.setGenderRu(source.getTypeRu());
        return gender;
    }
}
