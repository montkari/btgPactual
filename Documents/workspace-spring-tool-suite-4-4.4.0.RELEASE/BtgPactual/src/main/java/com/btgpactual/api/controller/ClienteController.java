package com.btgpactual.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.btgpactual.domain.model.Cliente;
import com.btgpactual.domain.repository.ClienteRepository;
import com.btgpactual.domain.service.CadastroClienteService;


@RestController
@RequestMapping("/digi")
public class ClienteController {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private CadastroClienteService cadastroCliente;
	
	//**************** Metodo listar Entidade Cliente*******************
	@GetMapping
	public List<Cliente> listar() {		
	
	return clienteRepository.findAll();		
		//return clienteRepository.findByNomeContaining("anner");
	}
	
	
	
	//********************* Método Buscar por ID Cliente***************
	@GetMapping("/{clienteId}")
	public ResponseEntity<Cliente> buscar(@PathVariable Long clienteId) {
		
	 Optional<Cliente> cliente = clienteRepository.findById(clienteId);
	 
	 if(cliente.isPresent()) {
		 return ResponseEntity.ok(cliente.get()) ;
	 }	 
	 
	 return ResponseEntity.notFound().build(); // cliente.orElse(null) ; 
	}
	
	
	//*****************Metodo Adicionar Cliente********
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public Cliente adcionar(@Valid @RequestBody Cliente cliente) throws Exception { // RequestBody transforma pega as informações por JSON 
	// return	clienteRepository.save(cliente);
		return cadastroCliente.salvar(cliente);
	}
	
	
	//****************Metodo Atualizar*****************
	@PutMapping("/{clienteId}")
	public ResponseEntity<Cliente> atualizar(@Valid @PathVariable Long clienteId, @RequestBody Cliente cliente) throws Exception{
		 
		if(!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}	
		
		cliente.setId(clienteId);
		cliente = cadastroCliente.salvar(cliente); // clienteRepository.save(cliente);
		
		return ResponseEntity.ok(cliente);
	}
	
	
	//*****************Metodo Excluir*****************
	
	@DeleteMapping("/{clienteId}")
	public ResponseEntity<Void> remover(@PathVariable Long clienteId){
		if(clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}
		
		cadastroCliente.excluir(clienteId);//	clienteRepository.deleteById(clienteId);
		
		return ResponseEntity.noContent().build();		
	}

}
