package service;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import domain.Adage;

public class Adages {

	private static CopyOnWriteArrayList<Adage> adages;
	private static AtomicInteger id;
	
	static {
		String[] aphormisms = {
				"What can be shown cannot be said.",
				"If a lion could talk, we could not understand him",
				"Philosophy is a battle against the bewitchment of our intellegence by means of language",
				"Ambition is the death of thought",
				"The limits of my language mean the limits of my world"
		};
		adages = new CopyOnWriteArrayList<Adage>();
		id = new AtomicInteger();
		for (String str : aphormisms){
			add(str);
		}
	}
	
	public static CopyOnWriteArrayList<Adage> getList(){
		return adages;
	}
	
	public static Adage find(int id){
		Adage adage = null;
		for (Adage a : adages){
			if (a.getId() == id){
				adage = a;
				break;
			}
		}
		return adage;
	}
	
	public static void remove(Adage adage){}
	
	public static void add(String words){
		int localId = id.incrementAndGet();
		Adage adage = new Adage();
		adage.setWords(words);
		adage.setId(localId);
		adages.add(adage);
	}
}
