package ar.com.ada.api.apiempleadas.services;

package ar.com.ada.api.empleadas.services;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.apiempleadas.entities.Categoria;
import ar.com.ada.api.apiempleadas.repos.CategoriaRepository;
import ar.com.ada.api.apiempleadas.repos.EmpleadaRepository;

@Service
public class CategoriaService {

    @Autowired
    CategoriaRepository repo;

    public void crearCategoria(Categoria categoria){
        repo.save(categoria);
    }

    public List<Categoria> traerCategorias(){
        return repo.findAll();
    }

    public Categoria buscarCategoria(Integer categoriaId){

        Optional<Categoria> resultado =  repo.findById(categoriaId);
        Categoria categoria = null;

        if (resultado.isPresent())
            categoria = resultado.get();

        return categoria;

    }

        public void eliminar(Integer id) {
        Categoria categoria = this.buscarCategoria(id);
        repo.delete(categoria);
  
    }

    
    public Categoria obtenerCategoriaConMinimoSueldo() {
        return traerCategorias().stream().min(Comparator.comparing(Categoria :: getSueldoBase)).get();
    }

    public List<String> obtenerNombresCategorias(){
        
        return this.traerCategorias().stream().map(cat -> cat.getNombre()).collect(Collectors.toList());

        
    }
}


