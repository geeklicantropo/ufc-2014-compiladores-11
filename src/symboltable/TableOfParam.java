package symboltable;

/* Nessa classe temos uma estrutura que vai guardar um parametro(Symbol) e seu devido
 * tipo(Type). Temos tambem os metodos de acesso a essa estrutura.
 */

import java.util.LinkedHashMap;
import syntaxtree.Type;
import symbol.Symbol;

public class TableOfParam {
	
	private LinkedHashMap<Symbol, Type> hashmapParamsTable;
	
	public TableOfParam() {
		hashmapParamsTable = new LinkedHashMap<Symbol, Type>();
	}

	//setter
	public void setHashmapParamsTable(LinkedHashMap<Symbol, Type> paramsTable) {
		
		this.hashmapParamsTable = paramsTable;
	}
	
	// getter
	public LinkedHashMap<Symbol, Type> getHashmapParamsTable() {
		
		return hashmapParamsTable;
	}

}
