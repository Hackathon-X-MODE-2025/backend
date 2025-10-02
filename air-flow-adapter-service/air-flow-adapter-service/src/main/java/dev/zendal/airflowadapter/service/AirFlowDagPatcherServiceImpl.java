package dev.zendal.airflowadapter.service;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AirFlowDagPatcherServiceImpl implements AirFlowDagPatcherService {
    @Override
    public String insertDagId(String dagCode, String newDagId) {
        // 1. Проверяем именованный параметр dag_id="..."
        Pattern named = Pattern.compile("dag_id\\s*=\\s*['\\\"]([^'\"]+)['\\\"]");
        Matcher matcher = named.matcher(dagCode);

        if (matcher.find()) {
            // Заменяем существующий dag_id
            return matcher.replaceFirst("dag_id=\"" + newDagId + "\"");
        }

        // 2. Проверяем первый позиционный аргумент "something"
        Pattern positional = Pattern.compile("DAG\\s*\\(\\s*['\\\"]([^'\"]+)['\\\"]");
        matcher = positional.matcher(dagCode);

        if (matcher.find()) {
            // Заменяем позиционный аргумент на именованный dag_id
            return matcher.replaceFirst("DAG(dag_id=\"" + newDagId + "\"");
        }

        // 3. Если dag_id нет вообще — вставляем его первым аргументом
        return dagCode.replaceFirst("DAG\\s*\\(", "DAG(dag_id=\"" + newDagId + "\", ");
    }
}
