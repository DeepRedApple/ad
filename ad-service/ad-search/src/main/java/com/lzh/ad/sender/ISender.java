package com.lzh.ad.sender;

import com.lzh.ad.mysql.dto.MySqlRowData;

/**
 * @author Li
 **/
public interface ISender {
	void sender(MySqlRowData rowData);
}
