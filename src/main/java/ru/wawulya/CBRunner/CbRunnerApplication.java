package ru.wawulya.CBRunner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;
import ru.wawulya.CBRunner.core.Sheduler;

@SpringBootApplication
public class CbRunnerApplication {

	@Autowired Sheduler sheduler;

	public static void main(String[] args) {

		SpringApplication.run(CbRunnerApplication.class, args);

		/*ApplicationContext context =
				new AnnotationConfigApplicationContext(CbRunnerApplication.class);*/
		//displayAllBeans(context);

		/*Runner run = context.getBean(Runner.class);
		run.start();
*/
		/*Sheduler sheduler = new Sheduler();
		sheduler.start();*/
	}
	@Bean
	CommandLineRunner init() {
		return (args) -> {
			sheduler.initialize();
			sheduler.start();
		};
	}

	public static void displayAllBeans(ApplicationContext ctx) {
		String[] allBeanNames = ctx.getBeanDefinitionNames();
		System.out.println("Loaded beans into context :");

		for(String beanName : allBeanNames) {
			System.out.println(beanName);
		}
	}

}
