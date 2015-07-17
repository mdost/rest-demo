package demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DogsCommandLineRunner implements CommandLineRunner{
	@Autowired
	DogRepository dogRepository;
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		List<Dogs> dog = dogRepository.findAll();
		for(Dogs i : dog){
			System.out.println(i.getName());
		}
	}

}
