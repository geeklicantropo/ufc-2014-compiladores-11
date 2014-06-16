package symboltable;

/* Nessa classe, temos dois hashmaps. Um deles, tem o nome do m�todo(Symbol) e seu
 * determinado tipo(Type). Uma outra estrutura tem o nome do m�todo(Symbol) e seu 
 * determinado conjunto de vari�veis locais e parametros que � um objeto do tipo
 * ParamAndLocalOfMethod.
 */

import java.util.HashMap;
import syntaxtree.Type;
import symbol.Symbol;


public class TableOfMethod {
	
	private HashMap<Symbol, Type> hashmapMethodType;
	private HashMap<Symbol, ParamAndLocalOfMethod> hashmapMethodParamAndLocal;
	private String current_id;
	
	public TableOfMethod() {
		hashmapMethodType = new HashMap<Symbol, Type>();
		hashmapMethodParamAndLocal = new HashMap<Symbol, ParamAndLocalOfMethod>();
	}

	// getters & setters	
	public HashMap<Symbol, ParamAndLocalOfMethod> getHashmapMethodParamAndLocal() {
		
		return hashmapMethodParamAndLocal;
	}

	public void setHashmapMethodParamAndLocal(HashMap<Symbol, 
											  ParamAndLocalOfMethod> 
											  hashmapMethodPAL) {
		
		this.hashmapMethodParamAndLocal = hashmapMethodPAL;
	}
	
	public HashMap<Symbol, Type> getHashmapMethodType() {
		
		return hashmapMethodType;
	}

	public void setHashmapMethodType(HashMap<Symbol, Type> hashmapMethodsTable) {
		
		this.hashmapMethodType = hashmapMethodsTable;
	}

	public String getCurrentID() {
		
		return current_id;
	}

	public void setCurrentID(String id) {
		
		this.current_id = id;
	}
	
}
