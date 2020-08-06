package com.lambdaschool.countries.controllers;

import com.lambdaschool.countries.models.Country;
import com.lambdaschool.countries.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.*;

@RestController
public class CountryController {
    @Autowired
    CountryRepository countryrepos;

    @GetMapping(value = "/names/all", produces = {"application/json"})
    public ResponseEntity<?> listAllCountries() {
        List<Country> countryList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(countryList::add);
        countryList.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
        return new ResponseEntity<>(countryList, HttpStatus.OK);
    }

    @GetMapping(value = "/names/start/{letter}", produces = {"application/json"})
    public ResponseEntity<?> countriesByFirstLetter(@PathVariable char letter) {
        List<Country> countryList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(countryList::add);
        countryList.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
        List<Country> filteredList = countryList.stream().filter(c -> c.getName().charAt(0) == letter).collect(Collectors.toList());
        return new ResponseEntity<>(filteredList, HttpStatus.OK);
    }

    @GetMapping(value = "/population/total", produces = {"application/json"})
    public ResponseEntity<?> totalPopulation() {
        List<Country> countryList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(countryList::add);
        long sumPop = countryList.stream().reduce((long) 0, (partialPopResult, c ) -> partialPopResult + c.getPopulation(), Long::sum);
        System.out.println("The Total Population is " + sumPop);
        return new ResponseEntity<>("Status OK", HttpStatus.OK);
    }

    @GetMapping(value = "/population/max", produces = {"application/json"})
    public ResponseEntity<?> maxPopulation() {
        List<Country> countryList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(countryList::add);
        countryList.sort((c1, c2) -> Long.compare(c2.getPopulation(), c1.getPopulation()));
        return new ResponseEntity<>(countryList.get(0), HttpStatus.OK);
    }

    @GetMapping(value = "/population/min", produces = {"application/json"})
    public ResponseEntity<?> minPopulation() {
        List<Country> countryList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(countryList::add);
        countryList.sort((c1, c2) -> Long.compare(c1.getPopulation(), c2.getPopulation()));
        return new ResponseEntity<>(countryList.get(0), HttpStatus.OK);
    }

    @GetMapping(value ="/population/median", produces = {"application/json"})
    public ResponseEntity<?>  medianPopulation() {
        List<Country> countryList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(countryList::add);
        countryList.sort((c1, c2) -> Long.compare(c1.getPopulation(), c2.getPopulation()));

        if(countryList.size() % 2 != 0) {
            return new ResponseEntity<>(countryList.get(countryList.size()/2), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No median country found", HttpStatus.OK);
        }
    }
}
