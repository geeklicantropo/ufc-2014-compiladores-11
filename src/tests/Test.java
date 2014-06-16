package tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import lexicalAnalysis.*;
import syntaxtree.*;
import visitor.*;

public class Test {
	
	public static void main(String[] args) throws ParseException, FileNotFoundException {
		
		File arq = new File("sampleMiniJavaPrograms/Factorial.java");
		FileInputStream file = new FileInputStream(arq);
		new Parser(file);
		System.out.println("\n\n>> Analise lexica e semantica: << \n");
		Program program = Parser.Program();
		program.accept(new PrettyPrintVisitor());
		System.out.println("\nPrograma MiniJava parseado com sucesso! \n\n");
		SymbolTableVisitor symbolTableVisitor = new SymbolTableVisitor();
		System.out.println(">> Tabela de simbolos e Checagem de tipos: << \n");
		program.accept(symbolTableVisitor);
	}
}
