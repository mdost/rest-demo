package demo;

import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@EnableTransactionManagement
@Transactional
public class DogsRestController {
	
	//declare instance of repository
	@Autowired
	DogRepository dogsRepo;
	
	/*
	 * return all dogs stored in the database (Get)
	 */
	@RequestMapping(value = "/dogs", method = RequestMethod.GET)
	public @ResponseBody List<Dogs> getAllDogs(){
		return dogsRepo.findAll();
	}
	
	/*
	 * create a new dog in the database (Post request) 
	 * return 200 created.
	 */
	@RequestMapping(value = "/createDog", method = RequestMethod.POST)
	@ResponseStatus(value=HttpStatus.CREATED)
	public @ResponseBody Dogs getAllDogs(@RequestBody Dogs dog){
		return dogsRepo.saveAndFlush(dog);
	}
	
	/*
	 * Search by dog ID (Get) 
	 * return the found dog
	 */
	@RequestMapping(value = "/dogs/{id}", method = RequestMethod.GET)
	public @ResponseBody Dogs getAllDogs(@PathVariable("id") Long id){
		return dogsRepo.findByid(id);
	}
	
	/*
	 * Delete dog by ID (Get)
	 * return a list of dogs
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	@ResponseStatus(value=HttpStatus.OK)
	public @ResponseBody List<Dogs> deleteDogByID(@PathVariable("id") Long id){
		dogsRepo.deleteByid(id);
		return dogsRepo.findAll();
	}
	
	/*
	 * Update a dog that is stored in the database by its ID
	 * return a list of all dogs stored in the database
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseStatus(value=HttpStatus.OK)
	public @ResponseBody List<Dogs> updateDogByID(@RequestBody Dogs dog){
		dogsRepo.updateByID(dog.getId(), dog.getWeight(), dog.getHeartbeat(), dog.getTemperature(), dog.getName(), dog.getLat(), dog.getLon());
		return dogsRepo.findAll();
	}
	
	/*
	 *  Performs the k-mean clustering algorithm for dogs weight on the dataset that is available in the system.
	 * 	return 2D array of clustered dogs
	 */
	@RequestMapping(value = "/dogClusters", method = RequestMethod.GET)
	@ResponseStatus(value=HttpStatus.OK)
	public @ResponseBody Dogs[][] KMeansDogClusters(@RequestParam("k") int clusters){
		//get all dogs from the database
		List<Dogs> getDogs = dogsRepo.findAll();
				
		int length= getDogs.size();		//get # of element
		int minValue= getDogs.get(0).getWeight();		//initially set the minValue for weight
		int maxValue = getDogs.get(0).getWeight();		//initially set the maxValue for weight
				
		//initialize cluster array to -1
		Dogs[][] clusterArray = new Dogs[clusters][length];
		for(int q=0; q<clusters; q++){
			for(int y=0; y< length; y++){
				clusterArray[q][y]=null;
			}
		}
				
		//choose k initial cluster centers
		//find min and max value in the list of dogs
		for(Dogs i : getDogs){				
			if(i.getWeight() < minValue){
				minValue= i.getWeight();
			}
						
			if(i.getWeight() > maxValue){
				maxValue= i.getWeight();
			}
		}
					
		//generate k random cluster centers in the range of the list
		int[] k = new int[clusters];
		Random rand = new Random();
					
		for(int i=0; i<clusters; i++){
			k[i] = rand.nextInt((maxValue-minValue)+1);
		}
					
		//for each point assign it to the cluster with the nearest distance
		double center, center2;
					
		for(Dogs i: getDogs){
			//calculate the distance between the centroid (center) point choosen and each point.
//					double v = Math.pow(i.getWeight()-k[0], 2);
			center=  Math.abs(i.getWeight()-k[0]);
			int flag =0;	//initially flag set to cluster 0
						
			//check which centroid does the point in the dataset has closest distance.
			for(int n=1; n< k.length;n++){
//				double value = Math.pow(i.getWeight()-k[n], 2);
				center2=  Math.abs(i.getWeight()-k[n]);
						
				if(center2 < center){
					center= center2;
					flag=n;		//change flag if the point is closer to another cluster

				}
			}
					
			//each row in the array corresponds to a cluser, for example row 0 is cluster 0, row 1 is cluster 1, etc.
			//place each object in correct row according to its flag.
			for(int o=0; o<k.length; o++){
				if(flag == o){
				    for(int b=0; b< length;b++){
				    	if(clusterArray[o][b] == null){
				        	clusterArray[o][b]= i;
				        	break;
				        }
				    }
				}
			}
						
		}
		
		
		return clusterArray;
	}
	
}
