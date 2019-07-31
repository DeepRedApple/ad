package com.lzh.ad.mysql.dto;

import com.lzh.ad.mysql.constant.OpType;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author Li
 **/
@Data
public class ParseTemplate {

	private String database;

	private Map<String, TableTemplate> tableTemplateMap = new HashMap<>();

	public static ParseTemplate parse(Template template) {

		ParseTemplate parseTemplate = new ParseTemplate();
		parseTemplate.setDatabase(template.getDatabase());

		template.getTableList().forEach(table -> {
			String tableName = table.getTableName();
			Integer level = table.getLevel();

			TableTemplate tableTemplate = new TableTemplate();
			tableTemplate.setTableName(tableName);
			tableTemplate.setLevel(level.toString());
			parseTemplate.getTableTemplateMap().put(tableName, tableTemplate);

			Map<OpType, List<String>> opTypeFieldSetMap = tableTemplate.getOpTypeFieldSetMap();
			table.getInsert().forEach(column ->
				getAndCreateIfNeed(OpType.ADD, opTypeFieldSetMap, ArrayList::new).add(column.getName())
			);

			table.getUpdate().forEach(column ->
				getAndCreateIfNeed(OpType.ADD, opTypeFieldSetMap, ArrayList::new).add(column.getName())
			);

			table.getDelete().forEach(column ->
				getAndCreateIfNeed(OpType.ADD, opTypeFieldSetMap, ArrayList::new).add(column.getName())
			);

		});

		return parseTemplate;
	}

	private static <T, R> R getAndCreateIfNeed(T key, Map<T, R> map, Supplier<R> factroy) {
		return map.computeIfAbsent(key, k -> factroy.get());
	}

}
