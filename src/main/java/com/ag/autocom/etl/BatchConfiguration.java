package com.ag.autocom.etl;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.ag.autocom.model.City;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	String[] names = { "country", "name", "province", "latitude", "longitude" };

	@Bean
	public FlatFileItemReader<City> reader() {
		return new FlatFileItemReaderBuilder<City>()
				.name("cityItemReader")
				.resource(new ClassPathResource("CitiesOfCanada.csv"))
				.linesToSkip(0)
				.delimited()
				.names(names)
				.lineMapper(lineMapper())
				.fieldSetMapper(new BeanWrapperFieldSetMapper<City>() {
					{
						setTargetType(City.class);
					}
				}).build();
	}

	@Bean
	public LineMapper<City> lineMapper() {
		final DefaultLineMapper<City> defaultLineMapper = new DefaultLineMapper<>();
		final DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(",");

		lineTokenizer.setStrict(false);
		lineTokenizer.setNames(names);
		final CityFieldSetMapper fieldSetMapper = new CityFieldSetMapper();
		defaultLineMapper.setLineTokenizer(lineTokenizer);
		defaultLineMapper.setFieldSetMapper(fieldSetMapper);

		return defaultLineMapper;
	}

	@Bean
	public CityItemProcessor processor() {
		return new CityItemProcessor();
	}

	@Bean
	public JdbcBatchItemWriter<City> writer(DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<City>()
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
				.sql("INSERT INTO city (name, province, country, latitude, longitude) VALUES (:name, :province, :country, :latitude, :longitude)")
				.dataSource(dataSource)
				.build();
	}

	@Bean
	public Job importCityData(JobCompletionNotificationListener listener, Step step1) {
		return jobBuilderFactory
				.get("importCityData")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(step1)
				.end()
				.build();
	}

	@Bean
	public Step step1(JdbcBatchItemWriter<City> writer) {
		return stepBuilderFactory
				.get("step1").<City, City>chunk(100)
				.reader(reader())
				.processor(processor())
				.writer(writer)
				.build();
	}

}
