package ca.mcgill.ecse420.a2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class BakeryLock implements Lock{

	private static volatile boolean[] flag;
	private static volatile int[] number;

	public BakeryLock(int threadSize){
		flag = new boolean[threadSize];
		number = new int[threadSize];
		
		for(int i=0; i<threadSize; i++){
			flag[i] = false;
			number[i] = 0;
		}
	}
	
	public void lock(int id) {
		int max = 0;
		this.flag[id] = true;
		max = this.getMax(this.number) + 1;
		number[id] = max;
		System.out.println("In lock id:" + id + ", max: " + max);
		this.flag[id] = false;
		
		for(int i = 0; i < number.length; i++){
			if(i == id){
				continue;
			}
			//Wait for thread that's being set
			while(this.flag[i]){}
			//Wait for thread with smaller number or with smaller pid
			while(this.number[i] != 0 && (this.number[i] < this.number[id] || this.number[i] == this.number[id] && i < id)){}
		}
		
	}
	
	
	public void unlock(int id) {
		number[id] = 0;
	}
	
	private int getMax(int[] list){
		int max = 0;
		for(int i=0; i<list.length; i++){
			if(list[i] > max){
				max = list[i];
			}
		}
		return max;
	}



	@Override
	public void lock() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void unlock() {
		// TODO Auto-generated method stub
		
	}
}
