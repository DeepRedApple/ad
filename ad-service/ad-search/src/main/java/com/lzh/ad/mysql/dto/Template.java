package com.lzh.ad.mysql.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Li
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Template {
	private String database;
	private List<JsonTable> tableList;

}
