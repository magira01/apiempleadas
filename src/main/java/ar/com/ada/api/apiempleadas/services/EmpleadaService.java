package ar.com.ada.api.apiempleadas.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.apiempleadas.entities.Categoria;
import ar.com.ada.api.apiempleadas.entities.Empleada;
import ar.com.ada.api.apiempleadas.entities.Empleada.EstadoEmpleadaEnum;
import ar.com.ada.api.apiempleadas.repos.CategoriaRepository;
import ar.com.ada.api.apiempleadas.repos.EmpleadaRepository;

@Service
public class EmpleadaService {

    @Autowired
    EmpleadaRepository repo;

    @Autowired
    CategoriaService categoriaService;

    public void crearEmpleada(Empleada empleada) {
        repo.save(empleada);
    }

    public List<Empleada> traerEmpleadas() {
        return repo.findAll();
    }

    public Empleada buscarEmpleada(Integer empleadaId){
        Optional<Empleada> resultado = repo.findById(empleadaId);

        if(resultado.isPresent())
            return resultado.get();
        
        
        return null;
        
    }

     //DELETE LOGICO,que se mantiene en la db pero con estatus.
	public void bajaEmpleadaPorId(Integer id) {
        Empleada empleada = this.buscarEmpleada(id);
        
        empleada.setEstado(EstadoEmpleadaEnum.BAJA);
        empleada.setFechaBaja(new Date());

        repo.save(empleada);

	}
    public List<Empleada> traerEmpleadaPorCategoria(Integer catId) {
		
        Categoria categoria = categoriaService.buscarCategoria(catId);
        
        return categoria.getEmpleadas();

	}

	public void guardar(Empleada empleada) {
        repo.save(empleada);
	}
}

