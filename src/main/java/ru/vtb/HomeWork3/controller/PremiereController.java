package ru.vtb.HomeWork3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.vtb.HomeWork3.dto.PremiereDto;
import ru.vtb.HomeWork3.entity.PremiereEntity;
import ru.vtb.HomeWork3.mapper.PremiereTransformer;
import ru.vtb.HomeWork3.services.PremiereRepoService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/premieres")
public class PremiereController {

    private final PremiereRepoService premiereRepoService;
    private final PremiereTransformer premiereTransformer;

    @Autowired
    public PremiereController(PremiereRepoService premiereRepoService, PremiereTransformer premiereTransformer) {
        this.premiereRepoService = premiereRepoService;
        this.premiereTransformer = premiereTransformer;
    }

    @GetMapping
    public Collection<PremiereDto> findAllPremieres() {
        final Collection<PremiereEntity> allPremieres = premiereRepoService.findAll();
        final List<PremiereDto> result = new ArrayList<>(allPremieres.size());

        for (PremiereEntity p : allPremieres) {
            result.add(premiereTransformer.toPremiereDto(p));
        }
        return result;
    }

    @GetMapping("/{name}")
    public PremiereDto findPremiereByName(@PathVariable("name") String name) {
        return premiereTransformer.toPremiereDto(premiereRepoService.findByName(name));
    }

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public PremiereDto addPremiere(@RequestBody PremiereDto premiereDto) {
        return premiereTransformer.toPremiereDto(premiereRepoService.addPremiere(premiereTransformer.toPremiere(premiereDto)));
    }

    @DeleteMapping("/{name}")
    public void deletePremiere(@PathVariable("name") String name) {
        premiereRepoService.deletePremiere(name);
    }

    @PutMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public PremiereDto changePremiere(@RequestBody PremiereDto premiereDto) {
        return premiereTransformer.toPremiereDto(premiereRepoService.changePremiere(premiereTransformer.toPremiere(premiereDto)));
    }

}
