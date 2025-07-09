package br.com.estoque.dasa.modules.user.controller;


import br.com.estoque.dasa.modules.user.repository.UserRepository;
import br.com.estoque.dasa.modules.user.service.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository repository;

    @PostMapping
    @Transactional
    public void create(@RequestBody DataCreateUser data) {
        repository.save(new User(data));
    }

    @GetMapping
    public List<DataListUser> list() {
        return repository.findAll().stream().map(DataListUser::new).toList();
    }

    @PutMapping
    @Transactional
    public void update(@RequestBody DataAttUser data) {
        var user = repository.getReferenceById(data.id());
        user.updateValues(data);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
