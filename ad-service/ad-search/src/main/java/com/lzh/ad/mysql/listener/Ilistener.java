package com.lzh.ad.mysql.listener;

import com.lzh.ad.mysql.dto.BinlogRowData;

/**
 * @author Li
 **/
public interface Ilistener {

	void register();

	void onEvent(BinlogRowData eventData);

}
