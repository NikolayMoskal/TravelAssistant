package by.neon.travelassistant.config.sqlite.mapper;

import java.util.ArrayList;
import java.util.List;

import by.neon.travelassistant.config.sqlite.model.TypeDb;
import by.neon.travelassistant.model.Type;

/**
 * Converts the thing type objects from db entity to model and back.
 */
public final class TypeMapper extends BaseMapper<Type, TypeDb> {
    /**
     * Translates the thing type title from english to russian.
     *
     * @param en the english title.
     * @return the russian translation.
     */
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

    /**
     * Translates the thing type title from english to russian.
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
     * Converts the object from type {@link Type} to type {@link TypeDb}.
     *
     * @param source the object to convert.
     * @return the converted object.
     */
    @Override
    public TypeDb from(Type source) {
        TypeDb typeDb = new TypeDb();
        typeDb.setId(source.getId());
        typeDb.setTypeNameEn(source.getTypeEn());
        typeDb.setTypeNameRu(source.getTypeRu());
        return typeDb;
    }

    /**
     * Converts the object from type {@link TypeDb} to type {@link Type}.
     *
     * @param source the object to convert.
     * @return the converted object.
     */
    @Override
    public Type to(TypeDb source) {
        Type type = new Type();
        type.setId(source.getId());
        type.setTypeEn(source.getTypeNameEn());
        type.setTypeRu(source.getTypeNameRu());
        return type;
    }
}
