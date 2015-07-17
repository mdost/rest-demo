package demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Dogs {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	private int weight;
	private int heartbeat;
	private int temperature;
	private double lat;
	private double lon;
	
	public Dogs(String name, int weight, int heartbeat, int temperature, double lat, double lon){
		super();
		this.name = name;
		this.weight =weight;
		this.heartbeat= heartbeat;
		this.temperature= temperature;
		this.lat=lat;
		this.lon=lon;
		
	}
	
	public Dogs(){
		
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public void setWeight(int weight){
		this.weight = weight;
	}
	
	public void setHeartbeat(int heartbeat){
		this.heartbeat = heartbeat;
	}
	
	public void setTemperature(int temp){
		this.temperature = temp;
	}
	
	public void setLat(double lat){
		this.lat = lat;
	}
	
	public void setLon(double lon){
		this.lon = lon;
	}
	
	public Long getId(){
		return this.id;
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getWeight(){
		return this.weight;
	}
	
	public int getHeartbeat(){
		return this.heartbeat;
	}
	
	public int getTemperature(){
		return this.temperature;
	}
	
	public double getLat(){
		return this.lat;
	}
	
	public double getLon(){
		return this.lon;
	}
}
