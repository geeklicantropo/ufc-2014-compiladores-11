package symboltable;

/* Nessa classe procuramos representar uma tabela com as classes do programa e 
 * suas respectivas TableForClass correspondentes. Os pares chaves/valor do hashmap são
 * as classes(chave), que são objetos Symbol e as devidas tabelas(valor) correspondentes, 
 * que são objetos do tipo TableForClass. As classes são adicionadas na symboltable
 * através do método addClass.
 * Temos tambem um HashMap para registrar a classe filha(symbol) e seu determinado pai(symbol)
 */

import java.util.HashMap;
import symbol.Symbol;

public class TableOfSymbol {
	
	private HashMap<Symbol, TableForClass> symbolTable;
	private HashMap<Symbol, Symbol> inheritanceTable; 
	

	public TableOfSymbol() {
		symbolTable = new HashMap<Symbol, TableForClass>();
		inheritanceTable = new HashMap<Symbol, Symbol>();
	}
	
	public TableForClass addClass(Symbol identifier) {
		
		if (!symbolTable.containsKey(identifier)) {
			TableForClass tableForClass = new TableForClass();
			tableForClass.setIdentifier(identifier.toString());
			symbolTable.put(identifier, tableForClass);
			return tableForClass;
			
		} 
		else {
			return null;
		}
	}
	
	public void addInheritance(Symbol class1, Symbol class2) {
		inheritanceTable.put(class1, class2);
	}
	
	// getters & setters
	
	public TableForClass getClass(Symbol name) {
		
		return symbolTable.get(name);
	}

	public HashMap<Symbol, Symbol> getHeritages() {
		
		return inheritanceTable;
	}

	public void setHeritages(HashMap<Symbol, Symbol> heritagesTable) {
		
		this.inheritanceTable = heritagesTable;
	}
	
	public HashMap<Symbol, TableForClass> getSymbolTable() {
		
		return symbolTable;
	}

	public void setSymbolTable(HashMap<Symbol, TableForClass> symbolTable) {
		
		this.symbolTable = symbolTable;
	}

	
}
