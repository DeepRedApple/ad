package com.lzh.ad.runner;

import com.lzh.ad.mysql.BinlogClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author Li
 **/
@Slf4j
@Component
public class BinlogRunner implements CommandLineRunner {

	@Autowired
	private BinlogClient client;

	@Override
	public void run(String... args) throws Exception {
		log.info("Coming in BinLogRunner...");
		client.connect();
	}
}
