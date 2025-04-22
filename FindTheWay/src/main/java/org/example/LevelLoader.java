package org.example;

import Factories.CellFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LevelLoader {

    public static List<List<Cell>> loadFromJson(String filePath) {
        List<List<Cell>> field = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        CellFactory factory = new CellFactory();

        try {
            JsonNode root = mapper.readTree(new File(filePath));

            for (JsonNode rowNode : root) {
                List<Cell> row = new ArrayList<>();
                for (JsonNode cellNode : rowNode) {
                    int x = cellNode.get("x").asInt();
                    int y = cellNode.get("y").asInt();
                    boolean isEmpty = cellNode.get("isEmpty").asBoolean();
                    boolean isStart = cellNode.get("isStart").asBoolean();
                    boolean isEnd = cellNode.get("isEnd").asBoolean();

                    Direction directionEnter = null;
                    Direction directionExit = null;

                    if (cellNode.hasNonNull("directionEnter")) {
                        directionEnter = new Direction();
                        directionEnter.setDirection(org.example.DirectionEnum.valueOf(cellNode.get("directionEnter").asText()));
                    }

                    if (cellNode.hasNonNull("directionExit")) {
                        directionExit = new Direction();
                        directionExit.setDirection(org.example.DirectionEnum.valueOf(cellNode.get("directionExit").asText()));
                    }

                    Cell cell = factory.createCell(x, y, isEmpty, isStart, isEnd, directionEnter, directionExit);
                    row.add(cell);
                }
                field.add(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Ошибка при загрузке уровня: " + e.getMessage());
        }

        return field;
    }
}
