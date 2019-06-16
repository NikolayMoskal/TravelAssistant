package by.neon.travelassistant.config.sqlite.mapper;

import java.util.ArrayList;
import java.util.List;

import by.neon.travelassistant.config.sqlite.model.TransportDb;
import by.neon.travelassistant.model.Transport;

/**
 * Converts the transport objects from db entity to model and back.
 */
public final class TransportMapper extends BaseMapper<Transport, TransportDb> {
    /**
     * Translates the transport title from english to russian.
     *
     * @param en the english title.
     * @return the russian translation.
     */
    public String toRu(String en) {
        switch (en) {
            case "bus":
                return "автобус";
            case "auto":
                return "автомобиль";
            case "ship":
                return "корабль";
            case "train":
                return "поезд";
            case "airplane":
                return "самолет";
            case "cycle":
                return "велосипед";
            default:
                return null;
        }
    }

    /**
     * Translates the transport title from english to russian.
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
     * Converts the object from type {@link Transport} to type {@link TransportDb}.
     *
     * @param source the object to convert.
     * @return the converted object.
     */
    @Override
    public TransportDb from(Transport source) {
        if (source == null) {
            return null;
        }

        TransportDb transportDb = new TransportDb();
        transportDb.setId(source.getId());
        transportDb.setNameEn(source.getNameEn());
        transportDb.setNameRu(source.getNameRu());
        transportDb.setPackWeight(source.getHandPackWeight());
        transportDb.setMaxWeight(source.getMaxWeight());
        return transportDb;
    }

    /**
     * Converts the object from type {@link TransportDb} to type {@link Transport}.
     *
     * @param source the object to convert.
     * @return the converted object.
     */
    @Override
    public Transport to(TransportDb source) {
        if (source == null) {
            return null;
        }

        Transport transport = new Transport();
        transport.setId(source.getId());
        transport.setNameEn(source.getNameEn());
        transport.setNameRu(source.getNameRu());
        transport.setHandPackWeight(source.getPackWeight());
        transport.setMaxWeight(source.getMaxWeight());
        return transport;
    }
}
