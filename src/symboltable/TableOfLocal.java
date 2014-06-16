package symboltable;

/* Nessa classe temos uma estrutura para guardar uma variavel local(Symbol) e seu devido
 * tipo(Type). Temos tambem os metodos de acesso a essa estrutura.
 */

import java.util.HashMap;
import syntaxtree.Type;
import symbol.Symbol;

public class TableOfLocal {
	private HashMap<Symbol, Type> hashmapLocalsTable;
	
	public TableOfLocal() {
		hashmapLocalsTable = new HashMap<Symbol, Type>();
	}

	//Setters & getters

	public void setHashmapLocalsTable(HashMap<Symbol, Type> localsTable) {
		
		this.hashmapLocalsTable = localsTable;
	}
	
	public HashMap<Symbol, Type> getHashmapLocalsTable() {
		
		return hashmapLocalsTable;
	}

}
