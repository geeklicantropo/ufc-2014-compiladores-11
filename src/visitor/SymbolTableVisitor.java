package visitor;

import java.util.Map;

import symboltable.*;
import syntaxtree.*;
import symbol.*;


public class SymbolTableVisitor implements TypeVisitor {

	private String id;
	private TableForClass classTable = null;
	private TableOfMethod methodsTable = null;
	private TableOfSymbol symbolTable = new TableOfSymbol();
	
	// getters & setters
	
		public TableOfSymbol getSymbolTable() {
			
			return symbolTable;
		}

		public void setSymbolTable(TableOfSymbol symbolTable) {
			
			this.symbolTable = symbolTable;
		}

		public TableOfMethod getMethodsTable() {
			
			return methodsTable;
		}

		public void setMethodsTable(TableOfMethod methodsTable) {
			
			this.methodsTable = methodsTable;
		}

		public String getId() {
			
			return id;
		}

		public void setId(String id) {
			
			this.id = id;
		}

	
	
	@Override
	public Type visit(Program n) {
		n.m.accept(this);
		
		// para cada classe em ClassDeclList
		for ( int i = 0; i < n.cl.size(); i++ ) {
	        n.cl.elementAt(i).accept(this);
	    }
		//imprime a estruturação
		print();
		
		return null;
	}
	
	@Override
	public Type visit(MainClass n) {
		
		n.i1.accept(this); //indentificador1
		// adiciona a classe na TableForClass
		classTable = symbolTable.addClass(Symbol.symbol(n.i1.toString()));
		//indentificador2
		n.i2.accept(this);
		//statemente
	    n.s.accept(this);
	   
		return null;
	}

	@Override
	public Type visit(ClassDeclSimple n) {
		
		n.i.accept(this);
		//adiciona na TableForClass a classe declarada
		classTable = symbolTable.addClass(Symbol.symbol(n.i.toString()));
		
		// percorre a lista das variaveis declaradas VarDeclList
		for ( int i = 0; i < n.vl.size(); i++ ) {
			//aqui é adicionado os atributos da classe
			classTable.addAttributesTable(n.vl.elementAt(i).i, n.vl.elementAt(i).t);
	        n.vl.elementAt(i).accept(this);
	    }
		// percorre sobre a lista de Métodos declarados MethodDeclList
	    for ( int i = 0; i < n.ml.size(); i++ ) {
	    	// aqui é adicionado os metodos conrespondentes a classe
	    	setMethodsTable(classTable.addMethod(n.ml.elementAt(i).i, n.ml.elementAt(i).t)); // add methodsTable
	        n.ml.elementAt(i).accept(this);
	    }
	    
		return null;
	}

	@Override
	public Type visit(ClassDeclExtends n) {
		//Classe filhas
		//identificadors
		n.i.accept(this);
		//adiciona na TableForClass a classe declarada
		classTable = symbolTable.addClass(Symbol.symbol(n.i.toString())); 
		//Classe pai
		//identificador
		n.j.accept(this);
		//adiciona a classe de herança
	    symbolTable.addInheritance(Symbol.symbol(n.i.toString()), Symbol.symbol(n.j.toString()));
	    setUpTheInheritance();
	    
	    // percorre a lista das variaveis declaradas VarDeclList
	    for ( int i = 0; i < n.vl.size(); i++ ) {
	    	// adiciona os atributos da classe
	    	classTable.addAttributesTable(n.vl.elementAt(i).i, n.vl.elementAt(i).t); // add fieldsTable
	        n.vl.elementAt(i).accept(this);
	    }
	    // percorre sobre a lista de Métodos declarados MethodDeclList
	    for ( int i = 0; i < n.ml.size(); i++ ) {
	    	// adiciona os metodos da classe
	    	setMethodsTable(classTable.addMethod(n.ml.elementAt(i).i, n.ml.elementAt(i).t)); // add methodsTable
	        n.ml.elementAt(i).accept(this);
	    }
	    
		return null;
	}

	@Override
	public Type visit(VarDecl n) {
		//tipo
		n.t.accept(this);
		// identificador
	    n.i.accept(this);
	    
		return null;
	}

