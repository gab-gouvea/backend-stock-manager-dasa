package br.com.estoque.dasa.modules.user.controller;


import br.com.estoque.dasa.modules.user.entity.User;
import br.com.estoque.dasa.modules.user.repository.UserRepository;
import br.com.estoque.dasa.modules.user.service.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<?> create(@RequestBody @Valid DataCreateUser data) {
        if (repository.existsByEmail(data.email())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email já cadastrado!");
        }
        repository.save(new User(data));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public List<DataListUser> list() {
        return repository.findAll().stream().map(DataListUser::new).toList();
    }

    @PutMapping
    @Transactional
    public ResponseEntity<?> update(@RequestBody @Valid DataAttUser data) {
        if (!repository.existsById(data.id())) {
            return ResponseEntity.notFound().build();
        }
        var user = repository.getReferenceById(data.id());
        user.updateValues(data);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Dados atualizados com sucesso!");
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable String id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
