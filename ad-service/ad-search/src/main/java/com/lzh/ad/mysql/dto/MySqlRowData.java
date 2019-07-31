package com.lzh.ad.mysql.dto;

import com.lzh.ad.mysql.constant.OpType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Li
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MySqlRowData {

	private String tableName;

	private String level;

	private OpType type;

	private List<Map<String, String>> fieldValueMap = new ArrayList<>();

}