package model;

import java.io.Serializable;

public class Libro implements Serializable {
	private int id;
	private String titulo;
	private String autor;
	private int anyo;
	private String editor;
	private int numPags;
	
	public Libro() {
		
	}
	
	public Libro(int id, String titulo, String autor, int anyo, String editor, int numPags) {
		this.id = id;
		titulo = titulo.trim();
		this.titulo = titulo;
		this.autor = autor;
		this.anyo = anyo;
		this.editor = editor;
		this.numPags = numPags;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		titulo = titulo.trim();
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public int getAnyo() {
		return anyo;
	}

	public void setAnyo(int anyo) {
		this.anyo = anyo;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public int getNumPags() {
		return numPags;
	}

	public void setNumPags(int numPags) {
		this.numPags = numPags;
	}
}