package formais152.Modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class InputOutput {
	
	static public Automato createAutomato(String location){
		
		return null;
	}
	 static private ArrayList<String> getParameters(String line2, String separator){
	    	String line = line2;
		    ArrayList<String> lista= new ArrayList<String>();
	    	do{
					String estate = line;
					if(line.contains(separator)){
						int pos=line.indexOf(separator);
						estate = line.substring(0,pos );
						line = line.substring( pos +1 );	
					}
					estate = estate.trim();
					
					lista.add(estate);
				}while(line.contains(separator));
	    	return lista;	
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
    	        	ArrayList<String> lista= getParameters(line,"|");
    	        	for(String vt: lista){
    	        		gr.adicionaProducao(vn, vt);
    	        	}    	        	
 
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
   
    static public Automato criarAutomato(String location){
    	 String line= "";
    	 Automato auto= new Automato();
     	try {
     		BufferedReader	br= new BufferedReader(new FileReader(location));
     		line = br.readLine();
     		
     		if(line != null){
     			if( !line.contains("Q") ) return null;
     			int start= line.indexOf('[');
     			int end= line.indexOf(']');
     			if( start==-1 || end == -1)return null;
     			
     			String par= line.substring(start+1, end);
     			
     			ArrayList<String> lista= getParameters(line,",");
	        	for(String estado: lista){
	        		auto.addEstado(estado);
	        	}    	 
     		}
     		line = br.readLine();
     		
     		if(line != null){
     			if( !line.contains("q0") ) return null;
     			int start= line.indexOf('=');
     		
     			if( start==-1)return null;
     			
     			String par= line.substring(start+1);
  
     			line= line.trim();
	        	auto.setEstadoInicial(line);
	        	
	           	 
     		}
     		
     		line = br.readLine();
     		if(line != null){
     			if( !line.contains("F") ) return null;
     			int start= line.indexOf('[');
     			int end= line.indexOf(']');
     			if( start==-1 || end == -1)return null;
     			
     			String par= line.substring(start+1, end);
     			
     			ArrayList<String> lista= getParameters(line,",");
	        	for(String estado: lista){
	        		auto.addEstadoFinal(estado);
	        	}    	 
     		}
     		line = br.readLine();
     		
     		//TODO leitura de parametros
     		while(line != null){
     			String sub= line;
     		
     		}
     		
     		
     		
     		

     	    br.close();
     	} catch (Exception e) {		
 			e.printStackTrace();
 		} 
     	return null;
    }

}
