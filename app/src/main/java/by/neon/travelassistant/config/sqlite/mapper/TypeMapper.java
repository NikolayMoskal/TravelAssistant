package by.neon.travelassistant.config.sqlite.mapper;

import java.util.ArrayList;
import java.util.List;

import by.neon.travelassistant.config.sqlite.model.TypeDb;
import by.neon.travelassistant.model.Type;

public final class TypeMapper extends BaseMapper<Type, TypeDb> {
    public String toRu(String en) {
        switch (en) {
            case "documents":
                return "документы";
            case "equipment":
                return "техника";
            case "comfort":
                return "комфорт";
            case "cosmetics":
                return "косметика";
            case "outerwear":
                return "верхняя одежда";
            case "casual":
                return "повседневная одежда";
            case "underwear":
                return "нижнее белье";
            case "accessoires":
                return "аксессуары";
            case "decorations":
                return "украшения";
            case "dresscode":
                return "выходная одежда";
            case "shoes":
                return "обувь";
            case "headgear":
                return "головные уборы";
            case "kids":
                return "детские вещи";
            case "medicine":
                return "медицина";
            default:
                return null;
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
    public TypeDb from(Type source) {
        TypeDb typeDb = new TypeDb();
        typeDb.setId(source.getId());
        typeDb.setTypeNameEn(source.getTypeEn());
        typeDb.setTypeNameRu(source.getTypeRu());
        return typeDb;
    }

    @Override
    public Type to(TypeDb source) {
        Type type = new Type();
        type.setId(source.getId());
        type.setTypeEn(source.getTypeNameEn());
        type.setTypeRu(source.getTypeNameRu());
        return type;
    }
}
