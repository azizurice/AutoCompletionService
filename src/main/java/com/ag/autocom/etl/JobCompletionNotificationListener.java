package com.ag.autocom.etl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

	private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

//	private final JdbcTemplate jdbcTemplate;
//
//	@Autowired
//	public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
//		this.jdbcTemplate = jdbcTemplate;
//	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("!!! BATCH JOB FINISHED! Importing city data completed successfully");

//			jdbcTemplate
//					.query("SELECT name, province, country, latitude, longitude FROM city",
//							(rs, row) -> new City(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
//									rs.getString(5)))
//					.forEach(city -> log.info("Found <" + city + "> in the database."));
		}

	}
}