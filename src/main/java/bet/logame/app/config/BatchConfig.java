package bet.logame.app.config;

import bet.logame.app.model.SisCassinoJogo;
import bet.logame.app.processor.ImageFileProcessor;
import bet.logame.app.reader.DirectoryItemReader;
import bet.logame.app.repository.SisCassinoJogoRepository;
import bet.logame.app.writer.ImageFileWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.File;

@Configuration
@EnableBatchProcessing
@Slf4j
public class BatchConfig {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private SisCassinoJogoRepository repository;

    @Bean
    public Job processImageJob() {
        return new JobBuilder("processImageJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        return new StepBuilder("step1", jobRepository)
                .<File, SisCassinoJogo>chunk(10, transactionManager)
                .reader(fileItemReader())
                .processor(fileItemProcessor())
                .writer(fileItemWriter())
                .build();
    }

    @Bean
    public ItemReader<File> fileItemReader() {
        return new DirectoryItemReader(new File("src/main/resources/image"));
    }

    @Bean
    public ItemProcessor<File, SisCassinoJogo> fileItemProcessor() {
        return new ImageFileProcessor(repository);
    }

    @Bean
    public ItemWriter<SisCassinoJogo> fileItemWriter() {
        return new ImageFileWriter();
    }

    @Bean
    public JobExecutionListener listener() {
        return new JobExecutionListener() {
            @Override
            public void beforeJob(org.springframework.batch.core.JobExecution jobExecution) {
                log.info("Job started");
            }

            @Override
            public void afterJob(org.springframework.batch.core.JobExecution jobExecution) {
                log.info("Job ended");
            }
        };
    }
}