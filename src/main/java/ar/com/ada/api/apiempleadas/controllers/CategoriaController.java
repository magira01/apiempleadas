package ar.com.ada.api.apiempleadas.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.apiempleadas.entities.Categoria;
import ar.com.ada.api.apiempleadas.models.response.GenericResponse;
import ar.com.ada.api.apiempleadas.services.CategoriaService;

@RestController
public class CategoriaController {

    @Autowired
    private CategoriaService service;


    @PostMapping("/categorias") //primer endponit
    public ResponseEntity<?> crearCategoria(@RequestBody Categoria categoria){
        service.crearCategoria(categoria);

        GenericResponse r = new GenericResponse();
        r.isOk = true;
        r.id = categoria.getCategoriaId();
        r.message = "La categoria fue creada con exito";

        return ResponseEntity.ok(r); //devuelvo un response entity con el valor esperado = respuesta/estatus
    }




    @GetMapping("/categorias")
    public ResponseEntity<List<Categoria>> traerCategorias(){
        return ResponseEntity.ok(service.traerCategorias());
    }

    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<GenericResponse> eliminar(@PathVariable Integer id){

        Categoria categoria = service.buscarCategoria(id);
        service.eliminar(id);
        GenericResponse respuesta = new GenericResponse();

        respuesta.isOk = true;
        respuesta.id = categoria.getCategoriaId();
        respuesta.message = "Categoria eliminada con exito.";

        return ResponseEntity.ok(respuesta);
    }
  

    @GetMapping("/categorias/minimo-sueldo")
    public ResponseEntity<Categoria> obtenerCategoriaConMinimoSueldo() {
        return ResponseEntity.ok(service.obtenerCategoriaConMinimoSueldo());
    }

    @GetMapping("/categorias/nombres")
    public ResponseEntity<List<String>> obtenerNombresCategorias() {
        return ResponseEntity.ok(service.obtenerNombresCategorias());
    }
  

}
