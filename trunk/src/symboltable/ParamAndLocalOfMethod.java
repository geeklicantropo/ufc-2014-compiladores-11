package symboltable;

/* Nessa classes temos os parâmetros do método e suas 
 * variaveis locais. Os parametros do do método é representado por um objeto do tipo 
 * TableOfParam e as variáveis locais do método é um objeto do tipo TableOfLocal. 
 * Temos os métodos addParams e addLocals que tem a função de adicionar parametros e variaveis
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
