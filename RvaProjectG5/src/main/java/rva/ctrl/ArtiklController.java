package rva.ctrl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import rva.model.Artikl;
import rva.repository.ArtiklRepository;

@RestController
public class ArtiklController {

	@Autowired
	private ArtiklRepository artiklRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@GetMapping("/artikl")
	public Collection<Artikl> getAllArticles(){
		return artiklRepository.findAll();
	}
	
	@GetMapping("/artikl/{id}")
	public Artikl getArtiklById(@PathVariable Integer id ) {
		return artiklRepository.getById(id);
	}
	
	@GetMapping("/artikl/naziv/{naziv}")
	public Collection<Artikl> getArtiklByNaziv(@PathVariable String naziv){
		return artiklRepository.findByNazivContainingIgnoreCase(naziv);
	}
	
	@PostMapping("/artikl")
	public ResponseEntity<Artikl> createArtikl(@RequestBody Artikl artikl){
		if(!artiklRepository.existsById(artikl.getId())) {
			Artikl temp = artiklRepository.save(artikl);
			return new ResponseEntity<Artikl>(temp,HttpStatus.CREATED);
		}else {
			return new ResponseEntity<Artikl>(HttpStatus.CONFLICT);
		}
		
	}
	
	@PutMapping("/artikl")
	public ResponseEntity<Artikl> updateArtikl(@RequestBody Artikl artikl){
		if(artiklRepository.existsById(artikl.getId())){
			artiklRepository.save(artikl);
			return new ResponseEntity<Artikl>(HttpStatus.OK);
			}
		else {
			return new ResponseEntity<Artikl>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/artikl/{id}")
	public ResponseEntity<Artikl> deleteArtikl(@PathVariable int id){
		if(artiklRepository.existsById(id)) {
			artiklRepository.deleteById(id);
			jdbcTemplate.execute("INSERT INTO \"artikl\" VALUES(-100,'Test naziv', 'Test proizvodjac')");
			return new ResponseEntity<Artikl>(HttpStatus.OK);
		}else {
			return new ResponseEntity<Artikl>(HttpStatus.NOT_FOUND);
		}
	}
	
}
