package symboltable;

/* Nessa classes temos os par�metros do m�todo e suas 
 * variaveis locais. Os parametros do do m�todo � representado por um objeto do tipo 
 * TableOfParam e as vari�veis locais do m�todo � um objeto do tipo TableOfLocal. 
 * Temos os m�todos addParams e addLocals que tem a fun��o de adicionar parametros e variaveis
 * locais no TableOfParam e no TableOfLocal, respectivamente.
 */

import syntaxtree.Identifier;
import symbol.Symbol;
import syntaxtree.Type;


public class ParamAndLocalOfMethod {

	private TableOfLocal tableOfLocal;
	private TableOfParam tableOfParam;

	
	public ParamAndLocalOfMethod () {
		
		tableOfLocal = new TableOfLocal();
		tableOfParam = new TableOfParam();
	}
	
	public void addLocals(Identifier identifier, Type type) {
		
		if (!tableOfLocal.getHashmapLocalsTable().containsKey(Symbol.symbol(identifier.s.toString()))) {
			tableOfLocal.getHashmapLocalsTable().put(Symbol.symbol(identifier.s), type);
		} 
	}
	
	public void addParams(Identifier identifier, Type type) {
		
		if (!tableOfParam.getHashmapParamsTable().containsKey(Symbol.symbol(identifier.s))) {
			tableOfParam.getHashmapParamsTable().put(Symbol.symbol(identifier.s), type);
		}
		
	}
	
	
	// getters & setters
	
	public TableOfLocal getLocalsTable() {
		
		return tableOfLocal;
		
	}
	public void setLocalsTable(TableOfLocal tableOfLocal) {
		
		this.tableOfLocal = tableOfLocal;
		
	}
	
	public TableOfParam getParamsTable() {
		
		return tableOfParam;
		
	}
	public void setParamsTable(TableOfParam tableOfParam) {
		
		this.tableOfParam = tableOfParam;
		
	}
	
}
