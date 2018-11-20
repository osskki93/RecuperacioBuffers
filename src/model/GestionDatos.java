package model;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class GestionDatos {

	public GestionDatos() {

	}

	private FileReader crearFr(String fichero) throws IOException {
		FileReader f1 = new FileReader(fichero);
		return f1;
	}
	
	private void cerrarFr(FileReader f1) throws IOException {
		f1.close();
	}
	
	//TODO: Implementa una función para cerrar ficheros
	
	public boolean compararContenido (String fichero1, String fichero2) throws IOException  {
		
		FileReader f1 = crearFr(fichero1);
		FileReader f2 = crearFr(fichero2);
		
		int a, b;
		
		// Comparamos carácter a carácter los dos ficheros.
		while (((a=f1.read()) != -1) && ((b=f2.read()) != -1)) {
			if (a != b) {
				return false;
			}
		}
		// Confirmamos que ya no podemos leer más de ninguno de los ficheros.
		if (((a=f1.read()) == -1 ) && ((b=f2.read()) == -1)) {
			cerrarFr(f1);
			cerrarFr(f2);
			return true;
		} else {
			cerrarFr(f1);
			cerrarFr(f2);
			return false;
		}
		
	}
	
	public int buscarPalabra (String fichero1, String palabra, boolean primera_aparicion) throws IOException{
		FileReader f1 = crearFr(fichero1);
		LineNumberReader b1 = new LineNumberReader(f1);
		
		boolean aparece = false;
		int primera=0, ultima=0;
		String cadena = b1.readLine();
		
		while (cadena != null) {
			if (cadena.equals(palabra)) {			
				if (!aparece) {
					primera = b1.getLineNumber();
					ultima = primera;
					aparece = true;
				} else {
					ultima = b1.getLineNumber();
				}
			}
			cadena = b1.readLine();
		}
		if (!aparece) {
			cerrarFr(f1);
			return 0;
		} else {
			if (primera_aparicion) {
				cerrarFr(f1);
				return primera;
			} else {
				cerrarFr(f1);
				return ultima;
			}
		}
	}
	
	public int palabraMasLarga (String fichero1) throws IOException{
		
		FileReader f1 = crearFr(fichero1);
		LineNumberReader b1 = new LineNumberReader(f1);
		String cadena = b1.readLine();
		int numCaracteres = 0;
		numCaracteres = cadena.length();
		while(cadena != null && cadena.length() > numCaracteres) {
			
			numCaracteres = cadena.length();
	
		}		
		return numCaracteres;
		
	}
	
	public boolean guardarLibro(Libro libro) throws IOException {
		ObjectOutputStream out = null;
		out = new ObjectOutputStream(new FileOutputStream("Libros\\"+libro.getId()+".txt"));		
		out.writeObject(libro);
		cerrar(out);
		return true;		
	}
	
	public Libro recuperarLibro(int id) throws FileNotFoundException, IOException, ClassNotFoundException {
		Libro libro = null;
		ObjectInputStream in = null;
		in = new ObjectInputStream (new FileInputStream("Libros\\"+ id + ".txt"));
		libro = (Libro) in.readObject();
		cerrar(in);
		return libro;
	}
	
	public ArrayList<Libro> listarLibros() throws FileNotFoundException, IOException, ClassNotFoundException {
		Libro libro = null;
		ObjectInputStream in = null;
		ArrayList<Libro> libros= new ArrayList<Libro>();
		File dir = new File("Libros\\");
		String[] ficheros = dir.list();
		
		if (ficheros != null) {
			for (int i=0; i<ficheros.length; i++) {
				in = new ObjectInputStream (new FileInputStream("Libros\\" + ficheros[i]));
				libro = (Libro) in.readObject();
				libros.add(libro);
			}
		}
		return libros;
	}
	
	public ArrayList<String> buscarPrefijos(String fichero, String prefijo) throws IOException {
		FileReader f1 = crearFr(fichero);
		BufferedReader bf1 = new BufferedReader(f1);
		String palabra;
		ArrayList<String> palabras = new ArrayList<String>();
		boolean iguales;
		
		while((palabra = bf1.readLine()) != null) {
			System.out.println(palabra);
			iguales = true;
			for(int i=0; i<prefijo.length(); i++) {
				if(prefijo.charAt(i) != palabra.charAt(i)) {
					iguales = false;
				}
			}
			if(iguales) {
				palabras.add(palabra);
			}
		}
		cerrarFr(f1);
		return palabras;
	}
	
	public int buscarAutor(String autor) throws FileNotFoundException, ClassNotFoundException, IOException {
		Libro l1 = null;
		ArrayList<Libro> libros = listarLibros();
		ArrayList<Libro> librosAutor = new ArrayList<Libro>();
		Iterator it = libros.iterator();
		while(it.hasNext()) {
			l1 = (Libro) it.next();
			if(l1.getAutor().compareToIgnoreCase(autor) == 0) {
				librosAutor.add(l1);
			}
		}
		return librosAutor.size();
	}
	
	private boolean cerrar (Closeable IO) {
		try {
			IO.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

}