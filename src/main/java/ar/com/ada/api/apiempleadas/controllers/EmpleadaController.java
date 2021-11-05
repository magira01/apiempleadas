package ar.com.ada.api.apiempleadas.controllers;

package ar.com.ada.api.apiempleadas.controllers;

import java.math.BigDecimal;
import java.util.*;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.apiempleadas.entities.Categoria;
import ar.com.ada.api.apiempleadas.entities.Empleada;
import ar.com.ada.api.apiempleadas.entities.Empleada.EstadoEmpleadaEnum;
import ar.com.ada.api.apiempleadas.models.request.*;
import ar.com.ada.api.apiempleadas.models.response.GenericResponse;
import ar.com.ada.api.apiempleadas.services.CategoriaService;
import ar.com.ada.api.apiempleadas.services.EmpleadaService;

@RestController
public class EmpleadaController {

    @Autowired
    private EmpleadaService service;

    @Autowired
    CategoriaService categoriaService;

    @GetMapping("/empleados")
    public ResponseEntity<List<Empleada>> traerEmpleadas() {
        final List<Empleada> lista = service.traerEmpleadas();

        return ResponseEntity.ok(lista);
    }

    @PostMapping("/empleados")
    public ResponseEntity<?> crearEmpleada(@RequestBody  InfoEmpleadaNueva empleadaInfo) {
        GenericResponse respuesta = new GenericResponse();

        Empleada empleada = new Empleada();
        empleada.setNombre(empleadaInfo.nombre);
        empleada.setEdad(empleadaInfo.edad);
        empleada.setSueldo(empleadaInfo.sueldo);
        empleada.setFechaAlta(new Date());
        
       Categoria categoria = categoriaService.buscarCategoria(empleadaInfo.categoriaId);
        empleada.setCategoria(categoria);
        empleada.setEstado (EstadoEmpleadaEnum.ACTIVO);

        service.crearEmpleada(empleada);
        respuesta.isOk = true;
        respuesta.id = empleada.getEmpleadaId();
        respuesta.message = "La empleada fue creada con exito";
        return ResponseEntity.ok(respuesta);

    }

    @GetMapping("/empleados/{id}")
    public ResponseEntity<Empleada> getEmpleadaPorId(@PathVariable Integer id){
        Empleada empleada = service.buscarEmpleada(id);

        return ResponseEntity.ok(empleada);
    }
   //Detele/empleados/{id} --> Da de baja un empleado poniendo el campo estado en "baja"
    // y la fecha de baja que sea el dia actual.
    @DeleteMapping("/empleados/{id}")
    public ResponseEntity<?> bajaEmpleada(@PathVariable Integer id){

        service.bajaEmpleadaPorId(id);

        GenericResponse respuesta = new GenericResponse();

        respuesta.isOk = true;
        respuesta.message = "La empleada fue dada de baja con exito";

        return ResponseEntity.ok(respuesta);

    }
 //Get /empleados/categorias/{catId} --> Obtiene la lista de empleados de una categoria.
 @GetMapping("/empleados/categorias/{catId}")
 public ResponseEntity<List<Empleada>> obtenerEmpleadasPorCategoria(@PathVariable Integer catId){
     
     List<Empleada> empleadas = service.traerEmpleadaPorCategoria(catId);
     return ResponseEntity.ok(empleadas);
 }

 @PutMapping("/empleados/{id}/sueldos")
 public ResponseEntity<GenericResponse> modificarSueldo(@PathVariable Integer id, @RequestBody SueldoNuevoEmpleada sueldoNuevoInfo){

     //1) buscar la empleada
     Empleada empleada = service.buscarEmpleada(id);
     //2) setear su nuevo sueldo
     empleada.setSueldo(sueldoNuevoInfo.sueldoNuevo);
     //3) guardarlo  en la base de datos
     service.guardar(empleada);

     GenericResponse respuesta = new GenericResponse();

     respuesta.isOk = true;
     respuesta.message = "Sueldo actualizado";

     return ResponseEntity.ok(respuesta);
 } 
 
}