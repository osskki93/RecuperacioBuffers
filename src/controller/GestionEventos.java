package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import model.*;
import view.*;

public class GestionEventos {

	private GestionDatos model;
	private LaunchView view;
	private ActionListener actionListener_comparar, actionListener_buscar, actionListener_guardar, actionListener_buscarLibro, actionListener_listarLibros, actionListener_buscarPrefijos, actionListener_buscarAutor;

	public GestionEventos(GestionDatos model, LaunchView view) {
		this.model = model;
		this.view = view;
	}

	public void contol() {
		
		actionListener_buscarAutor = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				call_buscarAutor();
			}
		};
		view.getBtnBuscarAutor().addActionListener(actionListener_buscarAutor);
		
		actionListener_buscarPrefijos = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				call_buscarPrefijos();
			}
		};
		view.getBtnBuscarPrefijo().addActionListener(actionListener_buscarPrefijos);
		
		actionListener_guardar = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				call_guardarLibro();
			}
		};
		view.getGuardarLibro().addActionListener(actionListener_guardar);
		
		actionListener_buscarLibro = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				call_buscarLibro();
			}
		};
		view.getBuscarLibro().addActionListener(actionListener_buscarLibro);
		
		actionListener_comparar = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				// TODO: Llamar a la función call_compararContenido
				if(call_compararContenido()) {
					view.getTextArea().setText("Los Ficheros son iguales.");
				} else {
					view.getTextArea().setText("Los Ficheros son diferentes.");
				}
			}
		};
		view.getComparar().addActionListener(actionListener_comparar);

		actionListener_buscar = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				// TODO: Llamar a la función call_buscarPalabra
				view.getTextArea().setText(""+call_palabraMasLarga());
			}
		};
		view.getBuscar().addActionListener(actionListener_buscar);
		
		actionListener_listarLibros = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				call_listarLibros();
			}
		};
		view.getListarLibros().addActionListener(actionListener_listarLibros);
		
	}
	
	private void call_buscarAutor() {
		view.getTextArea().setText("");
		try {
			if(model.buscarAutor(view.getTxtAutor().getText()) == 0) {
				view.showError("No hay ningún libro de ese autor");
			} else {
				view.getTextArea().setText("Hay " + model.buscarAutor(view.getTxtAutor().getText()) + " del autor " + view.getTxtAutor().getText());
			}
			
		} catch (ClassNotFoundException | IOException e) {
			view.showError("Ha habido un error al listar los Libros");
		}
	}
	
	private void call_buscarPrefijos() {
		view.getTextArea().setText("");
		try {
			ArrayList<String> palabras = model.buscarPrefijos(view.getFichero1().getText(), view.getTxtPrefijo().getText());
			Iterator it = palabras.iterator();
			if(!palabras.isEmpty()) {
				while (it.hasNext()) {
					view.getTextArea().setText(view.getTextArea().getText() + it.next() + "\n");
				}
			} else {
				view.showError("0 palabras con el prefijo " + view.getTxtPrefijo().getText());
			}
		} catch (IOException e) {
			e.printStackTrace();
			view.showError("Error al abrir el fichero " + view.getFichero1().getText());
		}
		
	}

	private boolean call_compararContenido() {
		// TODO: Llamar a la función compararContenido de GestionDatos
		try {
			return model.compararContenido(view.getFichero1().getText(), view.getFichero2().getText());
		} catch (IOException e) {
			view.showError("HA HABIDO UN ERROR");
			return false;
		}
	}

	private int call_buscarPalabra() {

		try {
			return model.buscarPalabra(view.getFichero1().getText(), view.getPalabra().getText(), view.getPrimera().isSelected());
		} catch (IOException e) {
			view.showError("HA HABIDO UN ERROR");
			return 0;
		}
	}
	
	private int call_palabraMasLarga() {
		view.getTextArea().setText("");
		try {
			return model.palabraMasLarga(view.getFichero1().getText());
			view.getTextArea().setText("La palabra más larga de este fichero tiene los siguientes carácteres: " +model.palabraMasLarga(view.getFichero1().getText()));
		} catch (IOException e) {
			view.showError("HA HABIDO UN ERROR");
			return 0;
		}
	}
	
	private void call_guardarLibro() {
		Libro l1 = new Libro(Integer.parseInt(view.getTxtId().getText()), view.getTxtTitulo().getText(), view.getTxtAutor().getText(), Integer.parseInt(view.getTxtAnyo().getText()), view.getTxtEditor().getText(), Integer.parseInt(view.getTxtNumPags().getText()));
		try {
			model.guardarLibro(l1);
			view.getTextArea().setText("Libro Creado");
			limpiarCadenas();
		} catch (IOException e) {
			e.printStackTrace();
			view.showError("Error al guardar el libro.");
		}
	}
	
	private void call_buscarLibro() {
		try {
			Libro l1 = model.recuperarLibro(Integer.parseInt(view.getTxtIdBuscar().getText().trim()));
			view.getTextArea().setText("Identificador: " + l1.getId() + "\nTítulo: " + l1.getTitulo() + "\nAutor: " + l1.getAutor() + "\nAño: " + l1.getAnyo() + "\nEditor " + l1.getEditor() + "\nNumero de páginas: " + l1.getNumPags());
		} catch (NumberFormatException | ClassNotFoundException | IOException e) {
			view.showError("No se puede recuperar el libro.");
			e.printStackTrace();
		}
	}
	
	private void call_listarLibros() {
		view.getTextArea().setText(null);
		try {
			ArrayList<Libro> libros = model.listarLibros();
			Iterator it = libros.iterator();
			while(it.hasNext()) {
				Libro l1 = (Libro) it.next();
				view.getTextArea().setText(view.getTextArea().getText() + "Identificador: " + l1.getId() + "\nTítulo: " + l1.getTitulo() + "\nAutor: " + l1.getAutor() + "\nAño: " + l1.getAnyo() + "\nEditor " + l1.getEditor() + "\nNumero de páginas: " + l1.getNumPags());
				view.getTextArea().setText(view.getTextArea().getText() + "\n---------------------------------------------------\n");
			}
		} catch (ClassNotFoundException | IOException e) {
			view.showError("Error. No se han podido listar los libros");
			e.printStackTrace();
		}
	}
	
	private void limpiarCadenas() {
		view.getTxtId().setText(null);
		view.getTxtTitulo().setText(null);
		view.getTxtAutor().setText(null);
		view.getTxtAnyo().setText(null);
		view.getTxtEditor().setText(null);
		view.getTxtNumPags().setText(null);
	}

}