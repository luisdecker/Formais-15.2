package formais152.Modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class InputOutput {
	
	static public Automato createAutomato(String location){
		
		return null;
	}
	
    static public Gramatica criarGramatica(String location){
    	BufferedReader br = null;
	
    	Gramatica gr = new Gramatica();
    	try {
    		br= new BufferedReader(new FileReader(location));
    	
    	    String fullline = br.readLine();

    	    ///inicio da criação
    	    while (fullline != null) {
    	       String line = fullline;

    	        if( line.contains("->") ){
    	        	
    	        	int position = line.indexOf("->");
    	        	String vn = line.substring(0, position);
    	        	 line= line.substring(position+2);
    	        	
    	        	vn=vn.trim();
    	        	do{
    	        		String vt= line.trim();
    	        		
    	        		if(line.contains("|")){
    	        			int nextPosition=line.indexOf("|");
    	        			
    	        			
    	        			vt= line.substring(0,nextPosition);
    	        			
    	        			line = line.substring( nextPosition +1 );	
    	        		}
    	        		vt = vt.trim();
    	        		//limpando *
    	        		if(vn.contains("*")){
    	        			vn= vn.substring(1);
    	        		}
    	        		
    	        		if(! (vn.length()==0 || vt.length()==0)){
    	        			gr.adicionaProducao(vn,vt);
    	        		}
    	        		
    	     	
    	        	}while(line.contains("|"));
 	
    	        }
    
    	        fullline =  br.readLine();
    	    }
   
    	    br.close();
    	} catch (IOException e) {		
			e.printStackTrace();
		} 
    	return gr;
    }
    
    static public Expressao criarExpressao(String location){
    	BufferedReader br = null;
    	
    	 String fullline= "";
    	try {
    		br= new BufferedReader(new FileReader(location));
    		fullline = br.readLine();
    		while(fullline != null){
    			if(fullline.length()!=0){
    				fullline=fullline.trim();
    				return new Expressao(fullline);
    			}
    		}
    	    

    	   
   
    	    br.close();
    	} catch (IOException e) {		
			e.printStackTrace();
		} 
    	return null;
    }

}
