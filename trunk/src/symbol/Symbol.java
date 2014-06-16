package symbol;

import java.util.Hashtable;

public class Symbol {

	//variables
	private String name_;
	private static Hashtable<String, Symbol> dict = new Hashtable<String, Symbol>();
	
	
	//constructor
	public Symbol(String str) {
		name_ = str;
	}
	
	public static Symbol symbol(String str) {
		String aux = str.intern();
		Symbol s = (Symbol) dict.get(aux);
		if (s == null) {
			s = new Symbol(aux);
			dict.put(aux, s);
		}
		return s;
	}

	//getter
	public String toString() {
		return name_;
	}
}
