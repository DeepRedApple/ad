package com.lzh.ad.mysql.listener;

import com.github.shyiko.mysql.binlog.event.EventType;
import com.lzh.ad.mysql.constant.Constant;
import com.lzh.ad.mysql.constant.OpType;
import com.lzh.ad.mysql.dto.BinlogRowData;
import com.lzh.ad.mysql.dto.MySqlRowData;
import com.lzh.ad.mysql.dto.TableTemplate;
import com.lzh.ad.sender.ISender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Li
 **/
@Slf4j
@Component
public class IncrementListener implements Ilistener {

	@Resource(name = "indexSender")
	public ISender sender;

	@Autowired
	private AggregationListener aggregationListener;

	@Override
	@PostConstruct
	public void register() {

		log.info("IncrementListener register db and table info");

		Constant.table2Db.forEach((k, v) -> aggregationListener.register(v, k, this));
	}

	@Override
	public void onEvent(BinlogRowData eventData) {

		TableTemplate table = eventData.getTable();
		EventType eventType = eventData.getEventType();

		MySqlRowData rowData = new MySqlRowData();
		rowData.setTableName(table.getTableName());
		rowData.setLevel(table.getLevel());
		OpType type = OpType.to(eventType);
		rowData.setType(type);

		List<String> fieldList = table.getOpTypeFieldSetMap().get(type);
		if (null == fieldList) {
			log.warn("{} not support for {}", type, table.getTableName());
			return;
		}

		eventData.getAfter().forEach(map -> {
			Map<String, String> afterMap = new HashMap<>();

			map.entrySet().forEach(entry -> {
				String colName = entry.getKey();
				String colValue = entry.getValue();
				afterMap.put(colName, colValue);
			});

			rowData.getFieldValueMap().add(afterMap);
		});

		sender.sender(rowData);
	}
}
