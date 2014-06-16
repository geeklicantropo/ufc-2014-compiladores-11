package visitor;

/* Nessa classe teremos um booleano que indica se houve algum erro na
 * checagem de tipos. Se tiver erro, ser� mostrado ao usu�rio.
 */

class ErrorMsg {

	boolean is_there_any_error;
	
	void printError(String msg) {
		is_there_any_error = true;
		System.out.println("[ERRO] " + msg);
	}
}
