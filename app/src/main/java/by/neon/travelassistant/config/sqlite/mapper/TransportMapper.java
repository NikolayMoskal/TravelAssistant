package by.neon.travelassistant.config.sqlite.mapper;

import java.util.ArrayList;
import java.util.List;

import by.neon.travelassistant.config.sqlite.model.TransportDb;
import by.neon.travelassistant.model.Transport;

public final class TransportMapper extends BaseMapper<Transport, TransportDb> {
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

    public List<String> toRu(List<String> en) {
        List<String> list = new ArrayList<>();
        for (String s : en) {
            list.add(toRu(s));
        }
        return list;
    }

    @Override
    public TransportDb from(Transport source) {
        TransportDb transportDb = new TransportDb();
        transportDb.setId(source.getId());
        transportDb.setNameEn(source.getNameEn());
        transportDb.setNameRu(source.getNameRu());
        transportDb.setPackWeight(source.getPackWeight());
        return transportDb;
    }

    @Override
    public Transport to(TransportDb source) {
        Transport transport = new Transport();
        transport.setId(source.getId());
        transport.setNameEn(source.getNameEn());
        transport.setNameRu(source.getNameRu());
        transport.setPackWeight(source.getPackWeight());
        return transport;
    }
}
