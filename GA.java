package test;
public class GA{
	public final int ChrNum = 2000;
	public String ipop[] = new String [ChrNum];
	public double bestfitness=-1000;
	public final int GENE=9;
	public String beststr = null;
	public int bestgenerations=1;
	public int generation=1;

	String[] initPop() {
		for(int i=0;i<ChrNum;i++) {
			int num = (int) (100*(Math.random() * 2));
		    String bs = Integer.toBinaryString(num);
		    if(bs.length()>=9) {
		    	bs=bs.substring(1, 9);
		    }else {
		    	for(int j = 2 ; j<=9-bs.length();j++) {
		    		bs="0"+bs;
		    	}
		    }
		    String as;
		    if(Math.random()<0.33 && num<100) {
		    	as="0"+bs;
		    }else {
		    	as="1"+bs;
		    }
	        ipop[i]=as;
		}
		return ipop;
	}

	private double[] calculatefitnessvalue(String str) {
		double a = Integer.parseInt(str.substring(1));
		if(str.substring(0,1)=="0") {
			a=-a;
		}
 
		double fitness = a*Math.sin(10*Math.PI)+2.0;
		
		double[] returns = { a , fitness };
		
		return returns;
 
}

	private void select() {
		double evals[] = new double[ChrNum]; 
		double p[] = new double[ChrNum]; 
		double q[] = new double[ChrNum]; 
		double F = 0; 
		for (int i = 0; i < ChrNum; i++) {
			evals[i] = calculatefitnessvalue(ipop[i])[1];
			if (evals[i] > bestfitness){  
				bestfitness = evals[i];
				bestgenerations = generation;
				beststr = ipop[i];
			}
 
			F = F + evals[i]; 
		}
		String[] ipop_m=ipop;
		for (int i = 0; i < ChrNum; i++) {
			double r = Math.random();
			String a = null;
			for(int j =0;j<ChrNum; j++) {
				if(r>=p[j]) {
					a=ipop_m[j];
				}
			}
			ipop[i]=a;
		}
	}
	private void cross() {
		String temp1, temp2;
		for (int i = 0; i < ChrNum; i++) {
			if (Math.random() < 0.60) {
				int pos = (int)(Math.random());
				if(pos>=8) {
					pos-=1;
				}else if(pos<=0) {
					pos+=1;
				}
				temp1 = ipop[i].substring(0, pos) + ipop[(i + 1) % ChrNum].substring(pos); 
				temp2 = ipop[(i + 1) % ChrNum].substring(0, pos) + ipop[i].substring(pos);
				ipop[i] = temp1;
				ipop[(i + 1) % ChrNum] = temp2;
			}
		}
	}
	/**
	 * 基因突变操作 1%基因变异
	 */
	private void mutation() {
		double p=Math.random();
		if(p<0.100) {
			int num=(int)(ChrNum*Math.random());
			String a=ipop[num];
			int pos=(int) Math.floor((GENE*Math.random())+1);
			if(pos<=8 && pos>2) {
				if(a.substring(pos-1,pos)=="0") {
				a=a.substring(1,pos)+"1"+a.substring(pos);
				}else {
				a=a.substring(1,pos)+"0"+a.substring(pos);
				}
			}else if(pos==2){
				if(a.substring(0,1)=="0") {
					a=a.substring(0,1)+"1"+a.substring(2);
				}else {
					a=a.substring(0,1)+"0"+a.substring(2);
					}		
			}else {
				if(a.substring(8)=="0") {
					a=a.substring(0,7)+"1";
				}else {
					a=a.substring(0,7)+"0";
					}	
			}
			}
		}
public static void main(String args[]) {
	 
	GA Tryer = new GA();
	Tryer.ipop = Tryer.initPop(); //产生初始种群
	String str = "";
	
	//迭代次数
		for (int i = 0; i < 10000; i++) {
			Tryer.select();
			Tryer.cross();
			Tryer.mutation();
			Tryer.generation = i;
		}
		
		double[] x = Tryer.calculatefitnessvalue(Tryer.beststr);
 
		str = "最大值" + Tryer.bestfitness;
 
		System.out.println(str);
}
}
