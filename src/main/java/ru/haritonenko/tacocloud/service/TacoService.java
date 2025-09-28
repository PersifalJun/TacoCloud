package ru.haritonenko.tacocloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import ru.haritonenko.tacocloud.entity.Taco;
import ru.haritonenko.tacocloud.props.OrderProps;
import ru.haritonenko.tacocloud.repository.TacoRepository;

import java.util.Optional;


@Service
@Transactional
public class TacoService {
    private final TacoRepository tacoRepository;
    @Autowired
    public TacoService(TacoRepository tacoRepository) {
        this.tacoRepository = tacoRepository;
    }

    public Iterable<Taco> findAllFromPage(OrderProps props){
        PageRequest page = PageRequest.of(
                0, props.getPageSize(), Sort.by("createdAt").descending());

        return tacoRepository.findAll(page).getContent();
    }

    public Optional<Taco> getTacoById(Long id) {
        return tacoRepository.findById(id);
    }

    public Taco saveTaco(Taco taco){
        return tacoRepository.save(taco);
    }
}
