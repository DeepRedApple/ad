package com.lzh.ad.mysql;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.lzh.ad.mysql.listener.AggregationListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Li
 **/
@Slf4j
@Component
public class BinlogClient {

	private BinaryLogClient client;

	@Autowired
	private BinlogConfig config;

	@Autowired
	private AggregationListener listener;

	public void connect() {
		new Thread(() -> {
			client = new BinaryLogClient(config.getHost(), config.getPort(), config.getUsername(), config.getPassword());

			if (!StringUtils.isEmpty(config.getBinlogName()) && !config.getPosition().equals(-1)) {
				client.setBinlogFilename(config.getBinlogName());
				client.setBinlogPosition(config.getPosition());
			}

			client.registerEventListener(listener);

			try {
				log.info("connecting to mysql start");
				client.connect();
				log.info("connecting to mysql done");
			} catch (IOException e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}

		}).start();
	}

	public void close() {
		try {
			client.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
