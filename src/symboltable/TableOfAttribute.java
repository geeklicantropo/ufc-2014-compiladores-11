package symboltable;

/* Nessa classe temos uma estrutura responsável por guardar os atributos de uma determinda
 * classe. Temos uma estrutura que guarda um atributo(Symbol) e seu determinado tipo(Type).
 * temos também metodos de acesso à essa estrutura, bem como um método AddAttribute
 * para adicionar atributos.
 */

import java.util.HashMap;
import syntaxtree.*;
import symbol.Symbol;


public class TableOfAttribute {
	private HashMap<Symbol, Type> hashmapFieldsTable;
	
	public TableOfAttribute() {
		hashmapFieldsTable = new HashMap<Symbol, Type>();
	}

	public void addAttribute(Identifier identifier, Type type) {
		if (!hashmapFieldsTable.containsKey(Symbol.symbol(identifier.s.toString()))) {
			hashmapFieldsTable.put(Symbol.symbol(identifier.s.toString()), type);
		}
	}
	
	//getters & setters
	
	public HashMap<Symbol, Type> getHashmapFieldsTable() {
		
		return hashmapFieldsTable;
	}

	public void setHashmapFieldsTable(HashMap<Symbol, Type> hashmapFieldsTable) {
		
		this.hashmapFieldsTable = hashmapFieldsTable;
	}
	
}
