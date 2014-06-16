package symboltable;

/* Nessa classe temos uma tabela de atributos que é um objeto do tipo 
 * TableOfAttribute e uma tabela de métodos que é um objeto do tipo 
 * TableOfMethod. O metodos mais importantes são o addMethod que nada mais adiciona 
 * um método com seus respectivos parâmetros e variáveis locais em uma ParamAndLocalOfMethod, 
 * bem como seu tipo. 
 * Temos tambem a função addAttributesTable que vai criar um tabela de atributos 
 * e tambem registrar adicionar atributos
 */

import java.util.HashMap;
import symboltable.TableOfAttribute;
import syntaxtree.*;
import symbol.Symbol;


public class TableForClass {
	
	private TableOfAttribute tableOfAttribute;
	private TableOfMethod tableOfMethod;
	private String identifier;
    
	//constructor
	public TableForClass() {
		tableOfAttribute = new TableOfAttribute();
		tableOfMethod = new TableOfMethod();
	}
	
	public void addAttributesTable(Identifier identifier, Type type) {
		if (this.tableOfAttribute == null) {
			TableOfAttribute attributes_table = new TableOfAttribute();
			attributes_table.addAttribute(identifier, type);
			setFieldsTable(attributes_table);
		} else {
			tableOfAttribute.addAttribute(identifier, type);
		}
	}
	
	public TableOfMethod addMethod(Identifier identifier, Type type) {
		if (tableOfMethod == null) { //cria estruturas e depois salva
			TableOfMethod methodsTable = new TableOfMethod();
			HashMap<Symbol, Type> hashmapMethodType = new HashMap<Symbol, Type>();
			HashMap<Symbol, ParamAndLocalOfMethod> hashmapParamAndLocal = new HashMap<Symbol, ParamAndLocalOfMethod>();
			
			// se ainda esse metodo, adiciona com seu respectivo tipo
			if (!hashmapMethodType.containsKey(Symbol.symbol(identifier.s))) {
				hashmapMethodType.put(Symbol.symbol(identifier.s), type);
			} 
			
			//se ainda não esse metodo, adiciona ele e seus atributos e variaveis locais
			if (!hashmapParamAndLocal.containsKey(Symbol.symbol(identifier.s))) { 
				ParamAndLocalOfMethod paramAndLocalOfMethod = new ParamAndLocalOfMethod();
				hashmapParamAndLocal.put(Symbol.symbol(identifier.s), paramAndLocalOfMethod); // adiciona symbol e a methodTable correspondente
			} 
			
			// registra as informações
			methodsTable.setHashmapMethodType(hashmapMethodType);
			methodsTable.setHashmapMethodParamAndLocal(hashmapParamAndLocal);
			methodsTable.setCurrentID(identifier.s);
			setMethodsTable(methodsTable);
			
			return methodsTable;
		} else { //tableOfMethod != null
			// adiciona as informacoes do metodo
			tableOfMethod.getHashmapMethodType().put(Symbol.symbol(identifier.s), type);
			ParamAndLocalOfMethod paramAndLocalOfMethod = new ParamAndLocalOfMethod();
			tableOfMethod.getHashmapMethodParamAndLocal().put(Symbol.symbol(identifier.s), paramAndLocalOfMethod);
			tableOfMethod.setCurrentID(identifier.s);
			return this.tableOfMethod;
		}
	}
	
	// getters & setters
	
	public TableOfAttribute getFieldsTable() {
		
		return tableOfAttribute;
	}
	public void setFieldsTable(TableOfAttribute tableOfAttribute) {
		
		this.tableOfAttribute = tableOfAttribute;
	}
	public TableOfMethod getMethodsTable() {
		
		return tableOfMethod;
	}
	public void setMethodsTable(TableOfMethod tableOfMethod) {
		
		this.tableOfMethod = tableOfMethod;
	}
	
	public String getIdentifier() {
		
		return identifier;
	}

	public void setIdentifier(String identifier) {
		
		this.identifier = identifier;
	}
	
}