	@Override
	public Type visit(MethodDecl n) {
		
		// tipo de retorno do metodo
		n.t.accept(this);
		// identificador
	    n.i.accept(this);
	    
	    // percorre sobre a lista de parametro do determinado metodo FormalList
	    for ( int i = 0; i < n.fl.size(); i++ ) {
	    	
	    	addParams(n.fl.elementAt(i).i, n.fl.elementAt(i).t);
	        n.fl.elementAt(i).accept(this);
	    }
	    
	    // percorre sobre a lista de variaveis declaradas locais VarDeclList
	    for ( int i = 0; i < n.vl.size(); i++ ) {
	    	
	    	addLocals(n.vl.elementAt(i).i, n.vl.elementAt(i).t);
	        n.vl.elementAt(i).accept(this);
	    }
	    
	    //percorre sobre a lista de statements declarados no metodo
	    for ( int i = 0; i < n.sl.size(); i++ ) { // Statement
	    	
	        n.sl.elementAt(i).accept(this);
	    }
	    n.e.accept(this); // exp
		return null;
	}

	@Override
	public Type visit(Formal n) {
		n.t.accept(this); // tipo
	    n.i.accept(this); // identificador
	    addParams(n.i, n.t);
		return null;
	}

	@Override
	public Type visit(IntArrayType n) {
		return n;
	}

	@Override
	public Type visit(BooleanType n) {
		return n;
	}

	@Override
	public Type visit(IntegerType n) {
		return n;
	}

	@Override
	public Type visit(IdentifierType n) {
		return n;
	}

	@Override
	public Type visit(Block n) {
		
		//percorre sobre a lista de statements declaradoss
		for ( int i = 0; i < n.sl.size(); i++ ) {
			
	        n.sl.elementAt(i).accept(this);
	    }
		return null;
	}

//	TODO(Lazaro) completar essa sessão
	
	@Override
	public Type visit(If n) {
		n.e.accept(this);
	    n.s1.accept(this);
	    n.s2.accept(this);
		return null;
	}

	@Override
	public Type visit(While n) {
		n.e.accept(this);
	    n.s.accept(this);
		return null;
	}

	@Override
	public Type visit(Print n) {
		n.e.accept(this);
		return null;
	}

	@Override
	public Type visit(Assign n) {
		n.i.accept(this);
	    n.e.accept(this);
		return null;
	}

	@Override
	public Type visit(ArrayAssign n) {
		n.i.accept(this);
	    n.e1.accept(this);
	    n.e2.accept(this);
		return null;
	}

	@Override
	public Type visit(And n) {
		n.e1.accept(this);
	    n.e2.accept(this);
		return null;
	}

	@Override
	public Type visit(LessThan n) {
		n.e1.accept(this);
	    n.e2.accept(this);
		return null;
	}

	@Override
	public Type visit(Plus n) {
		n.e1.accept(this);
	    n.e2.accept(this);
		return null;
	}

	@Override
	public Type visit(Minus n) {
		n.e1.accept(this);
	    n.e2.accept(this);
		return null;
	}

	@Override
	public Type visit(Times n) {
		n.e1.accept(this);
	    n.e2.accept(this);
		return null;
	}

	@Override
	public Type visit(ArrayLookup n) {
		n.e1.accept(this);
	    n.e2.accept(this);
		return null;
	}

	@Override
	public Type visit(ArrayLength n) {
		n.e.accept(this);
		return null;
	}

	@Override
	public Type visit(Call n) {
		n.e.accept(this);
	    n.i.accept(this);
	    for ( int i = 0; i < n.el.size(); i++ ) {
	        n.el.elementAt(i).accept(this);
	    }
		return null;
	}

	@Override
	public Type visit(IntegerLiteral n) {
		return null;
	}

	@Override
	public Type visit(True n) {
		return null;
	}

	@Override
	public Type visit(False n) {
		return null;
	}

	@Override
	public Type visit(IdentifierExp n) {
		return null;
	}

	@Override
	public Type visit(This n) {
		return null;
	}

	@Override
	public Type visit(NewArray n) {
		n.e.accept(this);
		return null;
	}

	@Override
	public Type visit(NewObject n) {
		return null;
	}

