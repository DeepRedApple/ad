package com.lzh.ad.mysql;

import com.alibaba.fastjson.JSON;
import com.lzh.ad.mysql.constant.OpType;
import com.lzh.ad.mysql.dto.ParseTemplate;
import com.lzh.ad.mysql.dto.TableTemplate;
import com.lzh.ad.mysql.dto.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * @author Li
 **/
@Slf4j
@Component
public class TemplateHolder {

	private ParseTemplate parseTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private String SQL_SCHEMA = "select table_schema, table_name, column_name, ordinal_position from " +
		" information_schema.columns where table_schema = ? and table_name = ?";

	@PostConstruct
	private void init() {
		loadJson("template.json");
	}

	public TableTemplate getTable(String tableName) {
		return parseTemplate.getTableTemplateMap().get(tableName);
	}

	private void loadJson(String path) {
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		InputStream inputStream = cl.getResourceAsStream(path);

		try {
			Template template = JSON.parseObject(inputStream, Charset.defaultCharset(), Template.class);
			this.parseTemplate = ParseTemplate.parse(template);
			loadMeta();
		} catch (IOException e) {
			log.error(e.getMessage());
			throw new RuntimeException("fail to parse json file");
		}
	}

	private void loadMeta() {
		for (Map.Entry<String, TableTemplate> entry : parseTemplate.getTableTemplateMap().entrySet()) {

		}
		parseTemplate.getTableTemplateMap().entrySet().forEach(entry -> {
			TableTemplate table = entry.getValue();

			List<String> updateFields = table.getOpTypeFieldSetMap().get(OpType.UPDATE);
			List<String> insertFields = table.getOpTypeFieldSetMap().get(OpType.ADD);
			List<String> deleteFields = table.getOpTypeFieldSetMap().get(OpType.DELETE);

			jdbcTemplate.query(SQL_SCHEMA, new Object[]{parseTemplate.getDatabase(), table.getTableName()},
				(rs, i) -> {
					int pos = rs.getInt("ORDINAL_POSITION");
					String colName = rs.getString("COLUMN_NAME");

					if ((null != updateFields && updateFields.contains(colName))
						|| (null != insertFields && insertFields.contains(colName))
						|| (null != deleteFields && deleteFields.contains(colName))) {
						table.getPosMap().put(pos - 1, colName);
					}
					return null;
				}
			);
		});

	}
}
