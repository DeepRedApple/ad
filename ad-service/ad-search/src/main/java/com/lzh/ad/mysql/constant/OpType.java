package com.lzh.ad.mysql.constant;

import com.github.shyiko.mysql.binlog.event.EventType;

/**
 * @author Li
 **/
public enum  OpType {

	ADD,
	UPDATE,
	DELETE,
	OTHER;

	public static OpType to(EventType type) {
		switch (type) {
			case EXT_WRITE_ROWS:
				return ADD;
			case EXT_UPDATE_ROWS:
				return UPDATE;
			case EXT_DELETE_ROWS:
				return DELETE;
			default:
				return OTHER;
		}
	}
}