	@Override
	public Type visit(Not n) {
		n.e.accept(this);
		return null;
	}

	@Override
	public Type visit(Identifier n) {
		setId(n.s);
		return new IdentifierType(n.s);
	}
	
	//faz toda a configuracao de heranca aquis
	private void setUpTheInheritance() {
		
		for (Map.Entry<Symbol, Symbol> hTable : symbolTable.getHeritages().entrySet()) {
			TableForClass c1; // classe filha
			TableForClass c2; // classe pai
	
			c1 = symbolTable.getClass(hTable.getKey());
			c2 = symbolTable.getClass(hTable.getValue());
			
			c1.getFieldsTable().getHashmapFieldsTable().putAll(c2.getFieldsTable().getHashmapFieldsTable());
			//adiciona os metodos para que a classen  filha tenha acesso
			c1.getMethodsTable().getHashmapMethodType().putAll(c2.getMethodsTable().getHashmapMethodType());
			//adiciona os paramentros e as variaveis locais
			c1.getMethodsTable().getHashmapMethodParamAndLocal().putAll(c2.getMethodsTable().getHashmapMethodParamAndLocal());
		}
	}
	
	//uncao geral para adicionar parametros
	private void addParams(Identifier identifier, Type type) {
		
		if (classTable.getMethodsTable() != null) { 
			classTable.getMethodsTable().getHashmapMethodParamAndLocal().get(
					Symbol.symbol(classTable.getMethodsTable().getCurrentID())).addParams(
							identifier, type);	
	    }
	}
	
	//funcao geral para adicionar variaveis locais
	private void addLocals(Identifier identifier, Type type) {
		
		if (classTable.getMethodsTable() != null) { 
	    	classTable.getMethodsTable().getHashmapMethodParamAndLocal().get(
	    			Symbol.symbol(classTable.getMethodsTable().getCurrentID())).addLocals(
	    					identifier, type);
	    }
	}
	
	// funcao para printar a estrutura do programa baseado na estrutura de dados construida
	private void print() {
		for (Map.Entry<Symbol, TableForClass> sbTable : 
			symbolTable.getSymbolTable().entrySet()) {
			System.out.println(">> Classe: " + sbTable.getKey().toString());
			if (sbTable.getValue().getFieldsTable() != null) {
				System.out.println("  Campos:");
				for (Map.Entry<Symbol, Type> fieldsTable : 
					sbTable.getValue().getFieldsTable().getHashmapFieldsTable().entrySet()) {
					System.out.println("    Simbolo: " + fieldsTable.getKey().toString());
					System.out.println("    Tipo: " + fieldsTable.getValue().toString());
				}
			}
			
			if (sbTable.getValue().getMethodsTable() != null) {
				System.out.println("  Metodos:");
				for (Map.Entry<Symbol, Type> methodsTable :
					sbTable.getValue().getMethodsTable().getHashmapMethodType().entrySet()) {
					System.out.println("    Simbolo: " + methodsTable.getKey().toString());
					System.out.println("    Tipo: " + methodsTable.getValue().toString());
						if (sbTable.getValue().getMethodsTable().getHashmapMethodParamAndLocal().containsKey(
								methodsTable.getKey())) {
							ParamAndLocalOfMethod methodTable = sbTable.getValue().getMethodsTable().getHashmapMethodParamAndLocal().get(
									methodsTable.getKey());
							
							if (methodTable.getParamsTable() != null) {
								System.out.println("      Parametros: ");
								for (Map.Entry<Symbol, Type> paramsTable : methodTable.getParamsTable().getHashmapParamsTable().entrySet()) {
									System.out.println("        Simbolo: " + paramsTable.getKey().toString());
									System.out.println("        Tipo: " + paramsTable.getValue().toString());
								}
							}
							
							if (methodTable.getLocalsTable() != null) {
								System.out.println("      Variaveis locais: ");
								for (Map.Entry<Symbol, Type> localsTable :
									methodTable.getLocalsTable().getHashmapLocalsTable().entrySet()) {
									System.out.println("        Simbolo: " + localsTable.getKey().toString());
									System.out.println("        Tipo: " + localsTable.getValue().toString());
								}
							}
						}
				}
			}
		}
	}
	
	
}
